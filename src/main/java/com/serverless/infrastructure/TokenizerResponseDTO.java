package com.serverless.infrastructure;

public class TokenizerResponseDTO {

    private String token;

    private TokenizerResponseDTO(String token) {
        this.token = token;
    }

    public static TokenizerResponseDTO from(String token) {
        return new TokenizerResponseDTO(token);
    }

    public String getToken() {
        return token;
    }
}
