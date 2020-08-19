package mx.zublime.prediciclo.ui.perfil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTouch;
import butterknife.Unbinder;
import cn.pedant.SweetAlert.SweetAlertDialog;
import mx.zublime.prediciclo.R;
import mx.zublime.prediciclo.base.BaseActivity;
import mx.zublime.prediciclo.data.local.SavePreferenceInterface;
import mx.zublime.prediciclo.data.local.SavePreferenceManager;
import mx.zublime.prediciclo.ui.MainActivity;
import mx.zublime.prediciclo.ui.pedido.orden.PreparandoOrdenActivity;
import mx.zublime.prediciclo.ui.perfil.mvp.PerfilContract;
import mx.zublime.prediciclo.ui.perfil.mvp.PerfilPresenter;
import mx.zublime.prediciclo.util.DatePickerFragment;
import mx.zublime.prediciclo.util.NumberPickerDialog;


public class PerfilActivity extends BaseActivity implements View.OnClickListener, NumberPicker.OnValueChangeListener , PerfilContract.PerfilView {

    @BindView(R.id.btn_back)
    ImageButton btnBack;
    @BindView(R.id.btn_guardar)
    MaterialButton btnGuardar;
    private Unbinder mUnbinder;
    @BindView(R.id.alias_edittext)
    TextInputEditText editTextAlias;
    @BindView(R.id.tv_alias)
    TextView textViewAlias;
    @BindView(R.id.ciclo_edittext)
    TextInputEditText editTextCiclo;
    @BindView(R.id.periodo_edittext)
    TextInputEditText editTextPeriodo;
    @BindView(R.id.fecha_edittext)
    TextInputEditText editTextFecha;
    @BindView(R.id.alias_textinputlayout)
    TextInputLayout textInputLayoutAlias;
    private DatePickerDialog datePickerDialog;
    private SavePreferenceManager mPreferences;
    private final int DATE_REQUEST = 27;
    private String apodo;
    private  String duracionCiclo;
    private String duracionPeriodo;
    private String fechaNacicmiento;
    private String[] diasCicloArray;
    private String TITLE_PERIODO;
    private String TITLE_CICLO;
    private  final   String SUBTITLE = "selecciona un valor";
    private int optionOriginal;
    private SweetAlertDialog mDialog;
    private PerfilPresenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        mUnbinder = ButterKnife.bind(this);
        initViews();
    }

    private void initViews(){
        TITLE_PERIODO = getResources().getString(R.string.configuracion_periodo_editar);
        TITLE_CICLO = getResources().getString(R.string.configuracion_ciclo_editar);

        mPreferences = new SavePreferenceManager(this);
        mPresenter = new PerfilPresenter(this);
        btnBack.setOnClickListener(this);
        btnGuardar.setOnClickListener(this);
        editTextPeriodo.setOnClickListener(this);
        editTextCiclo.setOnClickListener(this);
        initData();
        initWatchers();
        initListener();
    }

    private void initData(){
        apodo = (mPreferences.getString(SavePreferenceInterface.DatosUsuario.USER_APODO) != null )? mPreferences.getString(SavePreferenceInterface.DatosUsuario.USER_APODO) :
                mPreferences.getString(SavePreferenceInterface.DatosUsuario.EMAIL) ;
        textViewAlias.setText(apodo);
        duracionPeriodo = mPreferences.getString(SavePreferenceInterface.DatosBiologicos.DURACION_PERIODO_MENSTRUAL);
        duracionCiclo = mPreferences.getString(SavePreferenceInterface.DatosBiologicos.DURACION_CICLO_MENSTRUAL);
        String fechaNacimiento = mPreferences.getString(SavePreferenceInterface.DatosUsuario.FECHA_NACIMIENTO);
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        String reformattedStr = "";
        try {
            reformattedStr = myFormat.format(myFormat.parse(fechaNacimiento));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        editTextAlias.setText(apodo);
        editTextPeriodo.setText(duracionPeriodo);
        editTextCiclo.setText(duracionCiclo);
        editTextFecha.setText(reformattedStr);
    }

    private void initListener(){
        final Calendar myCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(myCalendar);
            }

        };

        editTextFecha.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(PerfilActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel(Calendar calendar) {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editTextFecha.setText(sdf.format(calendar.getTime()));
    }

    private void initWatchers(){
        editTextAlias.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textViewAlias.setText(editTextAlias.getText().toString());
                validarCampos();
                if(editTextAlias.getText().length() > 12){
                    textInputLayoutAlias.setError("Máximo 12 caracteres");
                    textInputLayoutAlias.setErrorEnabled(true);

                }else {
                    textInputLayoutAlias.setErrorEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editTextPeriodo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validarCampos();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editTextCiclo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validarCampos();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editTextFecha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validarCampos();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:

                Intent mIntent = new Intent(PerfilActivity.this, MainActivity.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mIntent.putExtra("TAG", 3);

                startActivity(mIntent);
                finish();

                break;

            case R.id.btn_guardar:
                actualizarInformacion();
                break;
            case R.id.periodo_edittext:
                mostrarNumberPiker(0);
                optionOriginal = 0;
                break;
            case  R.id.ciclo_edittext:
                mostrarNumberPiker(1);
                optionOriginal = 1;
                break;

        }
    }

    private void mostrarNumberPiker(int option){
        switch (option){
            case 0:
                NumberPickerDialog newFragmentPeriodo = new NumberPickerDialog(getDiasCicloArray(21,
                        42,1),TITLE_PERIODO,
                        SUBTITLE,42,21);
                newFragmentPeriodo.setValueChangeListener(this);
                newFragmentPeriodo.show(getSupportFragmentManager(), TITLE_PERIODO);
                break;

            case 1:

                NumberPickerDialog newFragmentCiclo = new NumberPickerDialog(getDiasCicloArray(3,
                        6,1),TITLE_CICLO,
                        SUBTITLE,6,3);

                newFragmentCiclo.setValueChangeListener(this);
                newFragmentCiclo.show(getSupportFragmentManager(), TITLE_CICLO);

                break;
        }
    }

    private String[] getDiasCicloArray(int min, int max, int increment) {
        int counter = 0;
        int delta = (int) ( ( (max - min) + increment )/increment);
        String[] dias  = new String[ delta ];

        while (counter < delta) {
            dias[counter] = String.valueOf(min);
            min += increment;
            counter++;
        }

        return dias;
    }

    /**
     * @deprecated
     */
    private String[] llenarListaDias( int sizeArray, int minValue, int maxValue, int incioCicloPeriodo , int diasFinal){
        int inicioVariable = incioCicloPeriodo;
          String[]dias  = new String[sizeArray];
        for (int inicio = 0; inicio <= diasFinal; inicio++) {
            dias[inicio] = String.valueOf(inicioVariable += 1);
        }

        return dias;
    }

    private void actualizarInformacion(){
        String id = String.valueOf( mPreferences.getInt(SavePreferenceInterface.DatosUsuario.USER_ID));
        String duracionPeriodo = editTextPeriodo.getText().toString();
        String duracionCiclo = editTextCiclo.getText().toString();
        String fecha = editTextFecha.getText().toString();
        mPreferences.putString( SavePreferenceInterface.DatosUsuario.USER_APODO ,!(TextUtils.isEmpty(editTextAlias.getText()))? editTextAlias.getText().toString() : "");
        mPresenter.actualizarDatos(id,duracionPeriodo,duracionCiclo,fecha);
    }

    private void validarCampos(){
        if(!TextUtils.isEmpty(editTextPeriodo.getText()) && !TextUtils.isEmpty(editTextCiclo.getText()) && !TextUtils.isEmpty(editTextFecha.getText()) ){
          btnGuardar.setEnabled(true);
        }else {
            btnGuardar.setEnabled(false);
        }

    }

    @Override
    protected void onDestroy() {
        if(mUnbinder != null){
            mUnbinder = null;
        }
        super.onDestroy();
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int valueSpinner, int i1) {
       if(optionOriginal == 0){
           editTextPeriodo.setText(String.valueOf(valueSpinner));
       }else {
           editTextCiclo.setText(String.valueOf(valueSpinner));
       }
    }

    @Override
    public void showDialog() {
        mDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        mDialog.getProgressHelper().setBarColor(Color.parseColor("#DF7C73"));
        mDialog.setTitleText("Actualizando");
        mDialog.setCancelable(false);
        mDialog.show();
    }

    @Override
    public void hideDialog() {
        mDialog.dismiss();
    }

    @Override
    public void setActualizarDatos(boolean result) {
        if(result){
            mDialog = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE);
            //mDialog.getProgressHelper().setBarColor(Color.parseColor("#DF7C73"));
            mDialog.setTitleText("Tus datos se actualizaron con éxito");
            mDialog.setConfirmButton("Aceptar", (dialog) -> {
                dialog.dismissWithAnimation();
                this.finish();
            });
            mDialog.setCancelable(false);
            mDialog.show();
            btnGuardar.setEnabled(false);
        }else {

        }
    }

    @Override
    public void closeActivityError()
    {
        mDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        mDialog.setCancelable(false);
        mDialog.setContentText("Acceso no autorizado, cerrando sesión...");
        mDialog.show();
        new Handler().postDelayed(() -> {
            mDialog.dismissWithAnimation();
            this.closeActivity();
        }, 3000);
        mPreferences.putBoolean(SavePreferenceInterface.Autentication.IS_LOGIN,false);
    }

    private void  mostrarError(){
        mDialog = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE);
        //mDialog.getProgressHelper().setBarColor(Color.parseColor("#DF7C73"));
        mDialog.setTitleText("Error al actualizar");
        mDialog.setConfirmButton("Aceptar", SweetAlertDialog::dismissWithAnimation);
        mDialog.setCancelable(false);
        mDialog.show();
    }
}
