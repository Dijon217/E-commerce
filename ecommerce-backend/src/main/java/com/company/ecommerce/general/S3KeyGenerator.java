package com.company.ecommerce.general;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
public class S3KeyGenerator {

    private String originalFileName;
    private LocalDateTime timestamp;

    public String generateKey() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String formattedDate = timestamp != null
                ? timestamp.format(formatter)
                : LocalDateTime.now().format(formatter);

        return formattedDate + "_" + originalFileName;
    }
}
