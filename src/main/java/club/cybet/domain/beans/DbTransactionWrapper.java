package club.cybet.domain.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * cybet-api (club.cybet.domain.beans)
 * Created by: cybet
 * On: 20 Aug, 2018 8/20/18 2:57 PM
 **/
public class DbTransactionWrapper {

    private boolean hasErrors;
    private List<String> errors;
    private Object data;

    public DbTransactionWrapper() {
        this.hasErrors = false;
        this.errors = new ArrayList<>();
    }

    public boolean hasErrors() {
        return hasErrors;
    }

    public void setHasErrors(boolean hasErrors) {
        this.hasErrors = hasErrors;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void addError(String error){
        this.errors.add(error);
    }

    public String getErrors() {
        return errors.toString();
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
