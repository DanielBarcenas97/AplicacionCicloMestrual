package mx.zublime.prediciclo.ui.more;

import com.google.gson.JsonObject;

import mx.zublime.prediciclo.data.models.BaseResponse;

public class MorePresenter implements MoreContract.MorePresenterContract
{

    private MoreContract.MoreModelContract mModel;
    private MoreContract.MoreViewContract mView;

    public MorePresenter(MoreContract.MoreViewContract view)
    {
        this.mView = view;
        this.mModel = new MoreModel(this);
    }

    @Override
    public void clearCalendar(String user, String password)
    {
        mView.showDialog("Limpiando calendario");
        JsonObject request = new JsonObject();
        request.addProperty("PHP_AUTH_USER",user);
        request.addProperty("PHP_AUTH_PW",password);
        mModel.clearCalendar(request);
    }

    @Override
    public void onClearCalendar(BaseResponse response)
    {
        mView.hideDialog();
        if (response.getStatus() == 403)
        {
            mView.closeActivity();
        }
    }
}
