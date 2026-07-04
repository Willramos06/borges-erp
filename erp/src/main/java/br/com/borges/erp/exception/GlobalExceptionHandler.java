package br.com.borges.erp.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Captura erros de Validação (@NotBlank, @Email, @Pattern)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResposta> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> erros = new ArrayList<>();
        
        // Pega todos os campos que falharam e extrai a mensagem de erro definida por você
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            erros.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
        }

        ErroResposta resposta = new ErroResposta(
                HttpStatus.BAD_REQUEST.value(),
                "Erro de Validação de Dados",
                "Um ou mais campos foram preenchidos incorretamente.",
                erros
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resposta);
    }

    // 2. Captura erros de Regra de Negócio (Nossos throw new RuntimeException)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErroResposta> handleBusinessErrors(RuntimeException ex) {
        ErroResposta resposta = new ErroResposta(
                HttpStatus.BAD_REQUEST.value(),
                "Erro de Processamento",
                ex.getMessage(),
                null
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resposta);
    }

    // 3. Captura qualquer outro erro genérico não mapeado (Erro 500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResposta> handleGenericErrors(Exception ex) {
        ErroResposta resposta = new ErroResposta(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro Interno do Servidor",
                "Ocorreu um erro inesperado. Contate o suporte.",
                List.of(ex.getMessage()) // Em produção, é melhor ocultar a mensagem exata do erro genérico
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resposta);
    }
}