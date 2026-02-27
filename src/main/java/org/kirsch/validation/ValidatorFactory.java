package org.kirsch.validation;

import java.util.List;
import java.util.Map;
import javax.el.MethodNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;


@Component
public class ValidatorFactory implements IValidatorFactory {

  private final ApplicationContext applicationContext;

  @Autowired
  public ValidatorFactory(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }

  // TODO - is this idiomatic?
  public <T> IValidator<T> build(Class<T> t) throws MethodNotFoundException {
    Map<String, AbstractValidator> beanMap = applicationContext.getBeansOfType(AbstractValidator.class);
    List<AbstractValidator> beans = beanMap.values().stream().toList();
    for (IValidator<?> bean : beans) {
      if (bean.getGenericType() == t) {
        return (AbstractValidator<T>) bean;
      }
    }
    throw new MethodNotFoundException("No Validator Found for Class " + t);
  }
    
}