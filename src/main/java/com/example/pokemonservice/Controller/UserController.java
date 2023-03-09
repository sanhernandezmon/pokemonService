package com.example.pokemonservice.Controller;

import com.example.pokemonservice.Domain.UserRequest;
import com.example.pokemonservice.Security.JwtTokenUtil;
import com.example.pokemonservice.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping(path = "/api/v1/user")
public class UserController {

    private final UserService userService;

    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("register")
    ResponseEntity<?> RegisterUser(@RequestBody UserRequest userRequest){
        return userService.registerUser(userRequest);
    }

    @PostMapping("login")
    ResponseEntity<String> LogUser(@RequestBody UserRequest userRequest){
        System.out.println("entrando");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userRequest.getEmailRequest(), userRequest.getPasswordRequest()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenUtil.generateJwtToken(authentication);
        if (userService.LogUser(userRequest) != null) {
            return ResponseEntity.ok(jwt);
        }
        else{
            return ResponseEntity.badRequest().body("cannot log in user, try again");
        }
    }
}