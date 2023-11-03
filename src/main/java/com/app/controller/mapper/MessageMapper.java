package com.app.controller.mapper;

import java.util.ArrayList;
import java.util.List;

import com.app.dto.ChatDto;
import com.app.dto.MessageDto;
import com.app.dto.UserDto;
import com.app.modal.Message;

public class MessageMapper {
	
	
	public static MessageDto toMessageDto(Message message) {
		
		ChatDto chatDto= ChatMapper.toChatDto(message.getChat());
		UserDto userDto= UserMapper.toUserDTO(message.getUser());
		
		MessageDto messageDto=new MessageDto();
		messageDto.setId(message.getId());
		messageDto.setChat(chatDto);
		messageDto.setContent(message.getContent());
		messageDto.setIs_read(message.getIs_read());
		messageDto.setTimeStamp(message.getTimeStamp());
		messageDto.setUser(userDto);
//		messageDto.set
		
		return messageDto;
	}
	
	public static List<MessageDto> toMessageDtos(List<Message> messages){
		
		List<MessageDto> messageDtos=new ArrayList<>();
		
		for(Message message : messages) {
			MessageDto messageDto=toMessageDto(message);
			messageDtos.add(messageDto);
		}
		
		return messageDtos;
	}

}
