package com.example.mailbe.Service;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


public class InstantStringAdapter {

    public static String instantToFormattedString(Instant instant){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd|HH:mm:ss")
                .withZone(ZoneId.systemDefault());

        return formatter.format(instant);
    }

    public static String instantToEpochSeconds(Instant instant){
        return String.valueOf(instant.getEpochSecond());
    }

    public static String epochSecondsToUTCString(String epochSecond){
        long seconds = Long.parseLong(epochSecond);
        Instant instant = Instant.ofEpochSecond(seconds);
        return instant.toString();
    }

}
