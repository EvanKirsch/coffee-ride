package org.kirsch.validation.validator;

import java.util.ArrayList;
import java.util.List;

import org.kirsch.model.PathfindingRequestStr;
import org.kirsch.validation.IValidator;
import org.springframework.stereotype.Component;

@Component
public class PathFindingRequestStrValidator implements IValidator<PathfindingRequestStr> {

  @Override
  public List<Exception> validate(PathfindingRequestStr obj) {
    List<Exception> exceptions = new ArrayList<>();
    if(isNullEmpty(obj.getStepMiles())) {
      exceptions.add(new Exception("Miles must be entered"));
    }
    return exceptions;
  }

  private boolean isNullEmpty(String s) { 
    return s == null || s.isEmpty() || s.isBlank();
  }
    
}