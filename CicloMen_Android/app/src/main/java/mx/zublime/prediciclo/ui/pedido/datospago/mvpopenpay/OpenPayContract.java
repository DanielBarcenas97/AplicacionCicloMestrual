package mx.zublime.prediciclo.ui.pedido.datospago.mvpopenpay;

import com.google.gson.JsonObject;

import mx.zublime.prediciclo.data.models.ResponseCargoOpenpay;

public interface OpenPayContract {

    interface OpenPayView{
        void successPay(String source_id);
        void errorPay();
        void onSuccessCharge();
        void onErrorCharge();
    }

    interface OpenPayPresenter{
        void generatePayment(String name, String numberCard, int month,int year, String cvv);
        void onSuccessPayment( String source_id);
        void onErrorPayment();
        void updateOrden(int idCustomer, boolean pay);
        void onUpdateOrden();
        void generarCargo(String user, String password, String sourceId, String deviceId, int orderId);
        void setCargoResponse(ResponseCargoOpenpay responseCargoOpenpay);
    }

    interface OpenPayModel{
        void getGeneratePayment(String name, String numberCard, int month,int year, String cvv);
        void updateOrden(JsonObject orden);
        void generarCargo(JsonObject rqt);
    }

}
