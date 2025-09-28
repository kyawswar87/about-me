package com.example.aboutme;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AboutMeController {
    
    private ChatClient chatClient;
    private AboutMeService aboutMeService;
    
    public AboutMeController(ChatClient chatClient, AboutMeService aboutMeService) {
        this.chatClient = chatClient;
        this.aboutMeService = aboutMeService;
    }

    @GetMapping("/me")
    String aboutme(@RequestParam String question) {
        return this.chatClient.prompt()
            .user(question)
            .call()
            .content();
    }

    @GetMapping("/verify-pdf")
    String verifyPdf() {
        return this.aboutMeService.findALLPDF();
    }
}
