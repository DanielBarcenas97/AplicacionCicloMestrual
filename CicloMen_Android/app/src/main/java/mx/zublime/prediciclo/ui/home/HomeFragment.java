package mx.zublime.prediciclo.ui.home;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.pedant.SweetAlert.SweetAlertDialog;
import mx.zublime.prediciclo.R;
import mx.zublime.prediciclo.base.BaseActivity;
import mx.zublime.prediciclo.data.local.SavePreferenceInterface;
import mx.zublime.prediciclo.data.local.SavePreferenceManager;
import mx.zublime.prediciclo.data.models.ResponseCalendar;
import mx.zublime.prediciclo.resources.ResourcesUtils;
import mx.zublime.prediciclo.ui.home.interfaz.ISelectedButtonCommunication;
import mx.zublime.prediciclo.ui.home.mvp.CalendarContract;
import mx.zublime.prediciclo.ui.home.mvp.CalendarPresenter;
import mx.zublime.prediciclo.ui.home.sheets.BottomSheetAlertBorrar;
import mx.zublime.prediciclo.ui.home.sheets.BottomSheetAlertSeleccionar;
import mx.zublime.prediciclo.ui.home.sheets.BottomSheetReiniciarMenstruacion;
import mx.zublime.prediciclo.ui.home.sheets.BottomSheetSolicitarPrueba;

/**
 * A simple {@link Fragment} subclass.
 */
