package com.twoday.internshipwarehouse.utils;

import com.twoday.internshipwarehouse.constants.Constants;

import java.time.LocalDateTime;

public class FileUtils {

    private FileUtils() {

    }

    public static String getOrderReportFilePath(String directory, LocalDateTime startDateTime) {
        return String.format(Constants.REPORT_FILE_NAME_FORMAT, directory, startDateTime)
                .replace(":", "-");
    }
}
