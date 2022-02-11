package fr.askhim.api.chat.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Discussion {

    private UUID uuid;
    private long serviceId;
    private List<Message> messages;
    private List<Long> usersId;

    public Discussion(long serviceId){
        this.uuid = UUID.randomUUID();
        this.serviceId = serviceId;
        this.messages = new ArrayList<>();
        this.usersId = new ArrayList<>();
    }

    public List<Message> getMessages(){
        return messages;
    }

    public void addMessage(long authorId, String message){
        messages.add(new Message(authorId, message));
    }

}
