package ch.com.mazad.exception;

import org.springframework.http.HttpStatus;

/**
 * Created by Chemakh on 03/01/2017.
 */
public enum MazadError {


    IDENTIFIER_ALREADY_IN_USE("error.IdentifierAlreadyInUse", HttpStatus.BAD_REQUEST),
    VALIDATION_ERROR("error.ValidationError", HttpStatus.BAD_REQUEST),
    INVALID_CODE("error.InvalidCode", HttpStatus.BAD_REQUEST),
    RESOURCE_NOT_FOUND("error.resourceNotfound", HttpStatus.NOT_FOUND),
    ACTION_NOT_PERMITTED("error.ActionNotPermitted", HttpStatus.FORBIDDEN),
    ACTION_UNAUTHORIZED("error.Unauthorized", HttpStatus.UNAUTHORIZED),
    UNPROCESSABLE_ENTITY("error.UnprocessableEntity", HttpStatus.UNPROCESSABLE_ENTITY),
    ERR_AGENDA("error.InternalAgendaError", HttpStatus.INTERNAL_SERVER_ERROR);


    private final String message;

    private final HttpStatus httpStatus;


    MazadError(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }


}
