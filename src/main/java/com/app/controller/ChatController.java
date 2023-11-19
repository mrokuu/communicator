package com.app.controller;

import java.util.List;

import com.app.repository.ChatRepository;
import com.app.service.ChatService;
import com.app.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.controller.mapper.ChatMapper;
import com.app.dto.ChatDto;
import com.app.exception.ChatException;
import com.app.exception.UserException;
import com.app.modal.Chat;
import com.app.modal.User;
import com.app.common.request.GroupChatRequest;
import com.app.common.request.RenameGroupRequest;
import com.app.common.request.SingleChatRequest;

@RestController
@RequestMapping("/api/chats")
@AllArgsConstructor
public class ChatController {

	private ChatRepository chatRepository;
	
	private ChatService chatService;
	
	private UserService userService;
	
	@PostMapping("/single")
	public ResponseEntity<ChatDto> creatChatHandler(@RequestBody SingleChatRequest singleChatRequest, @RequestHeader("Authorization")  String jwt) throws UserException{
		
		User reqUser=userService.findUserProfile(jwt);
		
		Chat chat=chatService.createChat(reqUser.getId(),singleChatRequest.getUserId(),false);
		ChatDto chatDto= ChatMapper.toChatDto(chat);
		
		return new ResponseEntity<ChatDto>(chatDto,HttpStatus.OK);
	}
	
	@PostMapping("/group")
	public ResponseEntity<ChatDto> createGroupHandler(@RequestBody GroupChatRequest groupChatRequest, @RequestHeader("Authorization") String jwt) throws UserException{
		
		User reqUser=userService.findUserProfile(jwt);
		
		Chat chat=chatService.createGroup(groupChatRequest, reqUser.getId());
		ChatDto chatDto= ChatMapper.toChatDto(chat);
		
		return new ResponseEntity<ChatDto>(chatDto,HttpStatus.OK);
		
	}
	
	@GetMapping("/{chatId}")
	public ResponseEntity<ChatDto> findChatByIdHandler(@PathVariable Long chatId) throws ChatException{
		
		Chat chat =chatService.findChatById(chatId);
		
		ChatDto chatDto= ChatMapper.toChatDto(chat);
		
		return new ResponseEntity<ChatDto>(chatDto,HttpStatus.OK);
		
	}
	
	@GetMapping("/user")
	public ResponseEntity<List<ChatDto>> findAllChatByUserIdHandler(@RequestHeader("Authorization")String jwt) throws UserException{
		
		User user=userService.findUserProfile(jwt);
		
		List<Chat> chats=chatService.findAllChatByUserId(user.getId());
		
		List<ChatDto> chatDtos= ChatMapper.toChatDtos(chats);
		
		return new ResponseEntity<List<ChatDto>>(chatDtos,HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/{chatId}/add/{userId}")
	public ResponseEntity<ChatDto> addUserToGroupHandler(@PathVariable Long chatId,@PathVariable Long userId) throws UserException, ChatException{
		
		Chat chat=chatService.addUserToGroup(userId, chatId);
		
		ChatDto chatDto= ChatMapper.toChatDto(chat);
		
		return new ResponseEntity<ChatDto>(chatDto,HttpStatus.OK);
	}
	
	@PutMapping("/{chatId}/rename")
	public ResponseEntity<ChatDto> renameGroupHandler(@PathVariable Long chatId,@RequestBody RenameGroupRequest renameGoupRequest, @RequestHeader("Autorization") String jwt) throws ChatException, UserException{
		
		User reqUser=userService.findUserProfile(jwt);
		
		Chat chat =chatService.renameGroup(chatId, renameGoupRequest.getGroupName(), reqUser.getId());
		
		ChatDto chatDto= ChatMapper.toChatDto(chat);
		
		return new ResponseEntity<ChatDto>(chatDto,HttpStatus.OK);
	}
	
	@PutMapping("/{chatId}/remove/{userId}")
	public ResponseEntity<ChatDto> removeFromGroupHandler(@RequestHeader("Authorization") String jwt, @PathVariable Long chatId,@PathVariable Long userId) throws UserException, ChatException{
		
		User reqUser=userService.findUserProfile(jwt);
		
		Chat chat=chatService.removeFromGroup(chatId, userId, reqUser.getId());
		
		ChatDto chatDto= ChatMapper.toChatDto(chat);
		
		return new ResponseEntity<ChatDto>(chatDto,HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{chatId}/{userId}")
	public ResponseEntity<ChatDto> deleteChatHandler(@PathVariable Long chatId, @PathVariable Long userId) throws ChatException, UserException{
		
		Chat chat=chatService.deleteChat(chatId, userId);
		ChatDto chatDto= ChatMapper.toChatDto(chat);
		
		return new ResponseEntity<ChatDto>(chatDto,HttpStatus.OK);
	}
}
