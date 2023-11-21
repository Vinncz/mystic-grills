package exceptions;

public class DatabaseModificationPolicyViolatedException extends Exception {

    private String message;

    public DatabaseModificationPolicyViolatedException () {

    }

    public DatabaseModificationPolicyViolatedException (String _message) {
        this.message = _message;
    }

    public String getMessage () {
        return this.message;
    }

    public void setMessage (String _message) {
        this.message = _message;
    }

}
