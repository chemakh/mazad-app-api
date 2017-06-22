package ch.com.mazad.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ErrorDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String code;

    private final String message;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String description;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<FieldErrorDTO> fieldErrors;

    public ErrorDTO(String code, String message, String description) {
        this.message = message;
        this.code = code;
        this.description = description;
    }

    public void add(String objectName, String field, String message) {
        if (fieldErrors == null) {
            fieldErrors = new ArrayList<>();
        }
        fieldErrors.add(new FieldErrorDTO(objectName, field, message));
    }

    public void add(FieldErrorDTO fieldError) {
        if (fieldErrors == null) {
            fieldErrors = new ArrayList<>();
        }
        fieldErrors.add(fieldError);
    }


    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }

    public String getCode() {
        return code;
    }

    public List<FieldErrorDTO> getFieldErrors() {
        return fieldErrors;
    }

    @Override
    public String toString() {
        return "ErrorDTO{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", description='" + description + '\'' +
                ", fieldErrors=" + fieldErrors +
                '}';
    }
}
