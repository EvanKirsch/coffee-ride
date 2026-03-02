package io.coffeeride.validation;

import java.util.List;

public interface IValidationService {

  <T> List<Exception> validate(T t,  Class<T> tClass);
    
}