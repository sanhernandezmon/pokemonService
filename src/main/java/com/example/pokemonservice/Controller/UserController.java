package com.example.pokemonservice.Controller;

import com.example.pokemonservice.Domain.UserRequest;
import com.example.pokemonservice.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping(path = "/api/v1/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("register")
    ResponseEntity<?> RegisterUser(@RequestBody UserRequest userRequest){
        return userService.registerUser(userRequest);
    }

    @PostMapping("login")
    String LogUser(@RequestBody UserRequest userRequest){
        return userService.LogUser(userRequest);
    }
}
