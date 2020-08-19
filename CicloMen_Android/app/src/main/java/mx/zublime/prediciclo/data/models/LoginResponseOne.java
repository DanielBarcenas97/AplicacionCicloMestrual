package mx.zublime.prediciclo.data.models;

public class LoginResponseOne extends BaseResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public class Data{
        private boolean email_exists;

        public boolean isEmail_exists() {
            return email_exists;
        }
    }
}
