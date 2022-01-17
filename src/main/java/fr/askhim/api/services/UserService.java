package fr.askhim.api.services;

import fr.askhim.api.payload.UserRequest;
import fr.askhim.api.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }


}
