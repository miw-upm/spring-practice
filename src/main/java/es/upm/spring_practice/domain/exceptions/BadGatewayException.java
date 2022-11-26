package es.upm.spring_practice.domain.exceptions;

public class BadGatewayException extends RuntimeException {
    private static final String DESCRIPTION = "Unexpected error in access to external services";

    public BadGatewayException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
