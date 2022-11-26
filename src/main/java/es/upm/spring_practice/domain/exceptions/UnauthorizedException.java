package es.upm.spring_practice.domain.exceptions;

public class UnauthorizedException extends RuntimeException {
    private static final String DESCRIPTION = "unauthenticated user";

    public UnauthorizedException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
