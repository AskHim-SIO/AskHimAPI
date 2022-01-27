package fr.askhim.api.services;

import fr.askhim.api.entity.Token;
import fr.askhim.api.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    public Token getTokenById(UUID id){
        Optional<Token> tokenTry = tokenRepository.findById(id.toString());
        if(!tokenTry.isPresent()){
            return null;
        }
        return tokenTry.get();
    }

    public boolean tokenExist(UUID id){
        return getTokenById(id) != null;
    }
}
