package com.lvv.bulletinboard.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lvv.bulletinboard.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Vitalii Lypovetskyi
 */
@Configuration
@Slf4j
public class AppConfig {

    @Autowired
    public void storeObjectMapper(ObjectMapper objectMapper) {
        JsonUtil.setMapper(objectMapper);
    }

}

