package mx.zublime.prediciclo.ui.configuraciontur.mvp;

import com.google.gson.JsonObject;

import mx.zublime.prediciclo.data.models.BaseResponse;

public class ConfigurationPresenter implements ConfigurationContract.ConfigurationContractPresenter
{
    private ConfigurationContract.ConfigurationContractView mView;
    private ConfigurationContract.ConfigurationContractModel mModel;

    public ConfigurationPresenter(ConfigurationContract.ConfigurationContractView view)
    {
        mView = view;
        mModel = new ConfigurationModel(this);
    }

    @Override
    public void establecerDatos(String inicioPeriodo, String duracionPeriodo, String duracionCiclo, String fechaNacimiento, int id)
    {
        JsonObject request = new JsonObject();
        request.addProperty("user_id",id);
        request.addProperty("duracion_periodo",duracionPeriodo);
        request.addProperty("duracion_ciclo",duracionCiclo);
        request.addProperty("fecha_nacimiento",fechaNacimiento);
        request.addProperty("fecha_inicio_periodo",inicioPeriodo);
        mModel.establecerDatos(request);
        mView.showAlert("Guardando tus datos");
    }

    @Override
    public void setUpResponse(BaseResponse response)
    {
        mView.hideAlert();
        try
        {
            if (response.getStatus() == 200)
            {
                mView.exit();
            }
            else
            {
                mView.onFailure("Un error ocurrio mientras se establecia conexión con el servidor, intenta más tarde.");
            }
        }
        catch (NullPointerException ex)
        {
            mView.onFailure("Un error ocurrio, vuelve a intentarlo");
        }
    }

    @Override
    public void onDestroy()
    {
        mView = null;
        mModel = null;
    }

    @Override
    public void onFailure(String message)
    {
        mView.hideAlert();
        mView.onFailure(message);
    }
}
