package com.app.response;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthResponse {
	
	private String jwt;
	
	private boolean status;
	

}
