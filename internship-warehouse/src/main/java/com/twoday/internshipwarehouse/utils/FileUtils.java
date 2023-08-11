package com.twoday.internshipwarehouse.utils;

import com.twoday.internshipwarehouse.constants.Constants;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

public class FileUtils {

    @Value("${directory.reports}")
    private String reportsDirectory;

    public String getOrderReportFilePath(LocalDateTime startDateTime) {
        return String.format(Constants.REPORT_FILE_NAME_FORMAT, reportsDirectory, startDateTime)
                .replace(":", "-");
    }
}
