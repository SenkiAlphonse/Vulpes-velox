package com.vulpes.velox.services.methodservice;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Service
public class MethodServiceImpl implements MethodService {

  @Override
  public Map<String, ?> getErrorMessageFlashAttributes(String message,
                                                       RedirectAttributes redirectAttributes,
                                                       String errorAttribute) {
    redirectAttributes.addFlashAttribute(errorAttribute, true);
    redirectAttributes.addFlashAttribute("errorMessage", message);
    return redirectAttributes.getFlashAttributes();
  }

}
