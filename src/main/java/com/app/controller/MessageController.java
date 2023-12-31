package com.app.controller;

import java.util.List;

import com.app.service.MessageService;
import com.app.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.controller.mapper.MessageMapper;
import com.app.dto.MessageDto;
import com.app.exception.ChatException;
import com.app.exception.MessageException;
import com.app.exception.UserException;
import com.app.modal.Message;
import com.app.modal.User;
import com.app.common.request.SendMessageRequest;
import com.app.common.response.ApiResponse;

@RestController
@RequestMapping("/api/messages")
@AllArgsConstructor
public class MessageController {

	private MessageService messageService;
	

	private UserService userService;
	
	@PostMapping("/create")
	public ResponseEntity<MessageDto> sendMessageHandler(@RequestHeader("Authorization")String jwt,  @RequestBody SendMessageRequest req) throws UserException, ChatException{
		
		User reqUser=userService.findUserProfile(jwt);
		
		req.setUserId(reqUser.getId());
		
		Message message=messageService.sendMessage(req);
		
		MessageDto messageDto= MessageMapper.toMessageDto(message);
		
		return new ResponseEntity<MessageDto>(messageDto,HttpStatus.OK);
	}
	
	@GetMapping("/chat/{chatId}")
	public ResponseEntity<List<MessageDto>> getChatsMessageHandler(@PathVariable Long chatId) throws ChatException{
		
		List<Message> messages=messageService.getChatsMessages(chatId);
		
		List<MessageDto> messageDtos= MessageMapper.toMessageDtos(messages);
		
		return new ResponseEntity<List<MessageDto>>(messageDtos,HttpStatus.ACCEPTED);
		
	}
	
	@DeleteMapping("/{messageId}")
	public ResponseEntity<ApiResponse> deleteMessageHandler(@PathVariable Long messageId) throws MessageException{
		
		messageService.deleteMessage(messageId);
		
		ApiResponse res=new ApiResponse("message deleted successfully",true);
		
		return new ResponseEntity<ApiResponse>(res,HttpStatus.ACCEPTED);
	}
}
