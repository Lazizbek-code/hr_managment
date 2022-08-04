package lazizbek.uz.hr_management.payload;

public class ApiResponse {
    private String message;

    private boolean success;

    private Object object;

    // constructor
    public ApiResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public ApiResponse() {
    }

    public ApiResponse(String message, boolean success, Object object) {
        this.message = message;
        this.success = success;
        this.object = object;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
