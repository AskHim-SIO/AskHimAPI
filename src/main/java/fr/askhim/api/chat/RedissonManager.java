package fr.askhim.api.chat;

import fr.askhim.api.chat.entity.ChatManager;
import fr.askhim.api.chat.entity.Discussion;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.springframework.stereotype.Service;

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

        config.useSingleServer()
                .setAddress("askhim.ctrempe.fr:6379")
                .setPassword("qMA747wc");

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

    public static String dumpRedis(){
        RBucket<ChatManager> chatManagerBucket = redissonClient.getBucket("chatmanager");
        if(chatManagerBucket.isExists()){
            chatManagerBucket.delete();
        }
        return "Redis dump !";
    }

}
