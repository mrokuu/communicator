package com.app.common.request;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginRequest {
	
	private String email;
	private String password;
	
	

	

}
