package mx.zublime.prediciclo.ui.autenticacion.registro.registromvp;

import com.google.gson.JsonObject;

import mx.zublime.prediciclo.data.models.BaseResponse;
import mx.zublime.prediciclo.data.models.ResponseRegistro;

public interface RegistroContract
{
    interface RegistroContractModel
    {
        void registrarUsuario(JsonObject request);
        void cambiarPassword(JsonObject request);
    }

    interface RegistroContractView
    {
        void showAlert(String message);
        void hideAlert();
        void onSuccess(int id);
        void onSuccessRegistro();
        void onFailure(String error);
    }

    interface RegistroContractPresenter
    {
        void registrarUsuario(String user, String password);
        void setupResposeRegistro(ResponseRegistro response);
        void setupResponseUpdate(BaseResponse response);
        void onFailure(String message);
        void onDestroy();
    }
}