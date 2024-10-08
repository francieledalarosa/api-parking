package estudoapi.apiparking.web.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;
@Getter
@ToString
public class ErrorMensage {

    private String path;
    private String method;
    private int status;
    private String statusText;
    private String message;
    //utilizado para função addErros
    private Map<String, String> errors;

    public ErrorMensage() {
    }

    public ErrorMensage(HttpServletRequest request, HttpStatus status, String message) {
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.status = status.value();
        this.statusText = status.getReasonPhrase();
        this.message = message;
        
    }
//método para capturar as mensagens de erro de campos de validação
    public ErrorMensage(HttpServletRequest request, HttpStatus status, String message, BindingResult result) {
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.status = status.value();
        this.statusText = status.getReasonPhrase();
        this.message = message;
        addErros(result);

    }
//construção da função para pegar as mensagens, utilizado no método anterior
    private void addErros(BindingResult result){
        this.errors = new HashMap<>();
        for(FieldError fieldError : result.getFieldErrors()){
            this.errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }
}
