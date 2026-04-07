package com.smartedu.smart_education.dto.response;

import com.smartedu.smart_education.entity.Parent;
import lombok.Data;

@Data
public class ParentResponse {
    private Long id;
    private String parentName;
    private String email;
    private String phone;
    private String relation;
    private String studentName;

    public static ParentResponse fromEntity(Parent parent) {
        ParentResponse response = new ParentResponse();
        response.setId(parent.getId());
        response.setPhone(parent.getPhone());
        response.setRelation(parent.getRelation().name());
        if (parent.getUser() != null) {
            response.setParentName(parent.getUser().getName());
            response.setEmail(parent.getUser().getEmail());
        }
        if (parent.getStudent() != null && parent.getStudent().getUser() != null) {
            response.setStudentName(parent.getStudent().getUser().getName());
        }
        return response;
    }
}