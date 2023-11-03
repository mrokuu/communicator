package com.example.comminicator.controller.mapper;

import com.example.comminicator.dto.ChatDto;
import com.example.comminicator.dto.MessageDto;
import com.example.comminicator.dto.UserDto;
import com.example.comminicator.model.Chat;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ChatMapper {

    public static ChatDto toChatDto(Chat chat) {

        UserDto userDto=UserDtoMapper.toUserDTO(chat.getCreatedBy());
        List<MessageDto> messageDtos=MessageMapper.toMessageDtos(chat.getMessages());
        Set<UserDto> userDtos=UserDtoMapper.toUserDtos(chat.getUsers());
        Set<UserDto> admins=UserDtoMapper.toUserDtos(chat.getAdmins());

        ChatDto chatDto=new ChatDto();
        chatDto.setId(chat.getId());
        chatDto.setChatImage(chat.getChatImage());
        chatDto.setChatName(chat.getChatName());
        chatDto.setCreated_by(userDto);
        chatDto.setIs_group(chat.getIsGroup());
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
