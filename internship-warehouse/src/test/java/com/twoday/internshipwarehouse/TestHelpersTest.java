package com.twoday.internshipwarehouse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.twoday.internshipmodel.OrderCreateRequest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TestHelpersTest {

    @Test
    void givenValidObject_whenGetObjectAsJsonString_thenObjectJsonStringIsReturned() throws JsonProcessingException {

        OrderCreateRequest orderCreateRequest =
                new OrderCreateRequest(1, 1, new BigDecimal("1.1"));

        String actualResult = TestHelpers.getObjectAsJsonString(orderCreateRequest);
        String expectedResult = "{\"productId\":1,\"quantity\":1,\"unitPrice\":1.1}";

        assertThat(actualResult).isEqualTo(expectedResult);
    }
}
