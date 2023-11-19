package com.app.service;

import com.app.exception.ChatException;
import com.app.exception.MessageException;
import com.app.exception.UserException;
import com.app.modal.Chat;
import com.app.modal.Message;
import com.app.modal.User;
import com.app.repository.MessageRepository;
import com.app.common.request.SendMessageRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class MessageServiceTest {
    @Mock
    private MessageRepository messageRepository;

    @Mock
    private UserService userService;

    @Mock
    private ChatService chatService;

    @InjectMocks
    private MessageService messageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendMessageTest() throws UserException, ChatException {
        SendMessageRequest req = new SendMessageRequest();
        req.setUserId(1L);
        req.setChatId(1L);
        req.setContent("Test Message");

        User user = new User(); // Assuming User has an empty constructor
        Chat chat = new Chat(); // Assuming Chat has an empty constructor

        when(userService.findUserById(req.getUserId())).thenReturn(user);
        when(chatService.findChatById(req.getChatId())).thenReturn(chat);
        when(messageRepository.save(any(Message.class))).thenAnswer(i -> i.getArguments()[0]);

        Message sentMessage = messageService.sendMessage(req);

        assertEquals(req.getContent(), sentMessage.getContent());
        assertEquals(user, sentMessage.getUser());
        assertEquals(chat, sentMessage.getChat());
        assertNotNull(sentMessage.getTimeStamp());
        assertFalse(sentMessage.getIsRead());
    }

    @Test
    void deleteMessageTest() throws MessageException {
        Long messageId = 1L;
        Message message = new Message(); // Assuming Message has an empty constructor
        message.setId(messageId);

        when(messageRepository.findById(messageId)).thenReturn(Optional.of(message));
        doNothing().when(messageRepository).deleteById(messageId);

        messageService.deleteMessage(messageId);

        verify(messageRepository, times(1)).deleteById(messageId);
    }


    @Test
    void findMessageByIdTestExistingMessage() throws MessageException {
        Long messageId = 1L;
        Message message = new Message(); // Assuming Message has an empty constructor

        when(messageRepository.findById(messageId)).thenReturn(Optional.of(message));

        Message result = messageService.findMessageById(messageId);

        assertEquals(message, result);
    }

    @Test
    void findMessageByIdTestMessageNotFound() {
        Long messageId = 1L;

        when(messageRepository.findById(messageId)).thenReturn(Optional.empty());

        assertThrows(MessageException.class, () -> messageService.findMessageById(messageId));
    }

    @Test
    void testSendMessage() throws UserException, ChatException {
        // Arrange
        SendMessageRequest request = new SendMessageRequest(/* parameters */);
        User mockUser = new User(/* parameters */);
        Chat mockChat = new Chat(/* parameters */);
        Message mockMessage = new Message(/* parameters */);

        when(userService.findUserById(anyLong())).thenReturn(mockUser);
        when(chatService.findChatById(anyLong())).thenReturn(mockChat);
        when(messageRepository.save(any(Message.class))).thenReturn(mockMessage);

        // Act
        Message result = messageService.sendMessage(request);

        // Assert
        assertNotNull(result);
        assertEquals(mockMessage, result);
    }



    @Test
    void testGetChatsMessages() throws ChatException {
        // Arrange
        Long chatId = 1L;
        List<Message> mockMessages = Arrays.asList(new Message(/* parameters */));

        when(messageRepository.findMessageByChatId(chatId)).thenReturn(mockMessages);

        // Act
        List<Message> result = messageService.getChatsMessages(chatId);

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        // Additional assertions as needed
    }

    @Test
    void testFindMessageById() throws MessageException {
        // Arrange
        Long messageId = 1L;
        Message mockMessage = new Message(/* parameters */);

        when(messageRepository.findById(messageId)).thenReturn(Optional.of(mockMessage));

        // Act
        Message result = messageService.findMessageById(messageId);

        // Assert
        assertNotNull(result);
        assertEquals(mockMessage, result);
    }

    // ... [Previous Test Code] ...




    @Test
    void testDeleteMessageNotFound() {
        // Arrange
        Long messageId = 1L;

        when(messageRepository.findById(messageId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(MessageException.class, () -> messageService.deleteMessage(messageId));
    }

    @Test
    void testGetChatsMessagesEmptyChat() throws ChatException {
        // Arrange
        Long chatId = 1L;

        when(messageRepository.findMessageByChatId(chatId)).thenReturn(Collections.emptyList());

        // Act
        List<Message> result = messageService.getChatsMessages(chatId);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindMessageByIdNotFound() {
        // Arrange
        Long messageId = 1L;

        when(messageRepository.findById(messageId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(MessageException.class, () -> messageService.findMessageById(messageId));
    }



}