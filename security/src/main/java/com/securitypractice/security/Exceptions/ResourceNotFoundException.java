package com.securitypractice.security.Exceptions;

import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResourceNotFoundException extends RuntimeException {

    private String ResourceName;
    // private long fieldValue;
    private String fieldName;
    private long firOptional;

    public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
        super(String.format("%s not found with %s : %s ", resourceName, fieldName, fieldValue));
        ResourceName = resourceName;
        this.fieldName = fieldName;
        this.firOptional = fieldValue;
    }

}
