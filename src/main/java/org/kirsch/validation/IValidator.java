package org.kirsch.validation;

import java.util.List;

public interface IValidator<T> {

  List<Exception> validate(T o);
    
}