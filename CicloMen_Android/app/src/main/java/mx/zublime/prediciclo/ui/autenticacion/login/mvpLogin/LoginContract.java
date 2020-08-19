package mx.zublime.prediciclo.ui.autenticacion.login.mvpLogin;

import com.google.gson.JsonObject;

import mx.zublime.prediciclo.data.models.BaseResponse;
import mx.zublime.prediciclo.data.models.LoginResponse;
import mx.zublime.prediciclo.data.models.LoginResponseOne;
import mx.zublime.prediciclo.data.models.ResponseForgot;
import mx.zublime.prediciclo.data.models.ResponseUser;

public interface LoginContract {

    interface LoginContracView{
      void setAutenticarUsuarioStepOneExito();
      void setAutenticarUsuarioStepOneError( String message);
      void setAutenticarUsuarioStepTwoExito(String userId);
      void setAutenticarUsuarioStepTwoError(String message);
      void setPasswordDialog(String message);
      void showDialog(String message);
      void hideDialog();
      void setShowError(String message);
      void saveUserInfo(ResponseUser response);
    }

    interface LoginContractPresenter{
        void autenticarUsuarioStepOne(String email);
        void responseAutenticarUsuarioStepOne(LoginResponseOne response);
        void autenticarUsuarioStepTwo(String userName, String password);
        void recuperarPassword(String correo);
        void onRecuperrarPasword(ResponseForgot response);
        void responseAutenticarUsuarioStepTwo(LoginResponse response);
        void showError();
        void consultarInfo(String user, String password);
        void onSaveInfo(ResponseUser response);

    }

    interface  LoginContractModel{
         void getAutenticarUsuarioStepOne(String email);
         void getAutenticarUsuarioStepTwo(String userName, String password);
         void recuperarPassword(JsonObject request);
         void consultarInfo(JsonObject request);
    }
}
