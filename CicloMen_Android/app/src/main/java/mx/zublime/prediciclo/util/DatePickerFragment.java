package mx.zublime.prediciclo.util;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import mx.zublime.prediciclo.R;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener
{

    private static final String DATE_KEY = "mx.profuturo.profuturomvil.ui.pickers.DATE";
    final Calendar CALENDAR = Calendar.getInstance();

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        this.setStyle(DialogFragment.STYLE_NORMAL, R.style.prediciclo_color_picker);
        int day = CALENDAR.get(Calendar.DAY_OF_MONTH);
        int month = CALENDAR.get(Calendar.MONTH);
        int year = CALENDAR.get(Calendar.YEAR);

        return new DatePickerDialog(getActivity(), this,year,month,day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
    {
        CALENDAR.set(Calendar.YEAR,year);
        CALENDAR.set(Calendar.MONTH, month);
        CALENDAR.set(Calendar.DAY_OF_MONTH,dayOfMonth);

        String fechaSeleccionada = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(CALENDAR.getTime());

        getTargetFragment().onActivityResult(
                getTargetRequestCode(),
                Activity.RESULT_OK,
                new Intent().putExtra(DATE_KEY,fechaSeleccionada)
        );
    }

    public static String getDate(Intent args)
    {
        return args.getStringExtra(DATE_KEY);
    }
}
