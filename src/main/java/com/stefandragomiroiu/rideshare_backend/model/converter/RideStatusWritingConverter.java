package com.stefandragomiroiu.rideshare_backend.model.converter;

import com.stefandragomiroiu.rideshare_backend.model.RideStatus;
import jakarta.annotation.Nullable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

@Component
@WritingConverter
public class RideStatusWritingConverter implements Converter<RideStatus, String> {

    @Override
    public String convert(RideStatus source) {
        return source.toString();
    }
}
