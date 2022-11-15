package es.upm.spring_practice.domain.models.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class PositiveBigDecimalValidator implements ConstraintValidator< PositiveBigDecimal, BigDecimal > {

    @Override
    public void initialize(PositiveBigDecimal constraint) {
        // Empty, not operation
    }

    @Override
    public boolean isValid(BigDecimal bigDecimal, ConstraintValidatorContext context) {
        return bigDecimal != null && bigDecimal.signum() != -1;
    }

}
