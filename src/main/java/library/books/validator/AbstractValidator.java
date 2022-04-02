package library.books.validator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Slf4j
public abstract class AbstractValidator<U> implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        try {
             doValidate((U) target, errors);
        } catch (IllegalStateException e){
            log.error("중복 검증 에러",e);
            throw e;
        }
    }

    protected abstract void doValidate(final U dto, final Errors errors);
}
