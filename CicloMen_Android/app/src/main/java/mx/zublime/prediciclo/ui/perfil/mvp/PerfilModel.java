package mx.zublime.prediciclo.ui.perfil.mvp;

import mx.zublime.prediciclo.data.api.ApiAdapter;
import mx.zublime.prediciclo.data.models.BaseResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilModel implements PerfilContract.PerfilModel {

    private PerfilPresenter mPresenter;


    public PerfilModel(PerfilPresenter mPresenter){
        this.mPresenter = mPresenter;
    }


    @Override
    public void actualizarDatos(String id, String duracionPeriodo, String duracionCiclo, String fechaNacimiento) {
        Call<BaseResponse> call = ApiAdapter.getApiService().actualizarInformacionBiomatrica(id,duracionPeriodo,duracionCiclo,fechaNacimiento);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if(response.isSuccessful()){
                    mPresenter.responseActualizarDataos(response.body());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

            }
        });
    }
}
