package com.smartedu.smart_education.service;

import com.smartedu.smart_education.dto.request.ParentRequest;
import com.smartedu.smart_education.dto.response.ParentResponse;

import java.util.List;

public interface ParentService {
    ParentResponse addParent(ParentRequest request);
    ParentResponse getParentById(Long id);
    List<ParentResponse> getParentsByStudent(Long studentId);
    ParentResponse updateParent(Long id, ParentRequest request);
    void deleteParent(Long id);
}