package mx.zublime.prediciclo.ui.tienda.mvpretrieveacustomer;

import mx.zublime.prediciclo.data.api.ApiAdapter;
import mx.zublime.prediciclo.data.models.Direccion;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetriveCustomerModel implements RetriveCustomerContract.RetriveCustomerModel {
    private RetriveCustomerPresenter mPresenter;

    public RetriveCustomerModel(RetriveCustomerPresenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void getRetriveCustomer(String idCliente) {
        Call<Direccion> call = ApiAdapter.getApiService().consultarInfoEnvioCliente(idCliente);
        call.enqueue(new Callback<Direccion>() {
            @Override
            public void onResponse(Call<Direccion> call, Response<Direccion> response) {
                if(response.isSuccessful()){
                    mPresenter.responseRetriveCustomer(response.body());
                }else {
                    mPresenter.setError(response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<Direccion> call, Throwable t) {
                mPresenter.setError(t.getMessage());
            }
        });
    }
}
