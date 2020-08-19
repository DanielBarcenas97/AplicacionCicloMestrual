package mx.zublime.prediciclo.ui.configuraciontur.mvp;

import com.google.gson.JsonObject;

import mx.zublime.prediciclo.data.models.BaseResponse;

public interface ConfigurationContract
{
    interface ConfigurationContractModel
    {
        void establecerDatos(JsonObject request);
    }

    interface ConfigurationContractView
    {
        void showAlert(String message);
        void hideAlert();
        void onFailure(String message);
        void exit();
    }

    interface ConfigurationContractPresenter
    {
        void establecerDatos(String inicioPeriodo, String diracionPeriodo, String duracionCiclo, String fechaNacimiento, int id);
        void setUpResponse(BaseResponse response);
        void onDestroy();
        void onFailure(String message);
    }
}
