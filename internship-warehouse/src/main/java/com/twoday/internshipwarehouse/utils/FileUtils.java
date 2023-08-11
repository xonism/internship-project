package com.twoday.internshipwarehouse.utils;

import com.twoday.internshipwarehouse.constants.Constants;

import java.time.LocalDateTime;

public class FileUtils {

    private FileUtils() {

    }

    public static String getOrderReportFilePath(LocalDateTime startDateTime) {
        return String.format(Constants.REPORT_FILE_NAME_FORMAT, Constants.REPORTS_DIR, startDateTime)
                .replace(":", "-");
    }
}
