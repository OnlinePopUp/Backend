package com.example.auth.service;

import com.example.auth.dto.ChatDto;
import com.example.auth.entity.Chat;
import com.example.auth.entity.User;
import com.example.auth.repository.ChatRepository;
import com.example.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final UserRepository userRepository;

    public void sendMessage(String sEmail, String rEmail, String content) {
        User ruser = userRepository.findById(rEmail).orElse(null);
        User suser = userRepository.findById(sEmail).orElse(null);

        if(ruser == null || suser == null)
            return;

        Chat chat = new Chat();
        chat.setSEmail(sEmail);
        chat.setREmail(rEmail);
        chat.setContent(content);

        chatRepository.save(chat);
        messagingTemplate.convertAndSendToUser(rEmail,"/queue/messages", chat);
    }

    public ResponseEntity<?> getChatHistory(String sEmail, String rEmail) {
        User ruser = userRepository.findById(rEmail).orElse(null);
        User suser = userRepository.findById(sEmail).orElse(null);

        if(ruser == null || suser == null)
            return ResponseEntity.badRequest().body("존재하지 않는 유저");

        List<Chat> chatList = chatRepository.findChatHistory(sEmail, rEmail);
        List<ChatDto> chatDtoList = new ArrayList<>();

        for(Chat chat : chatList) {
            ChatDto chatDto = new ChatDto();
            chatDto.setSEmail(chat.getSEmail());
            chatDto.setREmail(chat.getREmail());
            chatDto.setContent(chat.getContent());
            chatDto.setChatId(chat.getChatId());
            chatDto.setCreated(chat.getCreated());

            chatDtoList.add(chatDto);
        }
        return ResponseEntity.ok(chatDtoList);
    }
}
