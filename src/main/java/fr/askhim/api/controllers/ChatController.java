package fr.askhim.api.controllers;

import fr.askhim.api.chat.RedissonManager;
import fr.askhim.api.chat.entity.ChatManager;
import fr.askhim.api.chat.entity.Discussion;
import fr.askhim.api.chat.entity.Message;
import fr.askhim.api.chat.model.DiscussionModel;
import fr.askhim.api.chat.model.MessageModel;
import fr.askhim.api.entity.Service;
import fr.askhim.api.entity.User;
import fr.askhim.api.model.ServiceMinModel;
import fr.askhim.api.model.UserModel;
import fr.askhim.api.payload.ApiResponse;
import fr.askhim.api.services.ServiceService;
import fr.askhim.api.services.TokenService;
import fr.askhim.api.services.UserService;
import org.modelmapper.ModelMapper;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@RestController
@RequestMapping("/chat")
public class ChatController {

    Logger logger = Logger.getLogger(ChatController.class.getPackage().getName());

    private final ServiceService serviceService;
    private final TokenService tokenService;
    private final UserService userService;

    private ModelMapper mapper = new ModelMapper();

    public ChatController(ServiceService serviceService, TokenService tokenService, UserService userService){
        this.serviceService = serviceService;
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @PostMapping("/init-discussion")
    public String initDiscussion(HttpServletRequest request, HttpServletResponse response, @RequestParam Long serviceId, @RequestParam UUID userToken){
        logger.info("[" + request.getRemoteHost() + "] " + request.getRequestURL());
        ChatManager chatManager = RedissonManager.getChatManager();
        long userId = tokenService.getUserByToken(userToken).getId();
        if(chatManager.discussionExist(serviceId, userId)){
            response.setStatus(HttpStatus.CONFLICT.value(), "DISCUSSION_ALREADY_EXIST");
            return "";
        }
        long authorServiceId = serviceService.getServiceById(serviceId).getUser().getId();
        Discussion discussion = chatManager.createDiscussion(serviceId, authorServiceId, userId);
        RedissonManager.updateChatManager(chatManager);
        RBucket<Discussion> discussionsBucket = RedissonManager.getRedissonClient().getBucket("discussion_" + discussion.getUuid());
        discussionsBucket.set(discussion);
        return discussion.getUuid().toString();
    }

    @GetMapping("/get-discussion-by-id/{discussionId}")
    public DiscussionModel getDiscussionById(HttpServletRequest request, HttpServletResponse response, @PathVariable String discussionId){
        logger.info("[" + request.getRemoteHost() + "] " + request.getRequestURL());
        UUID uuid = null;
        try {
            uuid = UUID.fromString(discussionId);
        }catch(Exception e){
            response.setStatus(HttpStatus.BAD_REQUEST.value(), "INCORRECT_UUID");
            return null;
        }
        ChatManager chatManager = RedissonManager.getChatManager();
        if(!chatManager.discussionExist(uuid)){
            response.setStatus(HttpStatus.NOT_FOUND.value(), "UNKNOWN_DISCUSSION");
            return null;
        }
        RBucket<Discussion> discussionBucket = RedissonManager.getRedissonClient().getBucket("discussion_" + uuid.toString());
        Discussion discussion = discussionBucket.get();
        DiscussionModel discussionModel = convertToDiscussionModel(discussion);
        return discussionModel;
    }

    @PostMapping("/post-message")
    public ResponseEntity postMessage(HttpServletRequest request, @RequestParam UUID discussionId, @RequestParam UUID userToken, @RequestParam String message){
        logger.info("[" + request.getRemoteHost() + "] " + request.getRequestURL());
        ChatManager chatManager = RedissonManager.getChatManager();
        if(!chatManager.discussionExist(discussionId)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "UNKNOWN_DISCUSSION", "La discussion n'a pas été trouvée"));
        }
        RBucket<Discussion> discussionBucket = RedissonManager.getRedissonClient().getBucket("discussion_" + discussionId.toString());
        Discussion discussion = discussionBucket.get();
        User user = tokenService.getUserByToken(userToken);
        if(!discussion.getUsersId().contains(user.getId())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse(false, "INSUFFICIENT_PERMISSIONS", "Cet utilisateur n'est pas autorisé à envoyer des messages dans cette discussion."));
        }
        Message newMessage = new Message(user.getId(), discussion.getUuid(), message);
        discussion.getMessages().add(newMessage.getUuid());
        RBucket<Message> messageBucket = RedissonManager.getRedissonClient().getBucket("message_" + newMessage.getUuid());
        messageBucket.set(newMessage);
        discussionBucket.set(discussion);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(true, "MESSAGE_SAVED", "Le message a été enregistré"));
    }

    @GetMapping("/get-discussions-from-user-by-token/{token}")
    public List<DiscussionModel> getDiscussionsFromUserByToken(HttpServletRequest request, @PathVariable UUID token, HttpServletResponse response){
        logger.info("[" + request.getRemoteHost() + "] " + request.getRequestURL());
        if(!tokenService.tokenExist(token)){
            response.setStatus(HttpStatus.NOT_FOUND.value(), "UNKNOWN_TOKEN");
            return null;
        }
        User user = tokenService.getUserByToken(token);
        List<DiscussionModel> discussions = new ArrayList<>();
        for(Discussion discussion : RedissonManager.getChatManager().getDiscussionsFromUser(user)){
            DiscussionModel discussionModel = convertToDiscussionModel(discussion);
            discussions.add(discussionModel);
        }
        return discussions;
    }

    @DeleteMapping("/flush-redis")
    public String flushRedis(HttpServletRequest request){
        logger.info("[" + request.getRemoteHost() + "] " + request.getRequestURL());
        return RedissonManager.flushRedis();
    }

    @GetMapping("/get-nb-discussions")
    public int getNbDiscussions(HttpServletRequest request){
        logger.info("[" + request.getRemoteHost() + "] " + request.getRequestURL());
        return RedissonManager.getChatManager().getDiscussionsKey().size();
    }

    @GetMapping("/get-nb-messages")
    public int getNbMessages(HttpServletRequest request){
        logger.info("[" + request.getRemoteHost() + "] " + request.getRequestURL());
        int nbMessages = 0;
        for(String discussionKey : RedissonManager.getChatManager().getDiscussionsKey()){
            RBucket<Discussion> discussionBucket = RedissonManager.getRedissonClient().getBucket(discussionKey);
            Discussion discussion = discussionBucket.get();
            nbMessages += discussion.getMessages().size();
        }
        return nbMessages;
    }

    @GetMapping("/check-new-messages-from-discussion")
    public ResponseEntity checkNewMessagesFromDiscussion(HttpServletRequest request, @RequestParam UUID discussionId, @RequestParam UUID userToken){
        logger.info("[" + request.getRemoteHost() + "] " + request.getRequestURL());
        if(!tokenService.tokenExist(userToken)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "UNKNOWN_TOKEN", "Le token spécifié n'existe pas"));
        }
        ChatManager chatManager = RedissonManager.getChatManager();
        if(!chatManager.discussionExist(discussionId)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "UNKNOWN_DISCUSSION", "La discussion spécifiée n'existe pas"));
        }
        User user = tokenService.getUserByToken(userToken);
        RBucket<Discussion> discussionBucket = RedissonManager.getRedissonClient().getBucket("discussion_" + discussionId.toString());
        Discussion discussion = discussionBucket.get();
        int unreadedMessages = 0;
        for(UUID messageId : discussion.getMessages()) {
            RBucket<Message> messageBucket = RedissonManager.getRedissonClient().getBucket("message_" + messageId.toString());
            Message message = messageBucket.get();
            if (message.getAuthorId() != user.getId() && message.isUnreaded()) {
                unreadedMessages++;
            }
        }
        ApiResponse response = (unreadedMessages == 0 ? new ApiResponse(false, "MESSAGES_READED", "Tous les messages ont été lus") : new ApiResponse(true, "MESSAGES_UNREADED", unreadedMessages + " messages non lus"));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @GetMapping("/check-new-messages-global")
    public ResponseEntity checkNewMessagesGlobal(HttpServletRequest request, @RequestParam UUID userToken){
        logger.info("[" + request.getRemoteHost() + "] " + request.getRequestURL());
        if(!tokenService.tokenExist(userToken)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "UNKNOWN_TOKEN", "Le token spécifié n'existe pas"));
        }
        User user = tokenService.getUserByToken(userToken);
        List<Discussion> discussions = RedissonManager.getChatManager().getDiscussionsFromUser(user);
        int unreadedMessages = 0;
        for(Discussion discussion : discussions){
            for(UUID messageId : discussion.getMessages()){
                RBucket<Message> messageBucket = RedissonManager.getRedissonClient().getBucket("message_" + messageId.toString());
                Message message = messageBucket.get();
                if (message.getAuthorId() != user.getId() && message.isUnreaded()) {
                    unreadedMessages++;
                }
            }
        }
        ApiResponse response = (unreadedMessages == 0 ? new ApiResponse(false, "MESSAGES_READED", "Tous les messages ont été lus") : new ApiResponse(true, "MESSAGES_UNREADED", unreadedMessages + " messages non lus"));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @PutMapping("/read-messages")
    public ResponseEntity readMessages(HttpServletRequest request, @RequestParam UUID discussionId, @RequestParam UUID userToken){
        logger.info("[" + request.getRemoteHost() + "] " + request.getRequestURL());
        if(!tokenService.tokenExist(userToken)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "UNKNOWN_TOKEN", "Le token spécifié n'existe pas"));
        }
        if(!RedissonManager.getChatManager().discussionExist(discussionId)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "UNKNOWN_DISCUSSION", "La discussion n'a pas été trouvée"));
        }
        User user = tokenService.getUserByToken(userToken);
        RBucket<Discussion> discussionBucket = RedissonManager.getRedissonClient().getBucket("discussion_" + discussionId.toString());
        Discussion discussion = discussionBucket.get();
        for(UUID messageId : discussion.getMessages()){
            RBucket<Message> messageBucket = RedissonManager.getRedissonClient().getBucket("message_" + messageId.toString());
            Message message = messageBucket.get();
            if (message.getAuthorId() != user.getId() && message.isUnreaded()) {
                message.setUnreaded(false);
                messageBucket.set(message);
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(true, "MESSAGE_READED", "Les messages ont été marqués comme lu avec succès"));
    }

    private DiscussionModel convertToDiscussionModel(Discussion discussion){
        DiscussionModel discussionModel = new DiscussionModel();
        discussionModel.setUuid(discussion.getUuid());
        Service service = serviceService.getServiceById(discussion.getServiceId());
        ServiceMinModel serviceModel = mapper.map(service, ServiceMinModel.class);
        discussionModel.setService(serviceModel);
        List<MessageModel> messages = new ArrayList<>();
        for(UUID messageUuid : discussion.getMessages()){
            RBucket<Message> messageBucket = RedissonManager.getRedissonClient().getBucket("message_" + messageUuid.toString());
            Message message = messageBucket.get();
            MessageModel messageModel = new MessageModel();
            messageModel.setUuid(message.getUuid());
            User author = userService.getUserById(message.getAuthorId());
            UserModel authorModel = mapper.map(author, UserModel.class);
            messageModel.setAuthor(authorModel);
            messageModel.setPostDate(message.getPostDate());
            messageModel.setMessage(message.getMessage());
            messageModel.setUnreaded(message.isUnreaded());
            messages.add(messageModel);
        }
        discussionModel.setMessages(messages);
        List<UserModel> users = new ArrayList<>();
        for(Long userId : discussion.getUsersId()){
            User user = userService.getUserById(userId);
            UserModel userModel = mapper.map(user, UserModel.class);
            users.add(userModel);
        }
        discussionModel.setUsers(users);
        return discussionModel;
    }

}
