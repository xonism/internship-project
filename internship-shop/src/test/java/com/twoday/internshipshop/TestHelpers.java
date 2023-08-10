package com.twoday.internshipshop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestHelpers {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private TestHelpers() {

    }

    public static String getObjectAsJsonString(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
