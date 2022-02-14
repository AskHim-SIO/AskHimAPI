package fr.askhim.api.chat.model;

import fr.askhim.api.model.ServiceMinModel;
import fr.askhim.api.model.UserModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class DiscussionModel {

    private UUID uuid;
    private ServiceMinModel service;
    private List<MessageModel> messages;
    private List<UserModel> users;

}
