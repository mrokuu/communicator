package com.app.service;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.app.common.request.GroupChatRequest;
import org.junit.jupiter.api.Test;
import com.app.exception.ChatException;
import com.app.exception.UserException;
import com.app.modal.Chat;
import com.app.modal.User;
import com.app.repository.ChatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class ChatServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private ChatRepository chatRepository;

    @InjectMocks
    private ChatService chatService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createChatChatExistsReturnsExistingChat() throws UserException {
        Long reqUserId = 1L;
        Long userId2 = 2L;
        User reqUser = mock(User.class);
        User user2 = mock(User.class);
        Chat existingChat = new Chat();

        when(userService.findUserById(reqUserId)).thenReturn(reqUser);
        when(userService.findUserById(userId2)).thenReturn(user2);
        when(chatRepository.findSingleChatByUsersId(user2, reqUser)).thenReturn(existingChat);

        Chat result = chatService.createChat(reqUserId, userId2, false);

        assertSame(existingChat, result);
    }

    @Test
    void createChatUserNotFoundThrowsUserException() throws UserException {
        Long reqUserId = 1L;
        Long userId2 = 2L;

        when(userService.findUserById(reqUserId)).thenThrow(new UserException("User not found"));

        assertThrows(UserException.class, () -> chatService.createChat(reqUserId, userId2, false));
    }

    @Test
    void testFindChatByIdFound() throws ChatException {
        Long chatId = 1L;
        Chat chat = mock(Chat.class);

        when(chatRepository.findById(chatId)).thenReturn(Optional.of(chat));

        Chat result = chatService.findChatById(chatId);

        assertEquals(chat, result);
    }

    @Test
    void testFindChatById_NotFound() {
        Long chatId = 1L;
        when(chatRepository.findById(chatId)).thenReturn(Optional.empty());

        assertThrows(ChatException.class, () -> chatService.findChatById(chatId));
    }




    @Test
    public void testCreateChatChatAlreadyExists() throws UserException {
        User user1 = new User(/* ... */);
        User user2 = new User(/* ... */);
        Chat existingChat = new Chat(/* ... */);

        when(userService.findUserById(1L)).thenReturn(user1);
        when(userService.findUserById(2L)).thenReturn(user2);
        when(chatRepository.findSingleChatByUsersId(user1, user2)).thenReturn(existingChat);

        Chat result = chatService.createChat(1L, 2L, false);

        assertEquals(existingChat, result);
    }

    @Test
    void testFindChatByIdSuccess() throws ChatException {
        Long chatId = 1L;
        Chat expectedChat = new Chat(); // Initialize with necessary fields
        when(chatRepository.findById(chatId)).thenReturn(Optional.of(expectedChat));

        Chat resultChat = chatService.findChatById(chatId);

        assertNotNull(resultChat);
        assertEquals(expectedChat, resultChat);
    }






}