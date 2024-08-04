package estudoapi.apiparking.exception;

public class CpfUniqueViolation extends RuntimeException {
    public CpfUniqueViolation(String message) {
        super(message);
    }
}
