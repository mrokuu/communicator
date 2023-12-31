package com.app.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.app.exception.ChatException;
import com.app.exception.MessageException;
import com.app.exception.UserException;
import com.app.modal.Chat;
import com.app.modal.Message;
import com.app.modal.User;
import com.app.repository.MessageRepository;
import com.app.common.request.SendMessageRequest;
import org.springframework.transaction.annotation.Transactional;

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
				.isRead(false)
				.build();

		
		
		return messageRepository.save(message);
	}

	public void deleteMessage(Long messageId) throws MessageException {
		
		Message message=findMessageById(messageId);

		messageRepository.deleteById(message.getId());

	}


	public List<Message> getChatsMessages(Long chatId) throws ChatException {
		return messageRepository.findMessageByChatId(chatId);
	}



public Message findMessageById(Long messageId) throws MessageException {
	return messageRepository.findById(messageId)
			.orElseThrow(() -> new MessageException("Message not exist with id " + messageId));
}

}
