package org.kirsch.validation;

import javax.el.MethodNotFoundException;

public interface IValidatorFactory {

  <T> IValidator<T> build(Class<T> t) throws MethodNotFoundException;

}