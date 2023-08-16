package com.twoday.internshipwarehouse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestHelpers {

    public static final String REPORTS_DIRECTORY = "./src/test/reports";

    public static final String EXPECTED_ORDER_REPORT_PATH = REPORTS_DIRECTORY + "/expected-order-report.csv";

    public static final String REPORT_LOCAL_DATE_TIME = "2023-08-15T20:00";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private TestHelpers() {

    }

    public static String getObjectAsJsonString(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
