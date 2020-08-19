package mx.zublime.prediciclo.ui.home.mvp;

import android.util.Log;

import com.google.gson.JsonObject;

import mx.zublime.prediciclo.data.api.ApiAdapter;
import mx.zublime.prediciclo.data.local.SavePreferenceManager;
import mx.zublime.prediciclo.data.models.BaseResponse;
import mx.zublime.prediciclo.data.models.ResponseCalendar;
import mx.zublime.prediciclo.data.models.ResponseUpdateCalendar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalendarModel implements CalendarContract.CalendarModelContract
{

    private CalendarPresenter mPresenter;

    public CalendarModel(CalendarPresenter presenter)
    {
        this.mPresenter = presenter;
    }

    @Override
    public void consultarCalendario(String numeroPeriodo, String user, String password) {

        Call<ResponseCalendar> call = ApiAdapter.getApiService().consultarCalendario(numeroPeriodo,user,password);
        call.enqueue(new Callback<ResponseCalendar>() {
            @Override
            public void onResponse(Call<ResponseCalendar> call, Response<ResponseCalendar> response) {
               if(response.isSuccessful()){
                   mPresenter.setConsultarCalendario(response.body());
               }
            }

            @Override
            public void onFailure(Call<ResponseCalendar> call, Throwable t) {
                Log.wtf("Error",t.getMessage());
            }
        });

    }

    @Override
    public void updateCalendar(JsonObject request)
    {
        Call<ResponseUpdateCalendar> call = ApiAdapter.getApiService().updateCalendar(request);
        call.enqueue(new Callback<ResponseUpdateCalendar>() {
            @Override
            public void onResponse(Call<ResponseUpdateCalendar> call, Response<ResponseUpdateCalendar> response)
            {
                if (response.isSuccessful())
                {
                    mPresenter.onUpdate(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseUpdateCalendar> call, Throwable t)
            {

            }
        });
    }

    @Override
    public void updateUserInfo(JsonObject request)
    {
        Call<BaseResponse> call = ApiAdapter.getApiService().actualizarInformacionDelUsuario(request);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response)
            {
                if (response.isSuccessful())
                {
                    mPresenter.onUpdateUser(response.body());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t)
            {

            }
        });
    }

    @Override
    public void clearCalendar(JsonObject request)
    {
        Call<BaseResponse> call = ApiAdapter.getApiService().clearCalendar(request);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response)
            {
                if (response.isSuccessful())
                {
                    mPresenter.onClearCalendar(response.body());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t)
            {

            }
        });
    }


}
