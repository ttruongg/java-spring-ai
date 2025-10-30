package com.example.tool_calling.controller;

import com.example.tool_calling.tools.HelpDeskTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@RestController
@RequestMapping("/api/tools")
public class HelpDeskTicketController {

    ChatClient chatClient;
    HelpDeskTools helpDeskTools;

    public HelpDeskTicketController(@Qualifier("helpDeskChatClient") ChatClient chatClient,
                                    HelpDeskTools helpDeskTools) {
        this.chatClient = chatClient;
        this.helpDeskTools = helpDeskTools;
    }

    @GetMapping("/help-desk")
    public ResponseEntity<String> helpDesk(@RequestHeader("username") String username,
                                           @RequestParam("message") String message) {
        String response = chatClient.prompt()
                .advisors(a -> a.param(CONVERSATION_ID, username))
                .user(message)
                .tools(helpDeskTools)
                .toolContext(Map.of("username", username))
                .call().content();
        return ResponseEntity.ok(response);
    }

}
