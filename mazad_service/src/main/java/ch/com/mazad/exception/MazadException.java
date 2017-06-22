package ch.com.mazad.exception;

import ch.com.mazad.utils.MessageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MazadException extends Exception {
    private Logger logger = LoggerFactory.getLogger(MazadException.class);

    public static final String WITH_ID = "id";
    public static final String WITH_EMAIL = "email";
    public static final String WITH_REF = "Reference";
    public static final String WITH_GLN = "Gln";

    private MazadError motif;
    private String code;
    private FieldErrorDTO fieldError;

    public FieldErrorDTO getFieldError() {
        return fieldError;
    }

    public void setFieldError(FieldErrorDTO fieldError) {
        this.fieldError = fieldError;
    }


    public MazadException(Throwable cause) {
        super(cause);
        this.motif = MazadError.ERR_AGENDA;

        logger.error("MazadException is Thrown");
        logger.error(this.getMessage());
    }

    private MazadException(String txt, MazadError cause, String code) {
        super(txt);
        this.motif = cause;
        this.code = code;

        logger.error("MazadException is Thrown");
        logger.error(this.getMessage());
    }

    @Override
    public String getMessage() {
        return super.getMessage();

    }

    public MazadError getMotif() {
        if (motif == null) {
            motif = MazadError.ERR_AGENDA;
        }
        return motif;
    }

    public void setMotif(MazadError motif) {
        this.motif = motif;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static MazadException actionNotPermittedErrorBuilder(String key,String[] objects) {

        String message = MessageFactory.getMessage("mazad.service.action_not_permitted_exception." +key, objects);
        return new MazadException(message, MazadError.ACTION_NOT_PERMITTED, null);
    }

    public static MazadException actionUnauthorizedErrorBuilder() {

        String message = MessageFactory.getMessage("mazad.service.action_unauthorized_exception.message", null);
        return new MazadException(message, MazadError.ACTION_UNAUTHORIZED, null);
    }

    public static MazadException unprocessableEntityExceptionBuilder(String key,String[] objects) {

        String message = MessageFactory.getMessage("mazad.service.unprocessable_entity_exception." +key, objects);
        return new MazadException(message, MazadError.UNPROCESSABLE_ENTITY, null);
    }

    public static MazadException validationErrorBuilder(FieldErrorDTO fieldError) {

        String message = MessageFactory.getMessage("mazad.exception.validation_error." + fieldError.getMessage(), new String[]{fieldError.getObjectName(), fieldError.getField()});
        MazadException ex = new MazadException(message, MazadError.VALIDATION_ERROR, null);
        ex.setFieldError(fieldError);
        return ex;
    }

    public static MazadException invalidCodeExceptionBuilder(String key) {

        return new MazadException(MessageFactory.getMessage("mazad.exception.invalid_code", new String[]{key}), MazadError.INVALID_CODE, null);
    }

    public static MazadException identifierAlreadyInUseExceptionBuilderBuilder(String identifier, String value,FieldErrorDTO fieldError) {

        MazadException ex = new MazadException(MessageFactory.getMessage("mazad.exception.identifier_already_in_use", new String[]{identifier, value}), MazadError.IDENTIFIER_ALREADY_IN_USE, null);
        ex.setFieldError(fieldError);
        return ex;
    }

    public static MazadException resourceNotFoundExceptionBuilder(Class object, String reference, String idType) {

        return new MazadException(MessageFactory.getMessage("mazad.exception.resource_not_found", new String[]{object.getSimpleName(), idType, reference}), MazadError.RESOURCE_NOT_FOUND, null);

    }

    public static MazadException resourceNotFoundExceptionBuilder(Class object, String reference) {

        return new MazadException(MessageFactory.getMessage("mazad.exception.resource_not_found", new String[]{object.getSimpleName(), WITH_REF, reference}), MazadError.RESOURCE_NOT_FOUND, null);

    }

    public static MazadException resourceNotFoundExceptionBuilder(String object, String reference, String idType) {

        return new MazadException(MessageFactory.getMessage("mazad.exception.resource_not_found", new String[]{object, idType, reference}), MazadError.RESOURCE_NOT_FOUND, null);

    }

    public static MazadException resourceNotFoundExceptionBuilder(String object, String reference) {

        return new MazadException(MessageFactory.getMessage("mazad.exception.resource_not_found", new String[]{object, WITH_REF, reference}), MazadError.RESOURCE_NOT_FOUND, null);

    }
}
