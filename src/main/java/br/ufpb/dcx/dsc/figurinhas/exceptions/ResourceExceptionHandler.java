package br.ufpb.dcx.dsc.figurinhas.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler({UserNotFoundException.class, AlbumNotFoundException.class, FigurinhaNotFoundException.class})
    public ResponseEntity<StandardError> handleNotFound(RuntimeException ex, HttpServletRequest request) {
        String error = "Recurso não funciona";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(System.currentTimeMillis(), status.value(), error, ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> handleValidation(MethodArgumentNotValidException e, HttpServletRequest request) {
        String error = "Erro de Validação";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        StandardError err = new StandardError(System.currentTimeMillis(), status.value(), error, message, request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> handleGenericException(Exception ex, HttpServletRequest request) {
        String error = "Erro de Serviço";
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        StandardError err = new StandardError(System.currentTimeMillis(), status.value(), error, "Um erro inesperado aconteceu", request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
}
