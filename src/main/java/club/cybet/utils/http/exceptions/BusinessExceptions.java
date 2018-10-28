package club.cybet.utils.http.exceptions;

import java.util.List;

public class BusinessExceptions {

    private List<ExceptionRepresentation> exceptions = null;

    public BusinessExceptions(List<ExceptionRepresentation> exceptions) {
        this.exceptions = exceptions;
    }

    public List<ExceptionRepresentation> getExceptions() {
        return exceptions;
    }

    public void setExceptions(List<ExceptionRepresentation> exceptions) {
        this.exceptions = exceptions;
    }

    @Override
    public String toString() {
        return "BusinessExceptions{" +
                "exceptions=" + exceptions +
                '}';
    }
}
