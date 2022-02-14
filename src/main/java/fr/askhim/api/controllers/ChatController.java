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

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/chat")
public class ChatController {

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
    public String initDiscussion(HttpServletResponse response, @RequestParam Long serviceId, @RequestParam UUID userToken){
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
    public DiscussionModel getDiscussionById(HttpServletResponse response, @PathVariable String discussionId){
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
    public ResponseEntity postMessage(@RequestParam UUID discussionId, @RequestParam UUID userToken, @RequestParam String message){
        ChatManager chatManager = RedissonManager.getChatManager();
        if(!chatManager.discussionExist(discussionId)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "UNKNOWN_DISCUSSION", "La discussion n'a pas été trouvée"));
        }
        RBucket<Discussion> discussionBucket = RedissonManager.getRedissonClient().getBucket("discussion_" + discussionId.toString());
        Discussion discussion = discussionBucket.get();
        User user = tokenService.getUserByToken(userToken);
        Message newMessage = new Message(user.getId(), discussion.getUuid(), message);
        discussion.getMessages().add(newMessage.getUuid());
        RBucket<Message> messageBucket = RedissonManager.getRedissonClient().getBucket("message_" + newMessage.getUuid());
        messageBucket.set(newMessage);
        discussionBucket.set(discussion);
        return null;
    }

    @GetMapping("/test")
    public String test(){
        return "";
    }

    @DeleteMapping("/dump-redis")
    public String dumpRedis(){
        return RedissonManager.dumpRedis();
    }

    private DiscussionModel convertToDiscussionModel(Discussion discussion){
        DiscussionModel discussionModel = new DiscussionModel();
        discussionModel.setUuid(discussion.getUuid());
        Service service = serviceService.getServiceById(discussion.getServiceId());
        ServiceMinModel serviceModel = mapper.map(service, ServiceMinModel.class);
        discussionModel.setService(serviceModel);
        List<MessageModel> messages = new ArrayList<>();
        for(UUID messageUuid : discussion.getMessages()){
            RBucket<Message> messageBucket = RedissonManager.getRedissonClient()
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
