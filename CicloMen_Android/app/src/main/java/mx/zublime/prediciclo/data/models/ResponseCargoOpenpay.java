package mx.zublime.prediciclo.data.models;

public class ResponseCargoOpenpay {

    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ResponseCargoOpenpay.Data getData() {
        return data;
    }

    public void setData(ResponseCargoOpenpay.Data data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private ResponseCargoOpenpay.Data data;
    private  String  message;

    public class Data{
        public String getOpenpay_customer_id() {
            return openpay_customer_id;
        }

        public void setOpenpay_customer_id(String openpay_customer_id) {
            this.openpay_customer_id = openpay_customer_id;
        }

        private String openpay_customer_id;

        private String openpay_charge_id;

        private String device_session_id;

        private String source_id;

        private String method;

        private String currency;

        private String amount;

        private String description;

        private String order_id;

        public String getOpenpay_charge_id() {
            return openpay_charge_id;
        }

        public void setOpenpay_charge_id(String openpay_charge_id) {
            this.openpay_charge_id = openpay_charge_id;
        }

        public String getDevice_session_id() {
            return device_session_id;
        }

        public void setDevice_session_id(String device_session_id) {
            this.device_session_id = device_session_id;
        }

        public String getSource_id() {
            return source_id;
        }

        public void setSource_id(String source_id) {
            this.source_id = source_id;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }
    }

}
