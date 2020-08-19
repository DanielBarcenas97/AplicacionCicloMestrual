package mx.zublime.prediciclo.ui.configuraciontur.mvp;

import com.google.gson.JsonObject;

import mx.zublime.prediciclo.data.api.ApiAdapter;
import mx.zublime.prediciclo.data.models.BaseResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfigurationModel implements ConfigurationContract.ConfigurationContractModel
{
    private ConfigurationPresenter mPresenter;

    public ConfigurationModel(ConfigurationPresenter presenter)
    {
        this.mPresenter = presenter;
    }

    @Override
    public void establecerDatos(JsonObject request)
    {
        Call<BaseResponse> call = ApiAdapter.getApiService().actualizarInformacionDelUsuario(request);
        call.enqueue(new Callback<BaseResponse>()
        {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response)
            {
                if (response.isSuccessful())
                {
                    mPresenter.setUpResponse(response.body());
                }
                else
                {
                    mPresenter.onFailure("Ha ocurrido un error");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t)
            {

            }
        });
    }
}
