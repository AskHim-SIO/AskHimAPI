package fr.askhim.api.chat.entity;

import fr.askhim.api.chat.RedissonManager;
import org.redisson.api.RBucket;

import java.util.*;

public class ChatManager {

    private List<String> discussionsKey = new ArrayList<>();

    public ChatManager(){

    }

    public List<String> getDiscussionsKey(){
        return discussionsKey;
    }

    public Discussion createDiscussion(long serviceId, long authorServiceId, long userId){
        List<Long> usersId = new ArrayList<>();
        usersId.add(authorServiceId);
        usersId.add(userId);
        Discussion discussion = new Discussion(serviceId, usersId);
        discussionsKey.add("discussion_" + discussion.getUuid());
        return discussion;
    }

    public boolean discussionExist(long serviceId, long userId){
        boolean exist = false;
        for(String discussionKey : discussionsKey){
            RBucket<Discussion> discussionBucket = RedissonManager.getRedissonClient().getBucket(discussionKey);
            Discussion discussion = discussionBucket.get();
            if(discussion.getServiceId() == serviceId && discussion.getUsersId().contains(userId)){
                exist = true;
            }
        }
        return exist;
    }

    public boolean discussionExist(UUID uuid){
        return discussionsKey.contains("discussion_" + uuid.toString());
    }

}
