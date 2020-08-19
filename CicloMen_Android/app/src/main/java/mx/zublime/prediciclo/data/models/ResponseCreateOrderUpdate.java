package mx.zublime.prediciclo.data.models;

public class ResponseCreateOrderUpdate extends Direccion {


        private int id;
        private int customer_id;
        private String payment_method;
        private String payment_method_title;
        private boolean set_paid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSet_paid() {
        return set_paid;
    }


    // Getter Methods

        public int getCustomer_id() {
            return customer_id;
        }

        public String getPayment_method() {
            return payment_method;
        }

        public String getPayment_method_title() {
            return payment_method_title;
        }

        public boolean getSet_paid() {
            return set_paid;
        }

        // Setter Methods

        public void setCustomer_id(int customer_id) {
            this.customer_id = customer_id;
        }

        public void setPayment_method(String payment_method) {
            this.payment_method = payment_method;
        }

        public void setPayment_method_title(String payment_method_title) {
            this.payment_method_title = payment_method_title;
        }

        public void setSet_paid(boolean set_paid) {
            this.set_paid = set_paid;
        }

}
