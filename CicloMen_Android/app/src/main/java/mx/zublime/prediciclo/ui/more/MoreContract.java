package mx.zublime.prediciclo.ui.more;

import com.google.gson.JsonObject;

import mx.zublime.prediciclo.data.models.BaseResponse;

public interface MoreContract
{
    interface MoreModelContract
    {
        void clearCalendar(JsonObject request);
    }

    interface MoreViewContract
    {
        void showDialog(String mensaje);
        void hideDialog();
        void closeActivity();
    }

    interface MorePresenterContract
    {
        void clearCalendar(String user, String password);
        void onClearCalendar(BaseResponse response);
    }

}
