package com.data.deenasty.deenasty_data.controllers.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@RestController
public class CustomErrorController implements ErrorController {

    private final ErrorAttributes errorAttributes;

    public CustomErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping("/error")
    public ResponseEntity<Map<String, Object>> handleError(HttpServletRequest request) {
        // Conversion en WebRequest
        WebRequest webRequest = new ServletWebRequest(request);

        Map<String, Object> attributes = errorAttributes.getErrorAttributes(
            webRequest,
            ErrorAttributeOptions.defaults()
        );

        int status = (int) attributes.getOrDefault("status", 500);
        return ResponseEntity.status(status).body(attributes);
    }
}


