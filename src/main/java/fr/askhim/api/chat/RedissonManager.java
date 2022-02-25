package fr.askhim.api.chat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import fr.askhim.api.chat.entity.ChatManager;
import fr.askhim.api.chat.entity.Discussion;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;

@Service
public class RedissonManager {

    private static RedissonClient redissonClient;

    public RedissonManager(){
        connection();
    }

    public static RedissonClient getRedissonClient(){
        return redissonClient;
    }

    private void connection() {
        Config config = new Config();

        config.setCodec(new JsonJacksonCodec());

        Gson gson = new GsonBuilder().create();
        JsonObject obj = gson.fromJson(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("config.json")), JsonObject.class);

        String redisHost= obj.get("redis-host").getAsString();
        String redisPassword= obj.get("redis-password").getAsString();

        config.useSingleServer()
                .setAddress(redisHost)
                .setPassword(redisPassword);

        redissonClient = Redisson.create(config);
        System.out.println("[Redisson] Connexion à la base de données réussie !");
    }

    public static void disconnect() {
        redissonClient.shutdown();
        System.out.println("[Redisson] Déconnexion de la base de données réussie !");
    }

    // -------

    public static ChatManager getChatManager(){
        RBucket<ChatManager> chatManagerBucket = redissonClient.getBucket("chatmanager");
        if(!chatManagerBucket.isExists()){
            ChatManager chatManager = new ChatManager();
            chatManagerBucket.set(chatManager);
        }
        return chatManagerBucket.get();
    }

    public static void updateChatManager(ChatManager chatManager){
        RBucket<ChatManager> chatManagerBucket = redissonClient.getBucket("chatmanager");
        chatManagerBucket.set(chatManager);
    }

    public static String flushRedis(){
        redissonClient.getKeys().flushall();
        return "Redis flushed !";
    }

}
