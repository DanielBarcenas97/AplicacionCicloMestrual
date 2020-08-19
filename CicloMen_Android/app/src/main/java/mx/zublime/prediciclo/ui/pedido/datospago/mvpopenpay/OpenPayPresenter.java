package mx.zublime.prediciclo.ui.pedido.datospago.mvpopenpay;

import com.google.gson.JsonObject;

import mx.zublime.prediciclo.data.models.ResponseCargoOpenpay;

public class OpenPayPresenter implements OpenPayContract.OpenPayPresenter {

    private OpenPayContract.OpenPayView mView;
    private OpenPayContract.OpenPayModel mModel;

    public OpenPayPresenter(OpenPayContract.OpenPayView mView) {
        this.mView = mView;
        this.mModel = new OpenPayModel(this);
    }

    @Override
    public void generatePayment(String name, String numberCard, int month, int year, String cvv) {
        if(mView != null){

            mModel.getGeneratePayment(name,numberCard,month,year,cvv);
        }
    }

    @Override
    public void onSuccessPayment(String source_id) {
        mView.successPay(source_id);
    }

    @Override
    public void onErrorPayment() {
        mView.errorPay();
    }

    @Override
    public void updateOrden(int idCustomer, boolean pay)
    {
        JsonObject request = new JsonObject();
        request.addProperty("customer_id",idCustomer);
        request.addProperty("set_paid",pay);
        mModel.updateOrden(request);
    }

    @Override
    public void onUpdateOrden()
    {
        mView.successPay("");
    }

    @Override
    public void generarCargo(String user, String password, String sourceId, String deviceId, int orderId)
    {
        JsonObject request = new JsonObject();
        request.addProperty("PHP_AUTH_USER",user);
        request.addProperty("PHP_AUTH_PW",password);
        request.addProperty("source_id",sourceId);
        request.addProperty("device_session_id",deviceId);
        request.addProperty("order_id",orderId);
        mModel.generarCargo(request);
    }

    @Override
    public void setCargoResponse(ResponseCargoOpenpay response) {
        if(response.getStatus() == 200){
            mView.onSuccessCharge();
        }else {
            mView.onErrorCharge();
        }
    }


}
