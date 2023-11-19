package com.app.service;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.app.exception.ChatException;
import com.app.exception.UserException;
import com.app.modal.Chat;
import com.app.modal.User;
import com.app.repository.ChatRepository;
import com.app.common.request.GroupChatRequest;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ChatService {
	
	private UserService userService;
	
	private ChatRepository chatRepository;


	public Chat createChat(Long reqUserId, Long userId2, boolean isGroup) throws UserException {
		User reqUser=userService.findUserById(reqUserId);
		User user2 = userService.findUserById(userId2);

		Chat isChatExist = chatRepository.findSingleChatByUsersId(user2, reqUser);


		if(isChatExist!=null) {
			return isChatExist;
		}

		Chat chat= Chat.builder()
				.created_by(reqUser)
				.is_group(false)
				.build();


		chat.getUsers().add(reqUser);
		chat.getUsers().add(user2);

		return chatRepository.save(chat);
	}



public Chat findChatById(Long chatId) throws ChatException {
	return chatRepository.findById(chatId)
			.orElseThrow(() -> new ChatException("Chat not exist with id " + chatId));
}


	public List<Chat> findAllChatByUserId(Long userId) throws UserException {
		

		User user=userService.findUserById(userId);
		
		List<Chat> chats=chatRepository.findChatByUserId(user.getId());
		

		return chats;
	}
	

	public Chat deleteChat(Long chatId, Long userId) throws ChatException, UserException {

		User user=userService.findUserById(userId);
		Chat chat=findChatById(chatId);

		if((chat.getCreated_by().getId().equals(user.getId())) && !chat.getIs_group() ) {
			chatRepository.deleteById(chat.getId());

			return chat;
		}

		throw new ChatException("you dont have access to delete this chat");
	}





	public Chat createGroup(GroupChatRequest req,Long reqUserId) throws UserException {
		
		User userById=userService.findUserById(reqUserId);

		Chat chat=new Chat();
		chat.setCreated_by(userById);
		chat.getUsers().add(userById);
		chat.getAdmins().add(userById);
		chat.setChatName(req.getChat_name());
		chat.setChatImage(req.getChat_image());
		chat.setIs_group(true);


		for (Long userId : req.getUserIds()) {
			User user = userService.findUserById(userId);
			if (user != null) {
				chat.getUsers().add(user);
			} else {
				throw new UserException("User with id " + userId + " does not exist.");
			}
		}




		return chatRepository.save(chat);

	}



	public Chat addUserToGroup(Long userId, Long chatId) throws UserException, ChatException {
		
		Chat chat =findChatById(chatId);
		User user=userService.findUserById(userId);
		
		chat.getUsers().add(user);
		
		
		Chat updatedChat=chatRepository.save(chat);
		
		return updatedChat;
	}





	public Chat renameGroup(Long chatId, String groupName, Long reqUserId) throws ChatException, UserException {
		
		Chat chat=findChatById(chatId);
		User user=userService.findUserById(reqUserId);
		
		
		if(chat.getUsers().contains(user))
		chat.setChatName(groupName);
		
		return chatRepository.save(chat);
	}

	public Chat removeFromGroup(Long chatId, Long userId, Long reqUserId) throws UserException, ChatException {
		Chat chat = findChatById(chatId);
		User userToRemove = userService.findUserById(userId);
		User reqUser = userService.findUserById(reqUserId);

		if (userToRemove.getId().equals(reqUser.getId()) || chat.getAdmins().contains(reqUser)) {
			chat.getUsers().remove(userToRemove);
			return chatRepository.save(chat);
		} else {
			throw new ChatException("You do not have permission to remove this user.");
		}
	}

}
