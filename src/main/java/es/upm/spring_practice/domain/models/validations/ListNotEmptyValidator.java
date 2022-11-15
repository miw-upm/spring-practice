package es.upm.spring_practice.domain.models.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ListNotEmptyValidator implements ConstraintValidator< ListNotEmpty, List< ? > > {

    @Override
    public void initialize(ListNotEmpty constraint) {
        // Empty, not operation
    }

    @Override
    public boolean isValid(List< ? > list, ConstraintValidatorContext context) {
        return list != null && !list.isEmpty();
    }

}
