package mx.zublime.prediciclo.ui.pedido.datosenvio.mvpenvio;

import com.google.gson.JsonObject;

import mx.zublime.prediciclo.data.models.ResponseRegistrerCustomerOpenPay;
import mx.zublime.prediciclo.data.models.ResponseUpdateInfoDireccion;

public class EnvioPresenter implements EnvioContract.EnvioPresenter {

    private EnvioContract.EnvioView mView;
    private EnvioContract.EnvioModel mModel;


    public EnvioPresenter(EnvioContract.EnvioView mView) {
        this.mView = mView;
        this.mModel = new  EnvioModel(this);
    }

    @Override
    public void onUpdateInfoAddress(String idCliente, JsonObject rqt) {
        if(mView != null){
            mView.showDialog();
            mModel.updateInfoAddress(idCliente,rqt);
        }
    }

    @Override
    public void responseUpdateInfoAddress(ResponseUpdateInfoDireccion response) {
        mView.hideDialog();
        if(response != null){
            if (response.getShipping().getFirst_name() != null && response.getShipping().getLast_name() != null   && response.getShipping().getAddress_1() !=null && response.getShipping().getCountry()!= null
                && response.getShipping().getCity() != null && response.getShipping().getPostcode() != null && response.getShipping().getState() != null){
                mView.successUpdate();

            }else {
                mView.errorUpdate();
            }
        }

    }

    @Override
    public void setError(String message) {
        mView.hideDialog();
        mView.errorUpdate();
    }

    @Override
    public void registrerCustomerOpenPay(JsonObject rqt) {
        if (mView != null){
            mView.showDialog();
            mModel.registrerCustomerOpenPay(rqt);
        }
    }

    @Override
    public void responseRegistrerCustomerOpenPay(ResponseRegistrerCustomerOpenPay response) {
        mView.hideDialog();
        mView.setRegistrerCustomerOpenPay(response);
    }
}
