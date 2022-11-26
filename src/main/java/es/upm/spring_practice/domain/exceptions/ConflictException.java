package es.upm.spring_practice.domain.exceptions;

public class ConflictException extends RuntimeException {
    private static final String DESCRIPTION = "Field values that cannot be repeated";

    public ConflictException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
