package fr.askhim.api.controllers;

import fr.askhim.api.chat.RedissonManager;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @PostMapping("/init-chat")
    public String initChat(HttpServletResponse response, long serviceId, UUID userToken){

        return "Test";
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
