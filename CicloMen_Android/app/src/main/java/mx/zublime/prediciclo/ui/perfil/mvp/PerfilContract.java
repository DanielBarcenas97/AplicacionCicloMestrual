package mx.zublime.prediciclo.ui.perfil.mvp;

import mx.zublime.prediciclo.data.models.BaseResponse;

public interface PerfilContract {

    interface  PerfilView{

        void showDialog();
        void  hideDialog();
        void setActualizarDatos(boolean result);
        void closeActivityError();
    }

    interface PerfilPresenter{
        void actualizarDatos(String id, String duracionPeriodo, String duracionCiclo, String fechaNacimiento);
        void  responseActualizarDataos(BaseResponse response);

    }

    interface PerfilModel{
        void actualizarDatos(String id, String duracionPeriodo, String duracionCiclo, String fechaNacimiento);

    }

}
