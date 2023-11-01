package com.chatgptapi.test.controller;

import com.chatgptapi.test.service.ChatGptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/*
* Controller 역할:
* 화면에서 chatGpt에게 물어볼 prompt만 받아서 Service에 넘겨주고 받은 response만 내려주면 된다.*/
@RestController
@RequestMapping("/chatGPT")
public class ChatGptController {
    private static final Logger log = LoggerFactory.getLogger(ChatGptController.class);

    private ChatGptService chatGptService;

    @GetMapping(path = "/")
    public String index() { return "index"; }

    @PostMapping(path = "/")
    public String chat(Model model, @ModelAttribute String prompt) {
        try {
            model.addAttribute("request", prompt);
            model.addAttribute("response", chatGptService.chat(prompt));
        } catch (Exception e) {
            model.addAttribute("response", "CHAT GPT API ERROR");
            log.error("Exception", e);
        }
        return "index";
    }
}
