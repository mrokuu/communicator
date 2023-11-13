package com.app.service;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

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
    void createChat_ChatExists_ReturnsExistingChat() throws UserException {
        Integer reqUserId = 1;
        Integer userId2 = 2;
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
    void createChat_UserNotFound_ThrowsUserException() throws UserException {
        Integer reqUserId = 1;
        Integer userId2 = 2;

        when(userService.findUserById(reqUserId)).thenThrow(new UserException("User not found"));

        assertThrows(UserException.class, () -> chatService.createChat(reqUserId, userId2, false));
    }

    @Test
    void testFindChatById_Found() throws ChatException {
        Integer chatId = 1;
        Chat chat = mock(Chat.class);

        when(chatRepository.findById(chatId)).thenReturn(Optional.of(chat));

        Chat result = chatService.findChatById(chatId);

        assertEquals(chat, result);
    }

    @Test
    void testFindChatById_NotFound() {
        Integer chatId = 1;
        when(chatRepository.findById(chatId)).thenReturn(Optional.empty());

        assertThrows(ChatException.class, () -> chatService.findChatById(chatId));
    }

}