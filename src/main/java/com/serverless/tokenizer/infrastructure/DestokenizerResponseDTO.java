package com.serverless.tokenizer.infrastructure;

public class DestokenizerResponseDTO {
    
    private String pan;

    private DestokenizerResponseDTO(String pan) {
        this.pan = pan;
    }

    public static DestokenizerResponseDTO from(String pan) {
        return new DestokenizerResponseDTO(pan);
    }

    public String getPan() {
        return pan;
    }
}
