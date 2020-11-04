package com.azmd.controllers;

import com.azmd.domain.Message;
import com.azmd.domain.User;
import com.azmd.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/main")
    public String mainPage() {
        return "main";
    }

    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam String text,
            @RequestParam String tag,
            Map<String, Object> model) {
        Message message = new Message(text, tag, user);
        messageRepository.save(message);
        Iterable<Message> messages = messageRepository.findAll();
        model.put("messages", messages);
        return "main";
    }

    @PostMapping("/filter")
    public String filter(@RequestParam String text,
                         Map<String, Object> model) {
        List<Message> messageList = messageRepository.findByText(text);
        model.put("messages", messageList);
        return "main";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
//    @PostMapping("/login")
//    public String loginIn(@RequestParam String username,
//                          @RequestParam String password,
//                          Map<String, Object> model) {
//
//        return "home";
//    }
}
