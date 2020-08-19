package mx.zublime.prediciclo.data.models;

public class ResponseForgot extends BaseResponse
{
    private String message;
    private boolean data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isData() {
        return data;
    }

    public void setData(boolean data) {
        this.data = data;
    }
}
