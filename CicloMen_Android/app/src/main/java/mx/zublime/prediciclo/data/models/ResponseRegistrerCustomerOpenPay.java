
package mx.zublime.prediciclo.data.models;

import com.google.gson.annotations.SerializedName;


public class ResponseRegistrerCustomerOpenPay {

    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private  Data data;
    private  String  message;

    public class Data{
        public String getOpenpay_customer_id() {
            return openpay_customer_id;
        }

        public void setOpenpay_customer_id(String openpay_customer_id) {
            this.openpay_customer_id = openpay_customer_id;
        }

        private String openpay_customer_id;
    }

}
