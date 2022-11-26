package es.upm.spring_practice.domain.exceptions;

public class ForbiddenException extends RuntimeException {
    private static final String DESCRIPTION = "Authenticated user but his role does not allow the operation";

    public ForbiddenException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
