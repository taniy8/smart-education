package com.smartedu.smart_education.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SubjectRequest {

    @NotBlank(message = "Subject name is required")
    private String name;

    @NotBlank(message = "Subject code is required")
    private String code;

    @NotBlank(message = "Class name is required")
    private String className;
}