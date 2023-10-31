package com.example.comminicator.service;

import com.example.comminicator.common.request.SendMessageRequest;
import com.example.comminicator.exception.ChatException;
import com.example.comminicator.exception.MessageException;
import com.example.comminicator.exception.UserException;
import com.example.comminicator.model.Chat;
import com.example.comminicator.model.Message;
import com.example.comminicator.model.User;
import com.example.comminicator.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserService userService;
    private final ChatService chatService;



    public Message sendMessage(SendMessageRequest req) throws UserException, ChatException {
        User user = userService.findUserById(Math.toIntExact(req.getUserId()));
        Chat chat = chatService.findChatById(Math.toIntExact(req.getChatId()));


        Message message = new Message();
        message.setChat(chat);
        message.setUser(user);
        message.setContent(req.getContent());
        message.setTimeStamp(LocalDateTime.now());
        message.setIs_read(false);

        return messageRepository.save(message);
    }


    public void deleteMessage(Integer messageId) throws MessageException {
        Message message = findMessageById(messageId);


        messageRepository.deleteById(message.getId());
    }


    public List<Message> getChatsMessages(Integer chatId) throws ChatException {
        Chat chat = chatService.findChatById(chatId);
        return messageRepository.findMessageByChatId(chatId);
    }


    public Message findMessageById(Integer messageId) throws MessageException {
        return messageRepository.findById(Long.valueOf(messageId))
                .orElseThrow(() -> new MessageException("Message not found with ID: " + messageId));
    }
}
