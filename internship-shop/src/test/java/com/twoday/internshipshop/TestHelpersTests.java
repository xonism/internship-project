package com.twoday.internshipshop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.twoday.internshipmodel.OrderCreateRequest;
import com.twoday.internshipshop.utils.JsonUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TestHelpersTests {

    @Test
    void givenValidObject_whenGetObjectAsJsonString_thenObjectJsonStringIsReturned() throws JsonProcessingException {
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest(1, 1);

        String actualResult = JsonUtils.getObjectAsJsonString(orderCreateRequest);
        String expectedResult = "{\"productId\":1,\"quantity\":1}";

        assertThat(actualResult).isEqualTo(expectedResult);
    }
}
