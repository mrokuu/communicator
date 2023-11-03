package com.app.controller.mapper;



import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.app.dto.ChatDto;
import com.app.dto.MessageDto;
import com.app.dto.UserDto;
import com.app.modal.Chat;

public class ChatMapper {
	
	public static ChatDto toChatDto(Chat chat) {
		
		UserDto userDto= UserMapper.toUserDTO(chat.getCreated_by());
		List<MessageDto> messageDtos= MessageMapper.toMessageDtos(chat.getMessages());
		Set<UserDto> userDtos= UserMapper.toUserDtos(chat.getUsers());
		Set<UserDto> admins= UserMapper.toUserDtos(chat.getAdmins());
		
		ChatDto chatDto=new ChatDto();
		chatDto.setId(chat.getId());
		chatDto.setChat_image(chat.getChat_image());
		chatDto.setChat_name(chat.getChat_name());
		chatDto.setCreated_by(userDto);
		chatDto.setIs_group(chat.getIs_group());
		chatDto.setMessages(messageDtos);
		chatDto.setUsers(userDtos);
		chatDto.setAdmins(admins);
		
		
		return chatDto;
	}
	
	public static List<ChatDto> toChatDtos(List<Chat> chats){
		
		List<ChatDto> chatDtos=new ArrayList<>();
		
		for(Chat chat:chats) {
			ChatDto chatDto=toChatDto(chat);
			chatDtos.add(chatDto);
		}
		
		return chatDtos;
	}

}
