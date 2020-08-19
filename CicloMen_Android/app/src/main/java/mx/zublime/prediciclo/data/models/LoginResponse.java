package mx.zublime.prediciclo.data.models;

import com.google.gson.annotations.SerializedName;

public class LoginResponse  {
    @SerializedName("status")

    private int status;

    public int getStatus() {
        return status;
    }

    @SerializedName("data")
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
    public class Data{
        @SerializedName("user_id")
        private String user_id;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }
    }
}
