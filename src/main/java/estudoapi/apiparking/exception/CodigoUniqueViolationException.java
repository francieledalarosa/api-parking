package estudoapi.apiparking.exception;

public class CodigoUniqueViolationException extends RuntimeException {
    public CodigoUniqueViolationException(String mensage) {
        super(mensage);
    }
}
