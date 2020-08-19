package mx.zublime.prediciclo.data.models;

public class ResponseUpdateCalendar extends BaseResponse
{
    private boolean data;
    private String message;

    public boolean isData() {
        return data;
    }

    public void setData(boolean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
