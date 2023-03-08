package com.example.pokemonservice.Service;


import com.example.pokemonservice.Domain.User;
import com.example.pokemonservice.Domain.UserRequest;
import com.example.pokemonservice.Repository.UserRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.util.Optional;
import java.util.regex.Pattern;


@Service
@NoArgsConstructor
public class UserService {

    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public ResponseEntity<?> registerUser(UserRequest userRequest) {
        if(AlreadyRegistered(userRequest)){
            return ResponseEntity.badRequest().body("already exist an user with this email");
        }
        if(!ValidEmail(userRequest.getEmailRequest())){
            return ResponseEntity.badRequest().body("invalid email");
        }
        if(!ValidPassword(userRequest.getPasswordRequest())){
            return ResponseEntity.badRequest().body("invalid password");
        }
        User user = mapUserRequestToUser(userRequest);
        return ResponseEntity.ok(userRepository.save(user).getUserId());
    }

    public String LogUser(UserRequest userRequest){
        User user = userRepository.findUserByEmail(userRequest.getEmailRequest());
        if (getSha256(userRequest.getPasswordRequest())
                .equals(user.getPassword())){
            return user.getUserId();
        }else{
            return "Unauthorized wrong password";
        }
    }

    public User getUserByUserId(String userId){
        Optional<User> user = userRepository.findById(userId);
        return user.orElse(null);
    }

    public boolean AlreadyRegistered(UserRequest userRequest){
        return userRepository.countUsersByEmail(userRequest.getEmailRequest()) != 0;
    }

    public boolean ValidEmail(String email){
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        return pattern.matcher(email).find();
    }

    public boolean ValidPassword(String password){
        final String COMPLEX_PASSWORD_REGEX =
                "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,16}$";
        Pattern pattern = Pattern.compile(COMPLEX_PASSWORD_REGEX);
        return pattern.matcher(password).find();
    }
    public User mapUserRequestToUser(UserRequest userRequest){
        User user = new User();
        user.setEmail(userRequest.getEmailRequest());
        String encoded = getSha256(userRequest.getPasswordRequest());
        user.setPassword(encoded);
        return user;
    }
    public String getSha256(String value) {
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(value.getBytes());
            return bytesToHex(md.digest());
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }
    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) result.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        return result.toString();
    }
}
