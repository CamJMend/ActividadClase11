package com.example.app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.app.model.FormFactory;
import com.example.app.model.LoginRequest;
import com.example.app.factory.AdminFormFactory;
import com.example.app.factory.FormField;
import com.example.app.factory.GuestFormFactory;


@RestController
@RequestMapping("/form")
@CrossOrigin(origins = "http://localhost:5173")
public class FormController {

    private final AdminFormFactory adminFormFactory;
    private final GuestFormFactory guestFormFactory;

    public FormController(AdminFormFactory adminFormFactory, GuestFormFactory guestFormFactory) {
        this.adminFormFactory = adminFormFactory;
        this.guestFormFactory = guestFormFactory;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        if ("admin@email.com".equals(email) && "admin123".equals(password)) {
            return Map.of("role", "admin");
        } else if ("guest@email.com".equals(email) && "guest123".equals(password)) {
            return Map.of("role", "guest");
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
        }
    }

    @GetMapping("/{role}")
    public List<FormField> getForm(@PathVariable String role) {
        FormFactory factory;

        switch (role.toLowerCase()) {
            case "admin":
                factory = adminFormFactory;
                break;
            case "guest":
                factory = guestFormFactory;
                break;
            default:
                throw new IllegalArgumentException("Rol no válido");
        }

        return factory.createFormFields();
    }
}