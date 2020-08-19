package mx.zublime.prediciclo.ui.home.mvp;

import android.util.Log;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mx.zublime.prediciclo.data.models.BaseResponse;
import mx.zublime.prediciclo.data.models.ResponseCalendar;
import mx.zublime.prediciclo.data.models.ResponseUpdateCalendar;
import mx.zublime.prediciclo.util.ActionCodes;

public class CalendarPresenter implements CalendarContract.CalendarPresenterContract
{

    private CalendarContract.CalendarModelContract mModel;
    private CalendarContract.CalendarViewContract mView;

    private static int CASE_1 = 1;
    private static int CASE_2 = 2;
    private static int CASE_3 = 3;
    private static int CASE_4 = 4;
    private static int CASE_5 = 5;
    private static int CASE_6 = 6;
    private static int CASE_7 = 7;
    private static int CASE_8 = 8;
    private static int CASE_9 = 9;
    private static int CASE_10 = 10;
    private static int CASE_11 = 11;
    private static int CASE_12 = 12;
    private static int CASE_13 = 13;
    private static int CASE_14 = 14;
    private static int CASE_15 = 15;
    private static int CASE_16 = 16;


    public CalendarPresenter (CalendarContract.CalendarViewContract view)
    {
        mView = view;
        mModel = new CalendarModel(this);
    }

    @Override
    public void consultarCalendario(String numeroPeriodo, String usuario, String password)
    {
        //mView.showAler("Consultando datos");
        if(mView != null){
            mView.showAler("Consultando datos");
            mModel.consultarCalendario(numeroPeriodo,usuario,password);
        }
    }

    @Override
    public void updateCalendar(String user, String password, String date,boolean resultadoPrueba)
    {
        mView.showAler("Actualizando datos");
        JsonObject request = new JsonObject();
        request.addProperty("PHP_AUTH_USER",user);
        request.addProperty("PHP_AUTH_PW",password);
        request.addProperty("resultado_prueba",resultadoPrueba);
        request.addProperty("date",date);
        mModel.updateCalendar(request);
    }

    @Override
    public void updateCalendar(String user, String password, String date,boolean resultadoPrueba, boolean reinicio_mestruacion)
    {
        mView.showAler("Actualizando datos");
        JsonObject request = new JsonObject();
        request.addProperty("PHP_AUTH_USER",user);
        request.addProperty("PHP_AUTH_PW",password);
        request.addProperty("resultado_prueba",resultadoPrueba);
        request.addProperty("reinicio_mestruacion",reinicio_mestruacion);
        request.addProperty("date",date);
        mModel.updateCalendar(request);
    }

