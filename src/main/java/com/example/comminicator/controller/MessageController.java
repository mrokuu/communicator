package com.example.comminicator.controller;

import com.example.comminicator.common.request.SendMessageRequest;
import com.example.comminicator.common.response.ApiResponse;
import com.example.comminicator.controller.mapper.MessageMapper;
import com.example.comminicator.dto.MessageDto;
import com.example.comminicator.exception.ChatException;
import com.example.comminicator.exception.MessageException;
import com.example.comminicator.exception.UserException;
import com.example.comminicator.model.Message;
import com.example.comminicator.model.User;
import com.example.comminicator.service.MessageService;
import com.example.comminicator.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;


    @PostMapping("/create")
    public ResponseEntity<MessageDto> sendMessageHandler(@RequestHeader("Authorization") String jwt,
                                                         @RequestBody SendMessageRequest req)
            throws UserException, ChatException {

        User reqUser = userService.findUserProfile(jwt);
        req.setUserId(reqUser.getId());

        Message message = messageService.sendMessage(req);
        MessageDto messageDto = MessageMapper.toMessageDto(message);

        return new ResponseEntity<>(messageDto, HttpStatus.CREATED);
    }

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<MessageDto>> getChatsMessageHandler(@PathVariable Integer chatId)
            throws ChatException {

        List<Message> messages = messageService.getChatsMessages(chatId);
        List<MessageDto> messageDtos = MessageMapper.toMessageDtos(messages);

        return new ResponseEntity<>(messageDtos, HttpStatus.OK);
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<ApiResponse> deleteMessageHandler(@PathVariable Integer messageId)
            throws MessageException {

        messageService.deleteMessage(messageId);
        ApiResponse response = new ApiResponse("Message deleted successfully", true);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
