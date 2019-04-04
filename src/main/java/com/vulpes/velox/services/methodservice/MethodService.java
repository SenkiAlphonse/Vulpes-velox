package com.vulpes.velox.services.methodservice;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

public interface MethodService {

  Map<String, ?> getErrorMessageFlashAttributes(String message,
                                                RedirectAttributes redirectAttributes,
                                                String errorAttribute);
}


