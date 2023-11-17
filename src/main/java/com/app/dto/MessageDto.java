package com.app.dto;

import lombok.*;

import java.time.LocalDateTime;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MessageDto {

private String content;
	

	private Integer id;
	private LocalDateTime timeStamp;
	private Boolean isRead;
	private UserDto user;
	private ChatDto chat;
	

	
}
