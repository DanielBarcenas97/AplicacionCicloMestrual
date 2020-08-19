package mx.zublime.prediciclo.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.NumberPicker;

import androidx.fragment.app.DialogFragment;

import java.util.List;

public class NumberPickerDialog extends DialogFragment {

    private NumberPicker.OnValueChangeListener valueChangeListener;
    private String titulo;
    private String subTitulo;
    private String[] listDias;
    private int maxValue;
    private int minValue;
    private  final String  ACERPTAR = "ACEPTAR";
    private  final  String CANCEAR = "CANCELAR";


    public NumberPickerDialog(String[] listDias, String titulo, String subtitulo , int maxValue, int minValue){
        this.titulo = titulo;
        this.subTitulo = subtitulo;
        this.listDias = listDias;
        this.maxValue = maxValue;
        this.minValue = minValue;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final NumberPicker numberPicker = new NumberPicker(getActivity());

        numberPicker.setMinValue(minValue);
        numberPicker.setMaxValue(maxValue);
        numberPicker.setDisplayedValues(listDias);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(titulo);
        builder.setMessage(subTitulo);
        builder.setNeutralButton(CANCEAR, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               /* valueChangeListener.onValueChange(numberPicker,
                        numberPicker.getValue(), numberPicker.getValue());*/
            }
        });

        builder.setNegativeButton(ACERPTAR, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                valueChangeListener.onValueChange(numberPicker,
                        numberPicker.getValue(), numberPicker.getValue());
            }
        });

        builder.setView(numberPicker);
        return builder.create();
    }

    public NumberPicker.OnValueChangeListener getValueChangeListener() {
        return valueChangeListener;
    }

    public void setValueChangeListener(NumberPicker.OnValueChangeListener valueChangeListener) {
        this.valueChangeListener = valueChangeListener;
    }

}
