package club.cybet.utils.http.exceptions;

public class BusinessException {

    private ExceptionRepresentation exception = null;

    public BusinessException(ExceptionRepresentation exception) {
        this.exception = exception;
    }

    public ExceptionRepresentation getException() {
        return exception;
    }

    public void setException(ExceptionRepresentation exception) {
        this.exception = exception;
    }

    @Override
    public String toString() {
        return "BusinessException{" +
                " exception=" + exception +
                '}';
    }
}
