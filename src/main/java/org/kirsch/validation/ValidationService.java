package org.kirsch.validation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationService implements IValidationService {

  private final IValidatorFactory validatorFactory;

  @Autowired
  public ValidationService(IValidatorFactory validatorFactory) {
    this.validatorFactory = validatorFactory;
  }

  public <T> List<Exception> validate(T t) {
    IValidator<T> validator = validatorFactory.build(t);
    return validator.validate(t);
  }
    
}