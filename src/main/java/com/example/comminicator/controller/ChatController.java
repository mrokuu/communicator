package com.example.comminicator.controller;

import com.example.comminicator.common.request.GroupChatRequest;
import com.example.comminicator.common.request.RenameGroupRequest;
import com.example.comminicator.common.request.SingleChatRequest;
import com.example.comminicator.controller.mapper.ChatMapper;
import com.example.comminicator.dto.ChatDto;
import com.example.comminicator.exception.ChatException;
import com.example.comminicator.exception.UserException;
import com.example.comminicator.model.Chat;
import com.example.comminicator.service.ChatService;
import com.example.comminicator.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
public class ChatController {


    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    private final ChatService chatService;
    private final UserService userService;



    @PostMapping("/single")
    public ResponseEntity<ChatDto> createChatHandler(@RequestBody SingleChatRequest singleChatRequest,
                                                     @RequestHeader("Authorization") String jwt) throws UserException {
        logger.info("Creating single chat");
        Chat chat = chatService.createChat(Math.toIntExact(userService.findUserProfile(jwt).getId()), Math.toIntExact(singleChatRequest.getUserId()), false);
        return ResponseEntity.status(HttpStatus.CREATED).body(ChatMapper.toChatDto(chat));
    }

    @PostMapping("/group")
    public ResponseEntity<ChatDto> createGroupHandler(@RequestBody GroupChatRequest groupChatRequest,
                                                      @RequestHeader("Authorization") String jwt) throws UserException {
        logger.info("Creating group chat");
        Chat chat = chatService.createGroup(groupChatRequest, Math.toIntExact(userService.findUserProfile(jwt).getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(ChatMapper.toChatDto(chat));
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<ChatDto> findChatByIdHandler(@PathVariable  Integer chatId) throws ChatException {
        logger.info("Fetching chat with ID: {}", chatId);
        Chat chat = chatService.findChatById(chatId);
        return ResponseEntity.ok(ChatMapper.toChatDto(chat));
    }

    @GetMapping("/user")
    public ResponseEntity<List<ChatDto>> findAllChatByUserIdHandler(@RequestHeader("Authorization") String jwt) throws UserException {
        logger.info("Fetching all chats for user");
        List<Chat> chats = chatService.findAllChatByUserId(Math.toIntExact(userService.findUserProfile(jwt).getId()));
        return ResponseEntity.ok(ChatMapper.toChatDtos(chats));
    }

    @PutMapping("/{chatId}/add/{userId}")
    public ResponseEntity<ChatDto> addUserToGroupHandler(@PathVariable  Integer chatId,
                                                         @PathVariable  Integer userId) throws UserException, ChatException {
        logger.info("Adding user {} to chat {}", userId, chatId);
        Chat chat = chatService.addUserToGroup(userId, chatId);
        return ResponseEntity.ok(ChatMapper.toChatDto(chat));
    }

    @PutMapping("/{chatId}/rename")
    public ResponseEntity<ChatDto> renameGroupHandler(@PathVariable  Integer chatId,
                                                      @RequestBody RenameGroupRequest renameGroupRequest,
                                                      @RequestHeader("Authorization") String jwt) throws ChatException, UserException {
        logger.info("Renaming chat {} to {}", chatId, renameGroupRequest.getGroupName());
        Chat chat = chatService.renameGroup(chatId, renameGroupRequest.getGroupName(), Math.toIntExact(userService.findUserProfile(jwt).getId()));
        return ResponseEntity.ok(ChatMapper.toChatDto(chat));
    }

    @PutMapping("/{chatId}/remove/{userId}")
    public ResponseEntity<ChatDto> removeFromGroupHandler(@PathVariable  Integer chatId,
                                                          @PathVariable  Integer userId,
                                                          @RequestHeader("Authorization") String jwt) throws UserException, ChatException {
        logger.info("Removing user {} from chat {}", userId, chatId);
        Chat chat = chatService.removeFromGroup(chatId, userId, Math.toIntExact(userService.findUserProfile(jwt).getId()));
        return ResponseEntity.ok(ChatMapper.toChatDto(chat));
    }

    @DeleteMapping("/{chatId}/{userId}")
    public ResponseEntity<ChatDto> deleteChatHandler(@PathVariable  Integer chatId,
                                                     @PathVariable  Integer userId) throws ChatException, UserException {
        logger.info("Deleting chat {} for user {}", chatId, userId);
        Chat chat = chatService.deleteChat(chatId, userId);
        return ResponseEntity.ok(ChatMapper.toChatDto(chat));
    }
}
