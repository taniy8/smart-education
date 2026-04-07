package com.smartedu.smart_education.dto.request;

import com.smartedu.smart_education.entity.Parent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ParentRequest {

    @NotNull(message = "User id is required")
    private Long userId;

    @NotNull(message = "Student id is required")
    private Long studentId;

    @NotNull(message = "Relation is required")
    private Parent.Relation relation;

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone must be 10 digits")
    private String phone;
}