package com.example.discussit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RefreshTokenRequest {
    @NotBlank
    private String refreshToken;
    private String username;
}
