package mx.zublime.prediciclo.ui.pedido.ordenmvp;

import com.google.gson.JsonObject;

import mx.zublime.prediciclo.data.api.ApiAdapter;
import mx.zublime.prediciclo.data.models.ResponseCreateOrderUpdate;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderModel implements OrderContract.OrderModel {

    private OrderPresenter mPresenter;

    public OrderModel(OrderPresenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void onCreateOrder(JsonObject rqt) {
        Call<ResponseCreateOrderUpdate> call = ApiAdapter.getApiService().crearOrden(rqt);
        call.enqueue(new Callback<ResponseCreateOrderUpdate>() {
            @Override
            public void onResponse(Call<ResponseCreateOrderUpdate> call, Response<ResponseCreateOrderUpdate> response) {
                if(response.isSuccessful()){
                    mPresenter.onResponseCreateOrder(response.body());
                }else {
                    mPresenter.setError();
                }
            }

            @Override
            public void onFailure(Call<ResponseCreateOrderUpdate> call, Throwable t) {
                mPresenter.setError();
            }
        });

    }

    @Override
    public void onUpdateOrder(String idOrder ,JsonObject rqt) {
        Call<ResponseCreateOrderUpdate> call = ApiAdapter.getApiService().updateOrden(idOrder,rqt);
        call.enqueue(new Callback<ResponseCreateOrderUpdate>() {
            @Override
            public void onResponse(Call<ResponseCreateOrderUpdate> call, Response<ResponseCreateOrderUpdate> response) {
                if(response.isSuccessful()){
                    mPresenter.onResponseUpdateOrder(response.body());
                }else {
                    mPresenter.setError();
                }
            }

            @Override
            public void onFailure(Call<ResponseCreateOrderUpdate> call, Throwable t) {
                mPresenter.setError();
            }
        });

    }
}
