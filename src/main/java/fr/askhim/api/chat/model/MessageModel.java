package fr.askhim.api.chat.model;

import fr.askhim.api.model.UserModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class MessageModel {

    private UUID uuid;
    private UserModel author;
    private Date postDate;
    private String message;

}
