package fr.askhim.api.services;

import fr.askhim.api.entity.User;
import fr.askhim.api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User getUserById(Long id){
        Optional<User> userOptionnal = userRepository.findById(id);
        if(userOptionnal.isPresent()){
            return userOptionnal.get();
        }else{
            return null;
        }
    }

    public boolean userExistByEmail(String email){
        return getUserByEmail(email) != null;
    }

    public boolean userExistById(Long id){
        return getUserById(id) != null;
    }

    public User getRandomUser(){
        List<User> users = userRepository.findAll();
        if(users.size() == 0){
            return null;
        }
        Random random = new Random();
        return users.get(random.nextInt(users.size()));
    }

    public int getNbUserActivated(){
        List<User> users = userRepository.findAll();
        List<User> usersActivated = new ArrayList<>();
        for(User user : users){
            if(user.getDeleteDate() != null){
                usersActivated.add(user);
            }
        }
        return usersActivated.size();
    }

}
