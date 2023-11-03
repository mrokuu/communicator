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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageService {

    @Autowired
    private final MessageRepository messageRepository;

    @Autowired
    private final UserService userService;

    @Autowired
    private final ChatService chatService;




    public Message sendMessage(SendMessageRequest req) throws UserException, ChatException {

        User user=userService.findUserById(req.getUserId());
        Chat chat=chatService.findChatById(req.getChatId());

        Message message=new Message();
        message.setChat(chat);
        message.setUser(user);
        message.setContent(req.getContent());
        message.setTimeStamp(LocalDateTime.now());
        message.setIs_read(false);


        return messageRepository.save(message);
    }


    public String deleteMessage(Integer messageId) throws MessageException {

        Message message=findMessageById(messageId);

        messageRepository.deleteById(message.getId());

        return "message deleted successfully";
    }


    public List<Message> getChatsMessages(Integer chatId) throws ChatException {

        Chat chat=chatService.findChatById(chatId);

        List<Message> messages=messageRepository.findMessageByChatId(chatId);

        return messages;
    }


    public Message findMessageById(Integer messageId) throws MessageException {

        Optional<Message> message =messageRepository.findById(messageId);

        if(message.isPresent()) {
            return message.get();
        }
        throw new MessageException("message not exist with id "+messageId);
    }

}
