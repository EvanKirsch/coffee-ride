package io.coffeeride.validation.validator;

import java.util.ArrayList;
import java.util.List;

import io.coffeeride.model.PathfindingRequestStr;
import io.coffeeride.validation.AbstractValidator;
import org.springframework.stereotype.Component;

@Component
public class PathFindingRequestStrValidator extends AbstractValidator<PathfindingRequestStr> {

  @Override
  public List<Exception> validate(PathfindingRequestStr obj) {
    List<Exception> exceptions = new ArrayList<>();
    if(isNullEmpty(obj.getStepMiles())) {
      exceptions.add(new Exception("Miles must be entered"));
    }
    return exceptions;
  }

  private boolean isNullEmpty(String s) {
    return s == null || s.isBlank();
  }

}