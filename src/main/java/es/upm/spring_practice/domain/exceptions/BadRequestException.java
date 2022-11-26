package es.upm.spring_practice.domain.exceptions;

public class BadRequestException extends RuntimeException {
    private static final String DESCRIPTION = "Error in request field values";

    public BadRequestException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
