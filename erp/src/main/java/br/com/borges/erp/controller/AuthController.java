package br.com.borges.erp.controller;

import br.com.borges.erp.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String senha = credentials.get("senha");

        // Validação estática para esta etapa inicial (depois integraremos com o banco de dados)
        if ("admin@borges.com.br".equals(email) && "admin123".equals(senha)) {
            String token = jwtUtil.generateToken(email);
            
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("nome", "Admin Borges");
            
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
    }
}