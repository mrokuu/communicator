package com.app.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.exception.ChatException;
import com.app.exception.MessageException;
import com.app.exception.UserException;
import com.app.modal.Chat;
import com.app.modal.Message;
import com.app.modal.User;
import com.app.repository.MessageRepository;
import com.app.request.SendMessageRequest;

@Service
@AllArgsConstructor
public class MessageService {
	

	private MessageRepository messageRepository;
	

	private UserService userService;
	

	private ChatService chatService;
	
	  


	public Message sendMessage(SendMessageRequest req) throws UserException, ChatException {
		

		User userById=userService.findUserById(req.getUserId());
		Chat chatById=chatService.findChatById(req.getChatId());

		Message message = Message.builder()
				.chat(chatById)
				.user(userById)
				.content(req.getContent())
				.timeStamp(LocalDateTime.now())
				.is_read(false)
				.build();
		
//		Message message=new Message();
//		message.setChat(chat);
//		message.setUser(user);
//		message.setContent(req.getContent());
//		message.setTimeStamp(LocalDateTime.now());
//		message.setIs_read(false);

		
		
		return messageRepository.save(message);
	}


	public void deleteMessage(Integer messageId) throws MessageException {
		
		Message message=findMessageById(messageId);

		messageRepository.deleteById(message.getId());

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
