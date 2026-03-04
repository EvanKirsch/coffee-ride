package io.coffeeride.validation;

import java.util.List;

public interface IValidator<T> {

  List<Exception> validate(T o);

  Class<?> getGenericType();

}