package mx.zublime.prediciclo.ui.autenticacion.login.mvpLogin;

import com.google.gson.JsonObject;

import mx.zublime.prediciclo.data.api.ApiAdapter;
import mx.zublime.prediciclo.data.models.BaseResponse;
import mx.zublime.prediciclo.data.models.LoginResponse;
import mx.zublime.prediciclo.data.models.LoginResponseOne;
import mx.zublime.prediciclo.data.models.ResponseForgot;
import mx.zublime.prediciclo.data.models.ResponseUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginModel implements LoginContract.LoginContractModel {
    private LoginPresenter mLoginPresenter;

    public LoginModel(LoginPresenter loginPresenter) {
        this.mLoginPresenter = loginPresenter;
    }

    @Override
    public void getAutenticarUsuarioStepOne(String email) {
        Call<LoginResponseOne> call = ApiAdapter.getApiService().autenticarUsuarioStepOne(email);
        call.enqueue(new Callback<LoginResponseOne>() {
            @Override
            public void onResponse(Call<LoginResponseOne> call, Response<LoginResponseOne> response) {
                if(response.isSuccessful()){
                    mLoginPresenter.responseAutenticarUsuarioStepOne(response.body());
                }else {
                    mLoginPresenter.showError();
                }
            }

            @Override
            public void onFailure(Call<LoginResponseOne> call, Throwable t) {
                mLoginPresenter.showError();
            }
        });
    }

    @Override
    public void getAutenticarUsuarioStepTwo(String userName, String password) {

            Call<LoginResponse> call = ApiAdapter.getApiService().autenticarUsuarioStepTwo(userName,password);
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if(response.isSuccessful()){
                        mLoginPresenter.responseAutenticarUsuarioStepTwo(response.body());
                    }else {
                        mLoginPresenter.showError();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    mLoginPresenter.showError();
                }
            });
    }

    @Override
    public void recuperarPassword(JsonObject request) {
        Call<ResponseForgot> call = ApiAdapter.getApiService().recuperarPassword(request);
        call.enqueue(new Callback<ResponseForgot>() {
            @Override
            public void onResponse(Call<ResponseForgot> call, Response<ResponseForgot> response)
            {
                if (response.isSuccessful())
                {
                    mLoginPresenter.onRecuperrarPasword(response.body());
                }
                else
                {
                    mLoginPresenter.showError();
                }
            }

            @Override
            public void onFailure(Call<ResponseForgot> call, Throwable t)
            {
                mLoginPresenter.showError();
            }
        });
    }

    @Override
    public void consultarInfo(JsonObject request)
    {
        Call<ResponseUser> call = ApiAdapter.getApiService().consultarInfo(request);
        call.enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response)
            {
                if (response.isSuccessful())
                {
                    mLoginPresenter.onSaveInfo(response.body());
                }
                else
                {
                    mLoginPresenter.showError();
                }
            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t)
            {
                mLoginPresenter.showError();
            }
        });
    }
}
