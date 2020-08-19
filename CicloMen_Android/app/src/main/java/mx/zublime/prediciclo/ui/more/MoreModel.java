package mx.zublime.prediciclo.ui.more;

import com.google.gson.JsonObject;

import mx.zublime.prediciclo.data.api.ApiAdapter;
import mx.zublime.prediciclo.data.models.BaseResponse;
import mx.zublime.prediciclo.data.models.ResponseUpdateCalendar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoreModel implements MoreContract.MoreModelContract
{
    private MorePresenter mPresenter;

    public MoreModel(MorePresenter presenter)
    {
        this.mPresenter = presenter;
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
