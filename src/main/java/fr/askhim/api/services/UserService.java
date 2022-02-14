package fr.askhim.api.services;

import fr.askhim.api.entity.User;
import fr.askhim.api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User getUserById(Long id){
        return userRepository.findById(id).get();
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public boolean userExist(String email){
        return getUserByEmail(email) != null;
    }

    public User getRandomUser(){
        List<User> users = userRepository.findAll();
        if(users.size() == 0){
            return null;
        }
        Random random = new Random();
        return users.get(random.nextInt(users.size()));
    }

}
