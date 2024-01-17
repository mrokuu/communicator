package com.app.common.request;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateUserRequest {
	
	private String fullName;
	private String profilePicture;


}
