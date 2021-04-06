package com.example.queue.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableConfigurationProperties
public class SecretKeysConfig {

//    @Value("${secret.key}")
    private static String[] KEYS = { "secretKey" };

    public static List<String> getKEYS() {
        return Arrays.asList(KEYS);
    }
}
