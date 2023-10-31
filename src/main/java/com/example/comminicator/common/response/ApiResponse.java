package com.example.comminicator.common.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {

    private String message;
    private boolean status;
}
