package com.example.pokemonservice.Controller;

import com.example.pokemonservice.Client.RestClient;
import com.example.pokemonservice.Domain.CatsFacts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/v1/cats")
public class CatsController {
    private final RestClient restClient;
    @Autowired
    public CatsController(RestClient restClient) {
        this.restClient = restClient;
    }

    @GetMapping("")
    public CatsFacts GetCatsFact(){
        return restClient.get("");
    }
}
