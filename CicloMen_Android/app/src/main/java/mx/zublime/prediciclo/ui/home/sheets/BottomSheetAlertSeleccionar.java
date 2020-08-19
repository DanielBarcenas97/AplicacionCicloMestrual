package mx.zublime.prediciclo.ui.home.sheets;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textview.MaterialTextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import mx.zublime.prediciclo.R;
import mx.zublime.prediciclo.ui.home.interfaz.ISelectedButtonCommunication;
import mx.zublime.prediciclo.util.ActionCodes;

public class BottomSheetAlertSeleccionar extends BottomSheetDialogFragment
{
    @BindView(R.id.pregunta_borrar_textview)
    MaterialTextView title;
    @BindView(R.id.dia_calendar) CalendarView mDiaCalendar;
    private Unbinder mUnbinder;
    private ISelectedButtonCommunication mListener;
    private String mDate;
    private int action_code = 0;

    public BottomSheetAlertSeleccionar(ISelectedButtonCommunication listener) {
        this.mListener = listener;
    }

    public BottomSheetAlertSeleccionar(ISelectedButtonCommunication listener, int action_code) {
        this.mListener = listener;
        this.action_code = action_code;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_alert_seleccionar_periodo, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(this.action_code == ActionCodes.CICLO_INVALIDO) {
            title.setTextSize(15);
            title.setText(getString(R.string.selecciona_periodo_ciclo_invalido));
        }

        Calendar calendar = Calendar.getInstance();
        mDiaCalendar.setDate(calendar.getTimeInMillis());
        mDiaCalendar.setOnDateChangeListener((calendarView, i, i1, i2) ->
        {
            Calendar calendar_selected = Calendar.getInstance();
            calendar_selected.set(i, i1, i2);

            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            mDate = formatter.format(calendar_selected.getTime());

        });
    }

    @Override
    public void onDestroyView()
    {
        mUnbinder.unbind();
        super.onDestroyView();
    }

    @OnClick(R.id.guardar_button)
    public void onGuardarButtonClicked()
    {

        Calendar c = Calendar.getInstance();

        if (mDate == null)
        {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            mDate = formatter.format(c.getTime());
        }

        try {

            Calendar calendar_selected = Calendar.getInstance();
            Date date_selected = new SimpleDateFormat("yyyy-MM-dd").parse(mDate);
            calendar_selected.setTime(date_selected);

            Calendar c_limit_min = Calendar.getInstance();
            Calendar c_limit_max = Calendar.getInstance();

            c_limit_min.setTimeInMillis(c.getTimeInMillis() - (6 * 24 * 3600 * 1000));
            c_limit_max.setTimeInMillis(c.getTimeInMillis() + (0 * 24 * 3600 * 1000));

            if( c_limit_min.getTimeInMillis() >  calendar_selected.getTimeInMillis() ) {
                Toast.makeText(getContext(), "No puedes seleccionar una fecha 6 días antes del día de hoy", Toast.LENGTH_SHORT).show();
            }else if(calendar_selected.getTimeInMillis() > c_limit_max.getTimeInMillis()) {
                Toast.makeText(getContext(), "No puedes seleccionar una fecha posterior a hoy", Toast.LENGTH_SHORT).show();
            } else
            {

                if(this.action_code == ActionCodes.CICLO_INVALIDO){
                    mListener.onResultSaveDateAndClear(mDate);
                } else {
                    mListener.onResultSaveDate(mDate);
                }

                this.dismiss();
            }

        } catch (Exception e){

        }

    }

}
