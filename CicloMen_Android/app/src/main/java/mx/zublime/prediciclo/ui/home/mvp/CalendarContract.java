package mx.zublime.prediciclo.ui.home.mvp;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;

import mx.zublime.prediciclo.data.models.BaseResponse;
import mx.zublime.prediciclo.data.models.ResponseCalendar;
import mx.zublime.prediciclo.data.models.ResponseUpdateCalendar;

public interface CalendarContract
{
    interface CalendarModelContract
    {
        void consultarCalendario(String numeroPeriodo,String user,String password);
        void updateCalendar(JsonObject request);
        void updateUserInfo(JsonObject request);
        void clearCalendar(JsonObject request);
    }

    interface CalendarViewContract
    {
        void paintCalendar(List<List> daysAll, HashMap<String, ResponseCalendar.Data> data);
        void updateCalendar();
        void showAler(String message);
        void hideAlert();
        void showErrorMessage();
        void showErrorMessage(int action_code);
        void showGeneralError();
        void closeActivity();
    }

    interface CalendarPresenterContract
    {
        void consultarCalendario(String numeroPeriodo,String user, String password);
        void updateCalendar(String user, String password, String date, boolean resultadoPrueba);
        void updateCalendar(String user, String password, String date, boolean resultadoPrueba, boolean reinicio_mestruacion);
        void setConsultarCalendario(ResponseCalendar responseCalendar);
        void onUpdate(ResponseUpdateCalendar response);
        void updateUserInfo(int userId, String fechaInicioPeriodo);
        void onUpdateUser(BaseResponse response);
        void clearCalendar(String user, String password);
        void onClearCalendar(BaseResponse response);
        void onDestroy();
    }
}
