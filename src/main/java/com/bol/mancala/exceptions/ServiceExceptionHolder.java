package com.bol.mancala.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author mir00r on 12/2/22
 * @project IntelliJ IDEA
 */
public class ServiceExceptionHolder {
    @Getter
    @RequiredArgsConstructor
    public static class ServiceException extends RuntimeException {
        private final String entity;
        private final String entityName;
        private final int code;
        private final String message;
    }

    public static class ResourceNotFoundException extends ServiceException {
        public ResourceNotFoundException(String entityName, String message) {
            super(null, entityName, 404, message);
        }
    }

    public static class EntityNotFoundException extends ResourceNotFoundException {
        public EntityNotFoundException(String entityName, Long id) {
            super(entityName , "No " + entityName + " found with ID: " + id);
        }
    }
}
