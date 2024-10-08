package estudoapi.apiparking.web.exception;

import estudoapi.apiparking.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.access.AccessDeniedException;


@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorMensage> accessDeniedException(AccessDeniedException ex, HttpServletRequest request){
        log.error("Api error - ", ex);
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMensage(request, HttpStatus.FORBIDDEN,ex.getMessage()));
        
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMensage> entityNotFoundException(EntityNotFoundException ex,
                                                                HttpServletRequest request){
        log.error("Api error - ", ex);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMensage(request, HttpStatus.NOT_FOUND,ex.getMessage()));

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMensage> methodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                        HttpServletRequest request,
                                                                        BindingResult result){
        log.error("Api error - ", ex);
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMensage(request, HttpStatus.UNPROCESSABLE_ENTITY, "Campo(s) inválido(s)", result));

    }
    @ExceptionHandler({ UsernameUniqueViolationException.class, CpfUniqueViolation.class,
            CodigoUniqueViolationException.class})
    public ResponseEntity<ErrorMensage> uniqueViolation(RuntimeException ex, HttpServletRequest request){
        log.error("Api error - ", ex);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMensage(request, HttpStatus.CONFLICT, ex.getMessage()));

    }
    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<ErrorMensage> handleIncorrectPasswordException(IncorrectPasswordException ex, HttpServletRequest request) {
        log.error("Api error - ", ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMensage(request, HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<ErrorMensage> handlePasswordMismatchException(PasswordMismatchException ex, HttpServletRequest request) {
        log.error("Api error - ", ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMensage(request, HttpStatus.BAD_REQUEST, ex.getMessage()));
    }


}
