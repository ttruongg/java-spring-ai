package com.example.essentials.controller;

import com.example.essentials.model.CountryCities;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class StructureOutputController {

    private final ChatClient chatClient;

    public StructureOutputController(ChatClient.Builder chatClient) {
        this.chatClient = chatClient
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }

    @GetMapping("/chat-bean")
    public ResponseEntity<CountryCities> getCountryCities(@RequestParam("message") String message) {
        CountryCities countryCities = chatClient.prompt()
                .user(message)
                .call()
                .entity(CountryCities.class);

        return ResponseEntity.ok(countryCities);
    }

    @GetMapping("/chat-list")
    public ResponseEntity<List<String>> getCities(@RequestParam("message") String message) {

        List<String> cities = chatClient
                .prompt()
                .user(message)
                .call()
                .entity(new ListOutputConverter());

        return ResponseEntity.ok(cities);
    }

    @GetMapping("/chat-map")
    public ResponseEntity<Map<String, Object>> getCityInfo(@RequestParam("message") String message) {

        Map<String, Object> cityInfo = chatClient
                .prompt()
                .user(message)
                .call()
                .entity(new MapOutputConverter());

        return ResponseEntity.ok(cityInfo);
    }

    @GetMapping("/chat-bean-list")
    public ResponseEntity<List<CountryCities>> getCountryCitiesList(@RequestParam("message") String message) {

        List<CountryCities> countryCities = chatClient
                .prompt()
                .user(message)
                .call()
                .entity(new ParameterizedTypeReference<List<CountryCities>>() {
                });

        return ResponseEntity.ok(countryCities);

    }
}
