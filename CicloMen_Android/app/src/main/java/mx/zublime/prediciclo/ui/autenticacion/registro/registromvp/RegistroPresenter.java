package mx.zublime.prediciclo.ui.autenticacion.registro.registromvp;

import android.util.Log;

import com.google.gson.JsonObject;

import mx.zublime.prediciclo.data.models.BaseResponse;
import mx.zublime.prediciclo.data.models.ResponseRegistro;

public class RegistroPresenter implements RegistroContract.RegistroContractPresenter
{

    private RegistroContract.RegistroContractModel mModel;
    private RegistroContract.RegistroContractView mView;

    private String mPassword;

    public RegistroPresenter(RegistroContract.RegistroContractView view)
    {
        this.mView = view;
        this.mModel = new RegistroModel(this);
    }

    @Override
    public void registrarUsuario(String user, String password)
    {
        mPassword = password;
        JsonObject request = new JsonObject();
        request.addProperty("email",user);
        request.addProperty("first_name","");
        request.addProperty("last_name","");
        request.addProperty("username",user);
        mModel.registrarUsuario(request);
        mView.showAlert("Registrando usuaria");
    }


    @Override
    public void setupResposeRegistro(ResponseRegistro response)
    {
        mView.hideAlert();
        try
        {
            if (response.getData() == null)
            {
                int id = response.getId();
                JsonObject request = new JsonObject();
                request.addProperty("user_id",id);
                request.addProperty("new_password",mPassword);
                mView.onSuccess(id);
                mModel.cambiarPassword(request);
            }
            else
            {
                mView.onFailure(response.getMessage());
            }
        }
        catch (NullPointerException ex)
        {
            ex.printStackTrace();
            mView.hideAlert();
            mView.onFailure("Parece que ha ocurrido un error, vuelve a intentarlo más tarde.");
        }
    }

    @Override
    public void setupResponseUpdate(BaseResponse response)
    {
        mView.hideAlert();
        try
        {
            if (response.getStatus() == 200)
            {
                mView.onSuccessRegistro();
            }
        }
        catch (NullPointerException ex)
        {
            mView.onFailure("Ha ocurrido un error");
        }
    }

    @Override
    public void onFailure(String menssage)
    {
        mView.hideAlert();
        mView.onFailure(menssage);
    }

    @Override
    public void onDestroy()
    {
        mView = null;
        mModel = null;
    }
}
