package com.app.request;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateUserRequest {
	
	private String full_name;
	private String profile_picture;


}
