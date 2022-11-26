package es.upm.spring_practice.domain.exceptions;

public class NotFoundException extends RuntimeException {
    private static final String DESCRIPTION = "Model not found";

    public NotFoundException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
