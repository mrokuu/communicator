package com.app.service;

import com.app.exception.ChatException;
import com.app.exception.MessageException;
import com.app.exception.UserException;
import com.app.modal.Chat;
import com.app.modal.Message;
import com.app.modal.User;
import com.app.repository.MessageRepository;
import com.app.request.SendMessageRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
        req.setUserId(1);
        req.setChatId(1);
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
        assertFalse(sentMessage.getIs_read());
    }

    @Test
    void deleteMessageTest() throws MessageException {
        Integer messageId = 1;
        Message message = new Message(); // Assuming Message has an empty constructor
        message.setId(messageId);

        when(messageRepository.findById(messageId)).thenReturn(Optional.of(message));
        doNothing().when(messageRepository).deleteById(messageId);

        messageService.deleteMessage(messageId);

        verify(messageRepository, times(1)).deleteById(messageId);
    }


    @Test
    void findMessageByIdTest_ExistingMessage() throws MessageException {
        Integer messageId = 1;
        Message message = new Message(); // Assuming Message has an empty constructor

        when(messageRepository.findById(messageId)).thenReturn(Optional.of(message));

        Message result = messageService.findMessageById(messageId);

        assertEquals(message, result);
    }

    @Test
    void findMessageByIdTest_MessageNotFound() {
        Integer messageId = 1;

        when(messageRepository.findById(messageId)).thenReturn(Optional.empty());

        assertThrows(MessageException.class, () -> messageService.findMessageById(messageId));
    }

}