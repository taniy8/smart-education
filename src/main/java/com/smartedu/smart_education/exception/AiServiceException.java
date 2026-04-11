package com.smartedu.smart_education.exception;

public class AiServiceException extends RuntimeException {
    public AiServiceException(String message) {
        super(message);
    }
    public AiServiceException(String resourceName, Long id){
        super(resourceName + " not found with id: " + id);
    }

}

