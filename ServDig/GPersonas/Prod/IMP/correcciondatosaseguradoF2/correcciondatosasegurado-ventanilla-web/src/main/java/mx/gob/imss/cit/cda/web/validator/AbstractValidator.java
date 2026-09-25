package mx.gob.imss.cit.cda.web.validator;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

public abstract class AbstractValidator implements Validator {

    private static final Logger LOG;

    static {
        LOG = LoggerFactory.getLogger(AbstractValidator.class);
    }

    @Override
    public void validate(final Object target, final Errors errors) {
        LOG.trace("target: {}", target);
        final List<ObjectError> errores = errors.getAllErrors();
        LOG.debug("Total errors: {}", errores.size());
        for (ObjectError objectError : errores) {
            if (!(objectError instanceof FieldError)) {
                LOG.trace(objectError.toString());
            }
        }
        final List<FieldError> fieldErrorLst = errors.getFieldErrors();
        LOG.debug("Num errors in fields : {} ", fieldErrorLst.size());
        for (FieldError fieldError : fieldErrorLst) {
            LOG.trace("fieldError: {}", fieldError);
        }
    }

}
