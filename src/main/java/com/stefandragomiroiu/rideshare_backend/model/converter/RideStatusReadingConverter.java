package com.stefandragomiroiu.rideshare_backend.model.converter;

import com.stefandragomiroiu.rideshare_backend.model.RideStatus;
import jakarta.annotation.Nullable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

@Component
@ReadingConverter
public class RideStatusReadingConverter implements Converter<String, RideStatus> {
    @Override
    public RideStatus convert(@Nullable String source) {
        return RideStatus.valueOf(source);
    }
}
