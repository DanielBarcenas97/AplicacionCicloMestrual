package mx.zublime.prediciclo.ui.autenticacion.registro.registromvp;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;

import mx.zublime.prediciclo.data.api.ApiAdapter;
import mx.zublime.prediciclo.data.models.BaseResponse;
import mx.zublime.prediciclo.data.models.ResponseRegistro;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroModel implements RegistroContract.RegistroContractModel
{

    private RegistroPresenter mPresenter;

    public RegistroModel(RegistroPresenter presenter)
    {
        mPresenter = presenter;
    }

    @Override
    public void registrarUsuario(JsonObject request)
    {
        Call<ResponseRegistro> call = ApiAdapter.getApiService().registrarUsuario(request);
        call.enqueue(new Callback<ResponseRegistro>()
        {
            @Override
            public void onResponse(Call<ResponseRegistro> call, Response<ResponseRegistro> response)
            {
                if (response.isSuccessful())
                {
                    mPresenter.setupResposeRegistro(response.body());
                }
                else
                {
                    ResponseRegistro rep = null;
                    try {
                        rep = new Gson().fromJson(response.errorBody().string(), ResponseRegistro.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mPresenter.setupResposeRegistro(rep);
                }

            }

            @Override
            public void onFailure(Call<ResponseRegistro> call, Throwable t)
            {
                Log.wtf("ERROR",t.getMessage());
            }
        });
    }

    @Override
    public void cambiarPassword(JsonObject request)
    {
        Call<BaseResponse> call = ApiAdapter.getApiService().actualizarInformacionDelUsuario(request);
        call.enqueue(new Callback<BaseResponse>()
        {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response)
            {
                if (response.isSuccessful())
                {
                    mPresenter.setupResponseUpdate(response.body());
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
