package com.app.common.response;

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
