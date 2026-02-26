package org.kirsch.validation;

import javax.el.MethodNotFoundException;

import org.kirsch.model.PathfindingRequestStr;
import org.kirsch.validation.validator.PathFindingRequestStrValidator;
import org.springframework.stereotype.Component;

import lombok.extern.java.Log;

@Component
@Log
public class ValidatorFactory implements IValidatorFactory {

  // TODO - abstract
  public <T> IValidator<T> build(T t) throws MethodNotFoundException {
    if (t instanceof PathfindingRequestStr) {
      return (IValidator<T>) new PathFindingRequestStrValidator();
    }
    throw new MethodNotFoundException("No Validator Found for Class " + t.getClass());
  }
    
}