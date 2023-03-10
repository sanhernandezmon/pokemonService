package com.example.pokemonservice.Service;


import com.example.pokemonservice.Domain.User;
import com.example.pokemonservice.Domain.UserRequest;
import com.example.pokemonservice.Repository.UserRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.regex.Pattern;


@Service
@NoArgsConstructor
public class UserService implements UserDetailsService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
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
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        return ResponseEntity.ok(userRepository.save(user).getUserId());
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
        user.setPassword(userRequest.getPasswordRequest());
        return user;
    }

    public User getUserByEmail(String userEmail){
        return userRepository.findUserByEmail(userEmail);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }
}
