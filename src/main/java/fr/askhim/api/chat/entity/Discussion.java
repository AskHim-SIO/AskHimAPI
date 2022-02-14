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
    private List<UUID> messages;
    private List<Long> usersId;

    public Discussion(long serviceId, List<Long> usersId){
        this.uuid = UUID.randomUUID();
        this.serviceId = serviceId;
        this.messages = new ArrayList<>();
        this.usersId = usersId;
    }

    public Discussion(){

    }

    public List<UUID> getMessages(){
        return messages;
    }

}
