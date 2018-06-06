package com.serverless.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

public class ObjectMapper extends com.fasterxml.jackson.databind.ObjectMapper {

    @Override
    public String writeValueAsString(Object value) {
        try {
            return super.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T readValue(String content, Class<T> valueType) {
        try {
            return super.readValue(content, valueType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
