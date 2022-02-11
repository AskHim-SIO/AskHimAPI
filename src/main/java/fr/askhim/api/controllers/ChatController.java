package fr.askhim.api.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @PostMapping("/init-chat")
    public String initChat(HttpServletResponse response){

        return "";
    }

}
