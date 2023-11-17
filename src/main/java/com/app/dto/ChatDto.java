package com.app.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChatDto {

	private Integer id;
	private String chatName;
	private String chatImage;
	
	@Column(columnDefinition = "boolean default false")
	private Boolean isGroup;
	
	private Set<UserDto> admins= new HashSet<>();
	
	private UserDto createdBy;

	private Set<UserDto> users = new HashSet<>();
	
	private List<MessageDto> messages=new ArrayList<>();



}
