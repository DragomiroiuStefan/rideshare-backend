package com.stefandragomiroiu.rideshare_backend.model;

import com.stefandragomiroiu.rideshare_backend.controller.validation.Update;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;

public record Location(
        @Id
        @NotNull(groups = {Update.class})
        Long locationId,
        @NotBlank
        @Size(max = 255)
        String city,
        @NotBlank
        @Size(max = 255)
        String county
) {}
