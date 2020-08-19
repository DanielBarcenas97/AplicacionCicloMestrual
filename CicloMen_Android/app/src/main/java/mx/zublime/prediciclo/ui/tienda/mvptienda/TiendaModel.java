package mx.zublime.prediciclo.ui.tienda.mvptienda;

import mx.zublime.prediciclo.data.api.ApiAdapter;
import mx.zublime.prediciclo.data.models.ResponseRetrieveProduct;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TiendaModel implements TiendaContract.tiendaModel {


    private TiendaPresenter mPresenter;

    public TiendaModel(TiendaPresenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void getProduct() {
        Call<ResponseRetrieveProduct> call = ApiAdapter.getApiService().consultarProducto();
        call.enqueue(new Callback<ResponseRetrieveProduct>() {
            @Override
            public void onResponse(Call<ResponseRetrieveProduct> call, Response<ResponseRetrieveProduct> response) {
                if(response.isSuccessful()){
                    mPresenter.responseProduct(response.body());
                }else {
                    mPresenter.setError(response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseRetrieveProduct> call, Throwable t) {
               mPresenter.setError(t.getMessage());
            }
        });
    }
}
