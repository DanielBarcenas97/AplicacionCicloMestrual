package mx.zublime.prediciclo.ui.perfil.mvp;

import mx.zublime.prediciclo.data.models.BaseResponse;

public class PerfilPresenter implements PerfilContract.PerfilPresenter {

    private PerfilContract.PerfilView mView;
    private PerfilContract.PerfilModel mModel;

    public PerfilPresenter( PerfilContract.PerfilView mView){
        this.mView = mView;
        this.mModel = new PerfilModel(this);
    }

    @Override
    public void actualizarDatos(String id, String duracionPeriodo, String duracionCiclo, String fechaNacimiento) {
        if(mView != null){
            mView.showDialog();
            mModel.actualizarDatos(id,duracionPeriodo,duracionCiclo,fechaNacimiento);
        }

    }

    @Override
    public void responseActualizarDataos(BaseResponse response) {
        mView.hideDialog();
        switch (response.getStatus())
        {
            case 200:
                mView.setActualizarDatos(true);
                break;
            case 500:
                mView.setActualizarDatos(false);
                break;
            case 403:
                mView.closeActivityError();
                break;
        }
    }
}