public class
HomeFragment extends Fragment implements ISelectedButtonCommunication, CalendarContract.CalendarViewContract, OnDayClickListener
{

    @BindView(R.id.calendar)
    CalendarView mCalendar;
    @BindView(R.id.limpiar_calendario_button)
    MaterialButton mLimpiarCalendarioButton;
    @BindView(R.id.help_imagebutton)
    ImageButton mHelp;

    private Context mContext;
    private Unbinder mUnbinder;
    private CalendarPresenter mPresenter;
    private String user;
    private  String password;
    private SavePreferenceManager mPreferences;
    List<EventDay> events = new ArrayList<>();
    private HashMap<String, ResponseCalendar.Data> mData;
    private String mSelectedDate = "";
    private SweetAlertDialog mDialog;
    private boolean updateView = false;

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        mPresenter = new CalendarPresenter(this);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        mPreferences = new SavePreferenceManager(mContext);
        user = mPreferences.getString(SavePreferenceInterface.DatosUsuario.EMAIL);
        password = mPreferences.getString(SavePreferenceInterface.DatosUsuario.PASSWORD);
        Calendar min = Calendar.getInstance();
        min.set(Calendar.MONTH, min.get(Calendar.MONTH) - 1);
        min.set(Calendar.DAY_OF_MONTH, 0);
        Calendar max = Calendar.getInstance();
        int dias = max.getActualMaximum(Calendar.DAY_OF_MONTH);
        max.set(Calendar.MONTH, max.get(Calendar.MONTH) + 2);
        max.set(Calendar.DAY_OF_MONTH,dias);
        mCalendar.setMinimumDate(min);
        mCalendar.setMaximumDate(max);
        mCalendar.setOnDayClickListener(this::onDayClick);
        List<Calendar> days = getActiveDays();
        mData = new HashMap<>();
//        createEvents(days,1);
//        createEvents(days,2);
//         createEvents(days,3);
//        createEvents(days,4);
//        createEvents(days,5);
        mPresenter.consultarCalendario("3",user,password);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden)
           updateCalendar();
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();
    }

    @OnClick(R.id.limpiar_calendario_button)
    public void onViewClicked()
    {
        BottomSheetAlertBorrar alert = new BottomSheetAlertBorrar(this);
        alert.show(getFragmentManager(),"CLEAR");
    }

    @OnClick(R.id.help_imagebutton)
    public void onHelp()
    {
        Intent help = new Intent(mContext,InfoActivity.class);
        startActivity(help);
    }

    private List<Calendar> getActiveDays()
    {
        Calendar calendar = Calendar.getInstance();
        Calendar lastCalendar = Calendar.getInstance();
        Calendar nextCalendar = Calendar.getInstance();
        Calendar nextToCalendar = Calendar.getInstance();

        lastCalendar.set(Calendar.MONTH, lastCalendar.get(Calendar.MONTH) - 1);
        nextCalendar.set(Calendar.MONTH, nextCalendar.get(Calendar.MONTH) + 1);
        nextToCalendar.set(Calendar.MONTH, nextToCalendar.get(Calendar.MONTH) + 2);

        int actual = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int lastMont = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int next = nextCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int nextTo = nextToCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        List<Calendar> disbledDays = new ArrayList<>();

        for (int i = 1; i <= lastMont; i++)
        {
            Calendar item = Calendar.getInstance();
            item.set(Calendar.MONTH, item.get(Calendar.MONTH) - 1);
            item.set(Calendar.DAY_OF_MONTH,i);
            disbledDays.add(item);
        }

        for (int i = 1; i <= actual; i++)
        {
            Calendar item = Calendar.getInstance();
            item.set(Calendar.MONTH, item.get(Calendar.MONTH));
            item.set(Calendar.DAY_OF_MONTH,i);
            disbledDays.add(item);
        }

        for (int i = 1; i <= next; i++)
        {
            Calendar item = Calendar.getInstance();
            item.set(Calendar.MONTH, item.get(Calendar.MONTH) + 1);
            item.set(Calendar.DAY_OF_MONTH,i);
            disbledDays.add(item);
        }

        for (int i = 1; i <= nextTo; i++)
        {
            Calendar item = Calendar.getInstance();
            item.set(Calendar.MONTH, item.get(Calendar.MONTH) + 2);
            item.set(Calendar.DAY_OF_MONTH,i);
            disbledDays.add(item);
        }

        return disbledDays;
    }

    private void createEvents(List<Calendar> days, int type)
    {

        switch (type)
        {
            case 1: // menstruacion
                setupEvents(R.drawable.ic_raindrop_close_up,getResources().getColor(R.color.mint),days);
                break;
            case 2: //prueba
                setupEvents(R.drawable.ic_pregnancy_test, getResources().getColor(R.color.mint),days);
                break;
            case 3: //Pico fertilidad
                setupEvents(R.drawable.ic_pico,getResources().getColor(R.color.negro),days);
                break;
            case 4: //niño
                setupEvents(R.drawable.ic_baby_boy,getResources().getColor(R.color.negro),days);
                break;
            case 5: //niña niño pico
                setupEvents(R.drawable.ic_pico_bebes,getResources().getColor(R.color.negro),days);
                break;
            case 6: //niña
                setupEvents(R.drawable.ic_baby_girl,getResources().getColor(R.color.negro),days);
                break;
            case 7: //niña pico
                setupEvents(R.drawable.ic_pico_girl,getResources().getColor(R.color.negro),days);
                break;
            case 8: //niño pico
                setupEvents(R.drawable.ic_pico_boy,getResources().getColor(R.color.negro),days);
                break;
            case 9: //niña prueba pico
                setupEvents(R.drawable.ic_pico_test_girl,getResources().getColor(R.color.negro),days);
                break;
            case 10: //niño prueba
                setupEvents(R.drawable.ic_test_boy,getResources().getColor(R.color.negro),days);
                break;
            case 11: //niño niña prueba
                setupEvents(R.drawable.ic_test_girl_boy,getResources().getColor(R.color.negro),days);
                break;
            case 12: //niña prueba
                setupEvents(R.drawable.ic_test_girl,getResources().getColor(R.color.negro),days);
                break;
            case 13: //niña prueba
                setupEvents(R.drawable.ic_pico_test_girl_boy,getResources().getColor(R.color.negro),days);
                break;
            case 14: //niño prueba pico
                setupEvents(R.drawable.ic_pico_test_boy,getResources().getColor(R.color.negro),days);
                break;
            case 15: //niño prueba pico
                setupEvents(R.drawable.ic_boy_girl,getResources().getColor(R.color.negro),days);
                break;

        }


    }

    private void setupEvents(int drawable, int color, List<Calendar> days)
    {

        for (Calendar calendar : days) {
            events.add(new EventDay(calendar, drawable, color));
        }
    }

    private void clearCalendar(List<Calendar> days)

    {
        events.clear();
        mData = null;
        List<EventDay> event= new ArrayList<>();

        /*for (Calendar calendar : days)
        {
            event.add(new EventDay(calendar));
        }*/

        mCalendar.setEvents(event);
    }

    @Override
    public void onResultFragmentClicked(boolean result)
    {
        if (result)
        {
            clearCalendar(getActiveDays());
            mPresenter.clearCalendar(user,password);
        }
    }

    @Override
    public void onResultSaveDate(String date)
    {
        Log.wtf("DATE",date);
        int userId = mPreferences.getInt(SavePreferenceInterface.DatosUsuario.USER_ID);
        mPresenter.updateUserInfo(userId,date);
    }

    @Override
    public void onResultSaveDateAndClear(String date)
    {

        clearCalendar(getActiveDays());
        mPresenter.clearCalendar(user,password);

        Log.wtf("DATE",date);
        int userId = mPreferences.getInt(SavePreferenceInterface.DatosUsuario.USER_ID);
        mPresenter.updateUserInfo(userId,date);
    }

    @Override
    public void onResultSavePrueba(boolean isPositive)
    {
        mPresenter.updateCalendar(user,password,mSelectedDate,isPositive);
    }

    @Override
    public void onResultReniciarMenstruacion(boolean result) {
        mPresenter.updateCalendar(user,password,mSelectedDate,false, result);
    }

    @Override
    public void paintCalendar(List<List> daysAll,HashMap<String, ResponseCalendar.Data> data)
    {
        try{
            this.mData = data;
            int counter_index = 1;

            for(List<ResponseCalendar.Data> listas : daysAll){
                List<Calendar> lista = new ArrayList<>();
                int tipo = 0;
                for (ResponseCalendar.Data datos : listas) {
                    Calendar calendar = Calendar.getInstance();
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(datos.getFecha());
                    calendar.setTime(date);
                    tipo = datos.getCaso();
                    lista.add(calendar);
                }

                createEvents(lista,tipo);

                counter_index++;
            }



            mCalendar.setEvents(events);


        } catch (ParseException e) {
            Log.wtf("Error",e.getMessage());
        }

    }

    @Override
    public void updateCalendar()
    {
        clearCalendar(getActiveDays());
        mPresenter.consultarCalendario("3",user,password);
    }

    @Override
    public void showAler(String message)
    {
        mDialog = new SweetAlertDialog(mContext,SweetAlertDialog.PROGRESS_TYPE);
        mDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorPrimary));
        mDialog.setContentText(message);
        mDialog.setCancelable(false);
        mDialog.show();
    }

    @Override
    public void hideAlert()
    {
        mDialog.dismissWithAnimation();
    }

    @Override
    public void showErrorMessage()
    {
        BottomSheetAlertSeleccionar alert = new BottomSheetAlertSeleccionar(this);
        alert.setCancelable(false);
        alert.show(getFragmentManager(),"SELECT");
    }

    @Override
    public void showErrorMessage(int action_code)
    {
        BottomSheetAlertSeleccionar alert = new BottomSheetAlertSeleccionar(this, action_code);
        alert.setCancelable(false);
        alert.show(getFragmentManager(),"SELECT");
    }

    @Override
    public void showGeneralError()
    {
        mDialog = new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE);
        mDialog.setContentText("Un error ha ocurrido, vuelve a intentarlo mas tarde");
        mDialog.setConfirmButton("Aceptar", SweetAlertDialog::dismissWithAnimation);
        mDialog.show();
    }

    @Override
    public void closeActivity()
    {
        mDialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
        mDialog.setCancelable(false);
        mDialog.setContentText("Acceso no autorizado, cerrando sesión...");
        mDialog.show();
        new Handler().postDelayed(() -> {
            mDialog.dismissWithAnimation();
            ((BaseActivity)getActivity()).closeActivity();
        }, 3000);
        mPreferences.putBoolean(SavePreferenceInterface.Autentication.IS_LOGIN,false);

    }

    @Override
    public void onDayClick(EventDay eventDay)
    {

        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

            Calendar calendario_actual = Calendar.getInstance();
            String fecha_actual_str = formatter.format(calendario_actual.getTime());
            Date fecha_actual_obj = new SimpleDateFormat("yyyy-MM-dd").parse(fecha_actual_str);

            String fecha = formatter.format(eventDay.getCalendar().getTime());

            ResponseCalendar.Data dat = mData.get(fecha);
            if (fecha.equals(dat.getFecha()))
            {
                if(dat.isReiniciar_sangrado() && dat.isClick()) {

                    if (fecha_actual_obj.getTime() < eventDay.getCalendar().getTimeInMillis()) {

                        //La fecha escogida es superior al día de hoy

                    } else if (eventDay.getCalendar().getTimeInMillis() < (fecha_actual_obj.getTime() - (6 * 24 * 3600 * 1000))) {

                        //La fecha escogida es menor a seís días antes del día de hoy

                    } else {

                        Log.wtf("OBJCECT",new Gson().toJson(dat));
                        BottomSheetReiniciarMenstruacion dialog = new BottomSheetReiniciarMenstruacion(this);
                        dialog.show(getFragmentManager(),"REINICIAR");
                        mSelectedDate = fecha;

                    }

                }
                else if (dat.isSolicitar_prueba() && dat.isClick())
                {
                    Calendar fecha_seleccionada = Calendar.getInstance();
                    fecha_seleccionada.set(eventDay.getCalendar().get(Calendar.YEAR), eventDay.getCalendar().get(Calendar.MONTH), eventDay.getCalendar().get(Calendar.DAY_OF_MONTH), 0, 0, 0);

                    Calendar fecha_actual = Calendar.getInstance();
                    fecha_actual.set(fecha_actual.get(Calendar.YEAR), fecha_actual.get(Calendar.MONTH), fecha_actual.get(Calendar.DAY_OF_MONTH), 0, 0 ,0);

                    if(fecha_seleccionada.getTimeInMillis() > fecha_actual.getTimeInMillis()) {

                        //La fecha escogida es superior al día de hoy

                    } else if (fecha_seleccionada.getTimeInMillis() < fecha_actual.getTimeInMillis()) {

                        //La fecha escogida es menor al día de hoy menos seís días

                    } else {

                        Log.wtf("OBJCECT",new Gson().toJson(dat));
                        BottomSheetSolicitarPrueba dialog = new BottomSheetSolicitarPrueba(this);
                        dialog.show(getFragmentManager(),"PRUEBA");
                        mSelectedDate = fecha;

                    }

                }


            }
        }
        catch (NullPointerException ex)
        {
            ex.printStackTrace();

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

}
