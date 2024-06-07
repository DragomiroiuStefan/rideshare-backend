package com.stefandragomiroiu.rideshare_backend.config;

import com.stefandragomiroiu.rideshare_backend.model.converter.RideStatusReadingConverter;
import com.stefandragomiroiu.rideshare_backend.model.converter.RideStatusWritingConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class JdbcConfig {

    @Bean
    public JdbcCustomConversions jdbcCustomConversions() {
        List<Object> converters = new ArrayList<>();
        converters.add(new RideStatusReadingConverter());
        converters.add(new RideStatusWritingConverter());
        return new JdbcCustomConversions(converters);
    }
}
