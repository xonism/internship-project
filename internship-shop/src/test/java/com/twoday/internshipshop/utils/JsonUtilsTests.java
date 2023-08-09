package com.twoday.internshipshop.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.twoday.internshipmodel.OrderCreateRequest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class JsonUtilsTests {

    @Test
    void givenValidObject_whenGetObjectAsJsonString_thenObjectJsonStringIsReturned() throws JsonProcessingException {
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest(1, 1);

        String actualResult = JsonUtils.getObjectAsJsonString(orderCreateRequest);
        String expectedResult = "{\"productId\":1,\"quantity\":1}";

        assertThat(actualResult).isEqualTo(expectedResult);
    }
}
