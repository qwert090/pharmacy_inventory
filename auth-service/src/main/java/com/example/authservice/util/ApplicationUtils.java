package com.example.authservice.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@UtilityClass
public class ApplicationUtils {
    public static final String EMPTY = "";
    public static final String BEARER = "Bearer ";

    public static String extractJwtFromBearerToken(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith(BEARER)) {
            return bearerToken.replace(BEARER, EMPTY);
        } else {
            return bearerToken;
        }
    }

    public static LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
}
