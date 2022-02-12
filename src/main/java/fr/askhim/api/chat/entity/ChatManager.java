package fr.askhim.api.chat.entity;

import java.util.HashMap;
import java.util.Map;

public class ChatManager {

    private Map<String, Discussion> discussions = new HashMap<>();

    public Map<String, Discussion> getDiscussions(){
        return discussions;
    }

}
