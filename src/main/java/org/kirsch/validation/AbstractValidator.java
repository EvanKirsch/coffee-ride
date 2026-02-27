package org.kirsch.validation;

import java.lang.reflect.ParameterizedType;

public abstract class AbstractValidator<T> implements IValidator<T> {

  public Class getGenericType() {
    ParameterizedType parameterizedType = (ParameterizedType)getClass()
        .getGenericSuperclass();
    return (Class) parameterizedType.getActualTypeArguments()[0];
  }

}