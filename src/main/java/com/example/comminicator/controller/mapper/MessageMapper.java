package com.example.comminicator.controller.mapper;

import com.example.comminicator.dto.ChatDto;
import com.example.comminicator.dto.MessageDto;
import com.example.comminicator.dto.UserDto;
import com.example.comminicator.model.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageMapper {

    public static MessageDto toMessageDto(Message message) {

        ChatDto chatDto=ChatMapper.toChatDto(message.getChat());
        UserDto userDto=UserDtoMapper.toUserDTO(message.getUser());

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
