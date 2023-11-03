package com.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.exception.ChatException;
import com.app.exception.UserException;
import com.app.modal.Chat;
import com.app.modal.User;
import com.app.repository.ChatRepository;
import com.app.request.GroupChatRequest;

@Service
public class ChatService {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ChatRepository chatRepo;


	public Chat createChat(Integer reqUserId, Integer userId2, boolean isGroup) throws UserException {
		
		
		
		User reqUser=userService.findUserById(reqUserId);
		User user2 = userService.findUserById(userId2);
		

		Chat isChatExist = chatRepo.findSingleChatByUsersId(user2, reqUser);
		

		if(isChatExist!=null) {
			return isChatExist;
		}
		
		Chat chat=new Chat();
		
		chat.setCreated_by(reqUser);
		chat.getUsers().add(reqUser);
		chat.getUsers().add(user2);
		chat.setIs_group(false);
		
		Chat createdChat = chatRepo.save(chat);
		
//		
		
		return createdChat;
	}

	
	


	public Chat findChatById(Integer chatId) throws ChatException {
		
		Optional<Chat> chat =chatRepo.findById(chatId);
		
		if(chat.isPresent()) {
			return chat.get();
		}
		throw new ChatException("Chat not exist with id "+chatId);
	}


	public List<Chat> findAllChatByUserId(Integer userId) throws UserException {
		

		User user=userService.findUserById(userId);
		
		List<Chat> chats=chatRepo.findChatByUserId(user.getId());
		

		return chats;
	}
	

	public Chat deleteChat(Integer chatId, Integer userId) throws ChatException, UserException {
		
		User user=userService.findUserById(userId);
		Chat chat=findChatById(chatId);
		
		if((chat.getCreated_by().getId().equals(user.getId())) && !chat.getIs_group() ) {
			chatRepo.deleteById(chat.getId());
			
			return chat;
		}
		
		throw new ChatException("you dont have access to delete this chat");
	}





	public Chat createGroup(GroupChatRequest req,Integer reqUserId) throws UserException {
		
		User reqUser=userService.findUserById(reqUserId);
		
		Chat chat=new Chat();
		
		chat.setCreated_by(reqUser);
		chat.getUsers().add(reqUser);
		
		for(Integer userId:req.getUserIds()) {
			User user =userService.findUserById(userId);
			if(user!=null)chat.getUsers().add(user);
		}
		
		chat.setChat_name(req.getChat_name());
		chat.setChat_image(req.getChat_image());
		chat.setIs_group(true);
		chat.getAdmins().add(reqUser);
		
		return chatRepo.save(chat);
		
	}



	public Chat addUserToGroup(Integer userId, Integer chatId) throws UserException, ChatException {
		
		Chat chat =findChatById(chatId);
		User user=userService.findUserById(userId);
		
		chat.getUsers().add(user);
		
		
		Chat updatedChat=chatRepo.save(chat);
		
		return updatedChat;
	}





	public Chat renameGroup(Integer chatId, String groupName, Integer reqUserId) throws ChatException, UserException {
		
		Chat chat=findChatById(chatId);
		User user=userService.findUserById(reqUserId);
		
		
		if(chat.getUsers().contains(user))
		chat.setChat_name(groupName);
		
		return chatRepo.save(chat);
	}

	public Chat removeFromGroup(Integer chatId, Integer userId, Integer reqUserId) throws UserException, ChatException {
		Chat chat=findChatById(chatId);
		User user=userService.findUserById(userId);
		
		User reqUser=userService.findUserById(reqUserId);
		
		if(user.getId().equals(reqUser.getId()) ) {
			chat.getUsers().remove(reqUser);
		}
		
		return null;
	}

}
