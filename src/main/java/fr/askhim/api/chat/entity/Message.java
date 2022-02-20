package fr.askhim.api.chat.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class Message {

    private UUID uuid;
    private long authorId;
    private Date postDate;
    private UUID discussion;
    private String message;
    private boolean unreaded;

    public Message(long authorId, UUID discussion, String message){
        this.uuid = UUID.randomUUID();
        this.authorId = authorId;
        this.postDate = new Date();
        this.discussion = discussion;
        this.message = message;
        this.unreaded = true;
    }

    public Message(){

    }

}