    @Override
    public void setConsultarCalendario(ResponseCalendar responseCalendar)
    {
        //mView.hideAlert();
        HashMap<String, ResponseCalendar.Data> data = new HashMap<>();
        try
        {
            if(responseCalendar.getStatus() == 200)
            {
                if (responseCalendar.getData() != null)
                {
                    List<List> listaAllCase = new ArrayList<>();
                     List<ResponseCalendar.Data> listCase1 = new ArrayList<>();
                     List<ResponseCalendar.Data> listCase2 = new ArrayList<>();
                     List<ResponseCalendar.Data> listCase3 = new ArrayList<>();
                     List<ResponseCalendar.Data> listCase4 = new ArrayList<>();
                     List<ResponseCalendar.Data> listCase5 = new ArrayList<>();
                     List<ResponseCalendar.Data> listCase6 = new ArrayList<>();
                     List<ResponseCalendar.Data> listCase7 = new ArrayList<>();
                     List<ResponseCalendar.Data> listCase8 = new ArrayList<>();
                     List<ResponseCalendar.Data> listCase9 = new ArrayList<>();
                     List<ResponseCalendar.Data> listCase10 = new ArrayList<>();
                     List<ResponseCalendar.Data> listCase11 = new ArrayList<>();
                     List<ResponseCalendar.Data> listCase12 = new ArrayList<>();
                     List<ResponseCalendar.Data> listCase13 = new ArrayList<>();
                     List<ResponseCalendar.Data> listCase14 = new ArrayList<>();
                     List<ResponseCalendar.Data> listCase15= new ArrayList<>();
                     List<ResponseCalendar.Data> listCase16 = new ArrayList<>();

                    for (ResponseCalendar.Data dataItem : responseCalendar.getData())
                    {
                        data.put(dataItem.getFecha(),dataItem);
                        if(dataItem.getCaso() == CASE_1){
                            listCase1.add(dataItem);
                        }
                        if(dataItem.getCaso() == CASE_2){
                            listCase2.add(dataItem);
                        }

                        if(dataItem.getCaso() == CASE_3){
                            listCase3.add(dataItem);
                        }

                        if(dataItem.getCaso() == CASE_4){
                            listCase4.add(dataItem);
                        }

                        if(dataItem.getCaso() == CASE_5){
                            listCase5.add(dataItem);
                        }

                        if(dataItem.getCaso() == CASE_6){
                            listCase6.add(dataItem);
                        }

                        if(dataItem.getCaso() == CASE_7){
                            listCase7.add(dataItem);
                        }

                        if(dataItem.getCaso() == CASE_8){
                            listCase8.add(dataItem);
                        }

                        if(dataItem.getCaso() == CASE_9){
                            listCase9.add(dataItem);
                        }

                        if(dataItem.getCaso() == CASE_10){
                            listCase10.add(dataItem);
                        }

                        if(dataItem.getCaso() == CASE_11){
                            listCase11.add(dataItem);
                        }

                        if(dataItem.getCaso() == CASE_12){
                            listCase12.add(dataItem);
                        }

                        if(dataItem.getCaso() == CASE_13){
                            listCase13.add(dataItem);
                        }

                        if(dataItem.getCaso() == CASE_14){
                            listCase14.add(dataItem);
                        }

                        if(dataItem.getCaso() == CASE_15){
                            listCase15.add(dataItem);
                        }

                        if(dataItem.getCaso() == CASE_16){
                            listCase16.add(dataItem);
                        }

                    }

                    listaAllCase.add(listCase1);
                    listaAllCase.add(listCase2);
                    listaAllCase.add(listCase3);
                    listaAllCase.add(listCase4);
                    listaAllCase.add(listCase5);
                    listaAllCase.add(listCase6);
                    listaAllCase.add(listCase7);
                    listaAllCase.add(listCase8);
                    listaAllCase.add(listCase9);
                    listaAllCase.add(listCase10);
                    listaAllCase.add(listCase11);
                    listaAllCase.add(listCase12);
                    listaAllCase.add(listCase13);
                    listaAllCase.add(listCase14);
                    listaAllCase.add(listCase15);
                    listaAllCase.add(listCase16);
                    mView.hideAlert();
                    mView.paintCalendar(listaAllCase,data);
                }
                else if (responseCalendar.getMessageCode() == 500)
                {
                    //ciclo inválido
                    mView.hideAlert();
                    mView.showErrorMessage(ActionCodes.CICLO_INVALIDO);

                } else if(responseCalendar.getMessageCode() == 400){

                    mView.hideAlert();
                    mView.showErrorMessage();

                }
            }
            else if(responseCalendar.getStatus() == 403) {

                // Mostrar dialog loading que no permita hacer nada en la pantalla similar al que se usa
                // para mostrar el mensaje "Cargando datos" pero con un mensaje:
                // "Acceso no autorizado, cerrando sesión..."
                // Posteriormente de 3 segundos cerrar la sesión, limpiar los datos de usuario y enviar al login.
                mView.hideAlert();
                mView.closeActivity();

            }

        }catch (NullPointerException ex)
        {
            mView.hideAlert();
            ex.printStackTrace();
        }

    }

    @Override
    public void onUpdate(ResponseUpdateCalendar response)
    {
        mView.hideAlert();
        try
        {
            if (response != null)
            {
               switch (response.getStatus())
               {
                   case 200:
                       mView.updateCalendar();
                       break;
                   case 403:
                       mView.closeActivity();
                       break;
                   case 500:
                       mView.showGeneralError();
                       break;
               }
            }
        }
        catch (NullPointerException ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void updateUserInfo(int userId, String fechaInicioPeriodo)
    {
        mView.showAler("Actualizando datos");
        JsonObject request = new JsonObject();
        request.addProperty("user_id",userId);
        request.addProperty("fecha_inicio_periodo",fechaInicioPeriodo);
        mModel.updateUserInfo(request);
    }

    @Override
    public void onUpdateUser(BaseResponse response)
    {
        mView.hideAlert();
        switch (response.getStatus())
        {
            case 500:
                mView.showGeneralError();
                break;
            case 403:
                mView.closeActivity();
                break;
            case 200:
                mView.updateCalendar();
                break;
        }
    }

    @Override
    public void clearCalendar(String user, String password)
    {
        mView.showAler("Limpiando calendario");
        JsonObject request = new JsonObject();
        request.addProperty("PHP_AUTH_USER",user);
        request.addProperty("PHP_AUTH_PW",password);
        mModel.clearCalendar(request);
    }

    @Override
    public void onClearCalendar(BaseResponse response)
    {
        mView.hideAlert();
        switch (response.getStatus())
        {
            case 200:
                mView.showErrorMessage();
                break;
            case 403:
                mView.closeActivity();
                break;
            case 500:
                mView.showGeneralError();
                break;
        }
    }

    @Override
    public void onDestroy() {

    }
}
