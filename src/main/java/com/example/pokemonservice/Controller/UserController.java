package com.example.pokemonservice.Controller;

import com.example.pokemonservice.Domain.JwtResponse;
import com.example.pokemonservice.Domain.UserRequest;
import com.example.pokemonservice.Security.JwtTokenUtil;
import com.example.pokemonservice.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController()
@CrossOrigin(origins = "*")
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
    ResponseEntity<JwtResponse> LogUser(@RequestBody UserRequest userRequest) throws Exception {
        authenticate(userRequest.getEmailRequest(), userRequest.getPasswordRequest());
        final UserDetails userDetails = userService.loadUserByUsername(userRequest.getEmailRequest());
        final String token = jwtTokenUtil.generateToken(userDetails);
        JwtResponse jwtResponse = new JwtResponse(token);
        return ResponseEntity.ok(jwtResponse);
    }
    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}