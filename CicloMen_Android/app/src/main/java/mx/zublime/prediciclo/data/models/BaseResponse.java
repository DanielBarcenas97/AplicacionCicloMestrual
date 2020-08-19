package mx.zublime.prediciclo.data.models;

import com.google.gson.annotations.SerializedName;

public class BaseResponse {
    private int status;

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
