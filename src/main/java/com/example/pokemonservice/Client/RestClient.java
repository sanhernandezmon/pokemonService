package com.example.pokemonservice.Client;

import com.example.pokemonservice.Domain.CatsFacts;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Setter
@Getter
@Service
public class RestClient {
    private String server = "https://catfact.ninja/fact";
    private RestTemplate rest;
    private HttpHeaders headers;
    private HttpStatus status;

    public RestClient() {
        this.rest = new RestTemplate();
        this.headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "*/*");
    }
    public CatsFacts get(String uri) {
        HttpEntity<String> requestEntity = new HttpEntity<>("", headers);
        ResponseEntity<String> responseEntity = rest.exchange(server + uri, HttpMethod.GET, requestEntity, String.class);
        this.setStatus((HttpStatus) responseEntity.getStatusCode());
        CatsFacts fact;
        try {
            fact = new ObjectMapper().readValue( responseEntity.getBody(), CatsFacts.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return fact;
    }

}
