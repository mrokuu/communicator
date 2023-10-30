package com.example.comminicator.service;

import com.example.comminicator.common.request.GroupChatRequest;
import com.example.comminicator.exception.ChatException;
import com.example.comminicator.exception.UserException;
import com.example.comminicator.model.Chat;
import com.example.comminicator.model.User;
import com.example.comminicator.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final UserService userService;
    private final ChatRepository chatRepository;
    private final Logger logger = LoggerFactory.getLogger(ChatService.class);



    public Chat createChat(Integer reqUserId, Integer userId2, boolean isGroup) throws UserException {
        User reqUser = userService.findUserById(reqUserId);
        User user2 = userService.findUserById(userId2);

        Chat existingChat = chatRepository.findSingleChatByUsersId(user2, reqUser);
        if (existingChat != null) {
            return existingChat;
        }

        Chat newChat = new Chat();
        newChat.setCreated_by(reqUser);
        newChat.getUsers().add(reqUser);
        newChat.getUsers().add(user2);
        newChat.setIs_group(false);

        return chatRepository.save(newChat);
    }


    public Chat findChatById(Integer chatId) throws ChatException {
        Optional<Chat> chat = chatRepository.findById(Long.valueOf(chatId));
        return chat.orElseThrow(() -> new ChatException("Chat not exist with id " + chatId));
    }


    public List<Chat> findAllChatByUserId(Integer userId) throws UserException {
        User user = userService.findUserById(userId);
        return chatRepository.findChatByUserId(Math.toIntExact(user.getId()));
    }


    public Chat deleteChat(Integer chatId, Integer userId) throws ChatException, UserException {
        User user = userService.findUserById(userId);
        Chat chat = findChatById(chatId);

        if ((chat.getCreated_by().getId().equals(user.getId())) && !chat.getIs_group()) {
            chatRepository.deleteById(chat.getId());
            return chat;
        }

        throw new ChatException("You don't have access to delete this chat");
    }


    public Chat createGroup(GroupChatRequest req, Integer reqUserId) throws UserException {
        User reqUser = userService.findUserById(reqUserId);
        Chat groupChat = new Chat();

        groupChat.setCreated_by(reqUser);
        groupChat.getUsers().add(reqUser);
        groupChat.setChatName(req.getChatName());
        groupChat.setChatImage(req.getChatImage());
        groupChat.setIs_group(true);
        groupChat.getAdmins().add(reqUser);

        for (Integer userId : req.getUserIds()) {
            User user = userService.findUserById(userId);
            if (user != null) {
                groupChat.getUsers().add(user);
            }
        }

        return chatRepository.save(groupChat);
    }


    public Chat addUserToGroup(Integer userId, Integer chatId) throws UserException, ChatException {
        Chat chat = findChatById(chatId);
        User user = userService.findUserById(userId);

        chat.getUsers().add(user);

        return chatRepository.save(chat);
    }


    public Chat renameGroup(Integer chatId, String groupName, Integer reqUserId) throws ChatException, UserException {
        Chat chat = findChatById(chatId);
        User user = userService.findUserById(reqUserId);

        if (chat.getUsers().contains(user) && chat.getAdmins().contains(user)) {
            chat.setChatName(groupName);
        } else {
            throw new ChatException("User does not have permission to rename the group");
        }

        return chatRepository.save(chat);
    }


    public Chat removeFromGroup(Integer chatId, Integer userId, Integer reqUserId) throws UserException, ChatException {
        Chat chat = findChatById(chatId);
        User userToRemove = userService.findUserById(userId);
        User requestingUser = userService.findUserById(reqUserId);

        if (userToRemove.equals(requestingUser) || chat.getAdmins().contains(requestingUser)) {
            chat.getUsers().remove(userToRemove);
            return chatRepository.save(chat);
        } else {
            throw new ChatException("User does not have permission to remove from group");
        }
    }
}
