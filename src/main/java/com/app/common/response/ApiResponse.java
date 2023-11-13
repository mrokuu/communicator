package com.app.common.response;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApiResponse {
	
	private String message;
	private boolean status;
	


}
