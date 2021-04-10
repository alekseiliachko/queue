package com.example.queue.util;

import org.springframework.http.HttpHeaders;

import java.util.List;

public class TokenParser {

    private static final String TOKEN_VAR = "Authorization";

    public static String extractToken(HttpHeaders headers) {

        List<String> list = headers.get(TOKEN_VAR);

        if (list == null || list.isEmpty()) {
            return null;
        }

        String bearerToken = list.get(0);

        if (bearerToken == null) {
            return null;
        }

        return bearerToken.substring(7);
    }
}
