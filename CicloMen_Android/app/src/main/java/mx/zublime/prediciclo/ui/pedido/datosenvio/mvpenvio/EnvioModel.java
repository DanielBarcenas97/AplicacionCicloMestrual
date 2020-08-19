package mx.zublime.prediciclo.ui.pedido.datosenvio.mvpenvio;

import com.google.gson.JsonObject;

import mx.zublime.prediciclo.data.api.ApiAdapter;
import mx.zublime.prediciclo.data.models.ResponseRegistrerCustomerOpenPay;
import mx.zublime.prediciclo.data.models.ResponseUpdateInfoDireccion;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnvioModel implements EnvioContract.EnvioModel {

    private EnvioPresenter mPresenter;

    public EnvioModel(EnvioPresenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void updateInfoAddress(String idCliente, JsonObject rqt) {

        Call<ResponseUpdateInfoDireccion> call = ApiAdapter.getApiService().updateInfoEnvioCliente(idCliente,rqt);
        call.enqueue(new Callback<ResponseUpdateInfoDireccion>() {
            @Override
            public void onResponse(Call<ResponseUpdateInfoDireccion> call, Response<ResponseUpdateInfoDireccion> response) {
                if(response.isSuccessful()){
                    mPresenter.responseUpdateInfoAddress(response.body());
                }else {
                    mPresenter.setError(response.errorBody().toString());
                }
            }
            @Override
            public void onFailure(Call<ResponseUpdateInfoDireccion> call, Throwable t) {
                mPresenter.setError(t.getMessage());
            }
        });

    }

    @Override
    public void registrerCustomerOpenPay(JsonObject rqt) {
        Call<ResponseRegistrerCustomerOpenPay> call = ApiAdapter.getApiService().consultarInfoCustomerOpenPay(rqt);
        call.enqueue(new Callback<ResponseRegistrerCustomerOpenPay>() {
            @Override
            public void onResponse(Call<ResponseRegistrerCustomerOpenPay> call, Response<ResponseRegistrerCustomerOpenPay> response) {
                if(response.isSuccessful()){
                    mPresenter.responseRegistrerCustomerOpenPay(response.body());
                }else {
                    mPresenter.setError(response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseRegistrerCustomerOpenPay> call, Throwable t) {
                mPresenter.setError(t.getMessage());
            }
        });
    }
}
