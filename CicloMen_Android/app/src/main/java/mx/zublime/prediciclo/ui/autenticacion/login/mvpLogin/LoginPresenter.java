package mx.zublime.prediciclo.ui.autenticacion.login.mvpLogin;

import com.google.gson.JsonObject;

import mx.zublime.prediciclo.data.models.BaseResponse;
import mx.zublime.prediciclo.data.models.LoginResponse;
import mx.zublime.prediciclo.data.models.LoginResponseOne;
import mx.zublime.prediciclo.data.models.ResponseForgot;
import mx.zublime.prediciclo.data.models.ResponseUser;
import mx.zublime.prediciclo.resources.ResourcesUtils;

public class LoginPresenter implements LoginContract.LoginContractPresenter {

    private LoginContract.LoginContracView mView;
    private  LoginContract.LoginContractModel mModel;

    public LoginPresenter(LoginContract.LoginContracView view){
        this.mView = view;
        this.mModel = new LoginModel(this);
    }


    @Override
    public void autenticarUsuarioStepOne(String email) {
        if(mView != null){
            mView.showDialog("Cargando información...");
            mModel.getAutenticarUsuarioStepOne(email);
        }
    }

    @Override
    public void responseAutenticarUsuarioStepOne(LoginResponseOne response) {
        mView.hideDialog();
             if(response != null){
                 if(response.getStatus() == 200){
                     if(response.getData().isEmail_exists()){
                         // si exite el usuario
                         mView.setAutenticarUsuarioStepOneExito();
                     }else {
                         // no existe el usuario
                         mView.setAutenticarUsuarioStepOneError(ResourcesUtils.MESSAGE_RESPUESTAS_SERVICE.EMAIL_INVALIDO);
                     }
                 }
             }
    }

    @Override
    public void autenticarUsuarioStepTwo(String userName, String password) {
          if(mView != null){
              mView.showDialog("Validando usuaria");
              mModel.getAutenticarUsuarioStepTwo(userName,password);
          }
    }

    @Override
    public void recuperarPassword(String correo)
    {
        mView.showDialog("Recuperando contraseña");
        JsonObject request = new JsonObject();
        request.addProperty("email",correo);
        mModel.recuperarPassword(request);
    }

    @Override
    public void onRecuperrarPasword(ResponseForgot response)
    {
        mView.hideDialog();
        switch (response.getStatus())
        {
            case 200:
                mView.setPasswordDialog(response.getMessage());
                break;
            case 404:
                mView.setShowError("No pudimos encontrar tu correo");
                break;
            case 500:
                mView.setShowError("Ops ha ocurrido un error de nuestro lado, intentalo mas tarde");
                break;
        }
    }

    @Override
    public void responseAutenticarUsuarioStepTwo(LoginResponse response) {
        mView.hideDialog();
        if(response != null){
            if(response.getStatus() == 200){
                if(response.getData() != null ){
                    // login correcto
                    mView.setAutenticarUsuarioStepTwoExito(response.getData().getUser_id());

                }else {
                    //  datos vacios
                    mView.setAutenticarUsuarioStepTwoError(ResourcesUtils.MESSAGE_RESPUESTAS_SERVICE.ERROR_DEL_SERVIDOR);
                }
            }else {
                if(response.getStatus() == 403 && response.getData() == null){
                    // Autentication incorrecta -- usuario o contrasela incorrecta
                    mView.setAutenticarUsuarioStepTwoError(ResourcesUtils.MESSAGE_RESPUESTAS_SERVICE.EMAIL_END_USER_INCORRECTO);
                }else {
                    mView.setAutenticarUsuarioStepTwoError(ResourcesUtils.MESSAGE_RESPUESTAS_SERVICE.ERROR_DEL_SERVIDOR);
                    //  Nunca ocurrira este caso si los servicios responden correctamente
                }
            }
        }

    }

    @Override
    public void showError()
    {
        mView.hideDialog();
        mView.setShowError("Ops ha ocurrido un error de nuestro lado.");
    }

    @Override
    public void consultarInfo(String user, String password)
    {
        mView.showDialog("Obteniendo datos...");
        JsonObject request = new JsonObject();
        request.addProperty("PHP_AUTH_USER",user);
        request.addProperty("PHP_AUTH_PW",password);
        mModel.consultarInfo(request);
    }

    @Override
    public void onSaveInfo(ResponseUser response)
    {
        mView.hideDialog();
        switch (response.getStatus())
        {
            case 200:
                if (response.getData() != null)
                {
                    mView.saveUserInfo(response);
                }
                else
                {
                    mView.setShowError("Ops ha ocurrido un error de nuestro lado, intentalo mas tarde");
                }
                break;
            case 403:
                //CerrarApp
                break;
            case 500:
                mView.setShowError("Ops ha ocurrido un error de nuestro lado, intentalo mas tarde");
                break;
        }
    }
}
