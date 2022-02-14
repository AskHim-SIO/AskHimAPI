package fr.askhim.api.controllers;

import fr.askhim.api.chat.RedissonManager;
import fr.askhim.api.chat.entity.ChatManager;
import fr.askhim.api.chat.entity.Discussion;
import fr.askhim.api.chat.model.DiscussionModel;
import fr.askhim.api.services.ServiceService;
import fr.askhim.api.services.TokenService;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ServiceService serviceService;
    private final TokenService tokenService;

    public ChatController(ServiceService serviceService, TokenService tokenService){
        this.serviceService = serviceService;
        this.tokenService = tokenService;
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
        RBucket<Discussion> discussionsBucket = RedissonManager.getRedissonClient().getBucket("discussion_" + serviceId + "_" + userId);
        discussionsBucket.set(discussion);
        return discussion.getUuid().toString();
    }

    @GetMapping("/get-discussion-by-id/{discussionId}")
    public DiscussionModel getDiscussion(HttpServletResponse response, @PathVariable String discussionId){
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

}
