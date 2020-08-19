package mx.zublime.prediciclo.ui.pedido.datosenvio.mvpenvio;

import com.google.gson.JsonObject;

import mx.zublime.prediciclo.data.models.ResponseRegistrerCustomerOpenPay;
import mx.zublime.prediciclo.data.models.ResponseRetrieveProduct;
import mx.zublime.prediciclo.data.models.ResponseUpdateInfoDireccion;

public interface EnvioContract {

    interface  EnvioView{

        void showDialog();
        void hideDialog();
        void successUpdate();
        void errorUpdate();
        void setRegistrerCustomerOpenPay(ResponseRegistrerCustomerOpenPay response);

    }
    interface EnvioPresenter{

        void  onUpdateInfoAddress(String idCliente, JsonObject rqt);
        void  responseUpdateInfoAddress(ResponseUpdateInfoDireccion response);
        void registrerCustomerOpenPay(JsonObject rqt);
        void  responseRegistrerCustomerOpenPay(ResponseRegistrerCustomerOpenPay response);
        void  setError(String message);
    }

    interface EnvioModel
    {
        void updateInfoAddress(String idCliente, JsonObject rqt);
        void  registrerCustomerOpenPay(JsonObject rqt);

    }

}
