package com.example.comminicator.controller;

import com.example.comminicator.common.request.SendMessageRequest;
import com.example.comminicator.exception.ChatException;
import com.example.comminicator.exception.UserException;
import com.example.comminicator.model.Chat;
import com.example.comminicator.model.Message;
import com.example.comminicator.model.User;
import com.example.comminicator.service.ChatService;
import com.example.comminicator.service.MessageService;
import com.example.comminicator.service.UserService;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;

@RestController
public class RealTimeChat {


    private SimpMessagingTemplate simpMessagingTemplate;

    private UserService userService;

    private MessageService messageService;

    private ChatService chatService;

    @MessageMapping("/message")
    @SendTo("/group/public")
    public Message receiveMessage(@Payload Message message){
        simpMessagingTemplate.convertAndSend("/group/"+message.getChat().getId().toString(), message);

        return message;
    }

    @MessageMapping("/chat/{groupId}")
    public Message sendToUser(@Payload SendMessageRequest req, @Header("Authorization") String jwt, @DestinationVariable String groupId) throws UserException, ChatException {
        User user=userService.findUserProfile(jwt);
        req.setUserId(user.getId());

        Chat chat=chatService.findChatById(Math.toIntExact(req.getChatId()));

        Message createdMessage = messageService.sendMessage(req);

        User reciverUser=reciver(chat, user);

        simpMessagingTemplate.convertAndSendToUser(groupId, "/private", createdMessage);

        return createdMessage;
    }

    public User reciver(Chat chat, User reqUser) {
        Iterator<User> iterator = chat.getUsers().iterator();

        User user1 = iterator.next(); // get the first user
        User user2 = iterator.next();

        if(user1.getId().equals(reqUser.getId())){
            return user2;
        }
        return user1;
    }



}

