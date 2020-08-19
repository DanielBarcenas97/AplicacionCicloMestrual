package mx.zublime.prediciclo.ui.pedido.datospago;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.redmadrobot.inputmask.MaskedTextChangedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import mx.zublime.prediciclo.R;
import mx.zublime.prediciclo.data.local.SavePreferenceInterface;
import mx.zublime.prediciclo.data.local.SavePreferenceManager;
import mx.zublime.prediciclo.data.models.TarjetaBancaria;
import mx.zublime.prediciclo.ui.pedido.datospago.mvpopenpay.OpenPayContract;
import mx.zublime.prediciclo.ui.pedido.datospago.mvpopenpay.OpenPayPresenter;
import mx.zublime.prediciclo.util.CreditCardFormatTextWatcher;

public class FormTarjetaActivity extends AppCompatActivity  {

    @BindView(R.id.nombre_titular_textinput_layout)
    TextInputLayout textInputLayoutNombreTitular;
    @BindView(R.id.nombre_titular_edittext)
    TextInputEditText editNombreTitular;
    @BindView(R.id.num_tarjeta_textinputlayout)
    TextInputLayout textInputLayoutNumeroTarjeta;
    @BindView(R.id.num_tarjeta_edittext)
    TextInputEditText editNumTarjeta;
    @BindView(R.id.ccv_textinputlayout)
    TextInputLayout textInputLayoutCcv;
    @BindView(R.id.ccv_edittext)
    TextInputEditText editCcv;
    @BindView(R.id.fecha_vencimiento_textinputlayout)
    TextInputLayout textInputLayoutFecha;
    @BindView(R.id.fecha_vencimiento_edittext)
    TextInputEditText editFecha;
    @BindView(R.id.btn_guardar)
    MaterialButton btnGuardar;
    @BindView(R.id.txv_numero_tarjeta)
    TextView tvNumTarjeta;
    @BindView(R.id.txv_fecha)
    TextView tvFecha;
    @BindView(R.id.img_master)
    ImageView imgIconCard;
    private CreditCardFormatTextWatcher tvWacher;
    private int iniYear;
    private String stringYear;
    private   int yt;;
    private boolean isNumberValid = false;
    private boolean isDateValid = false;
    private boolean isCvvValid = false;
    private static final int MAXLENGTHTARJETA = 16;
    private OpenPayPresenter mPresenter;
    private SweetAlertDialog mDialog;
    private SavePreferenceManager mPreferences;
    private static final  String SCREEN = "SCREEN";
    private  static final  int SCREEN_FOR_ACTIVITY_RESULT = 1;
    private  static final  int SCREEN_NORMAL_RESULT = 2;
    private int valorDeScreen;
    private int tipoTarjeta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_pago);
        ButterKnife.bind(this);
        initViews();
        dataSetup();
    }

    private void initViews(){
        //mPresenter = new OpenPayPresenter(this);
        tvWacher = new CreditCardFormatTextWatcher(tvNumTarjeta);
        editNumTarjeta.addTextChangedListener(tvWacher);
        mPreferences = new SavePreferenceManager(this);
        setupListeners();
    }

    private void dataSetup(){
        if(getIntent()!=null && getIntent().getExtras()!=null){
            valorDeScreen = getIntent().getIntExtra(SCREEN,0);
        }else {

        }
    }

    private void setupListeners(){
        List<String> affineFormats = new ArrayList<>();
        affineFormats.add("[00]{/}[00]");
        MaskedTextChangedListener listener = new MaskedTextChangedListener(
                "[00]{/}[00]",
                affineFormats,
                editFecha,
                new MaskedTextChangedListener.ValueListener() {
                    @Override
                    public void onTextChanged(boolean maskFilled, @NonNull final String extractedValue) {
                        tvFecha.setText(editFecha.getText().toString());

                        if(validaFechaVigencia()){
                            isDateValid = true;
                        }else {
                            isDateValid = false;
                        }
                        validarDatos();

                    }
                }
        );
        editFecha.addTextChangedListener(listener);


        editNumTarjeta.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textInputLayoutNumeroTarjeta.setErrorEnabled(false);
                if (validarTarjeta(s.toString())) {
                    textInputLayoutNumeroTarjeta.setErrorEnabled(false);
                    isNumberValid = true;
                    validarDatos();
                } else {
                    textInputLayoutNumeroTarjeta.setError("Tarjeta invalida");
                    isNumberValid = false;
                    validarDatos();
                }

                if (s.toString().length() < 16) {
                    textInputLayoutNumeroTarjeta.setErrorEnabled(false);
                }

                if(start == 0){
                    String master = "5";
                    String visa = "4";
                    String americanExpress = "3";

                    if(s.toString().intern().equals(visa.intern())){
                        imgIconCard.setImageResource(R.drawable.ic_visa);
                        tipoTarjeta = 4;
                    }else if(s.toString().intern().equals(master.intern())){
                        imgIconCard.setImageResource(R.drawable.ic_mastercard);
                        tipoTarjeta = 5;
                    }else if(s.toString().intern().equals(americanExpress.intern())){
                        imgIconCard.setImageResource(R.drawable.ic_american_express);
                        tipoTarjeta = 3;
                    }else {
                        tipoTarjeta = 0;
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        editNombreTitular.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    validarDatos();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editCcv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validarDatos();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }
    private void  validarDatos(){
        if(isNumberValid && isDateValid && editCcv.getText().length() == 3 && editNombreTitular.getText().length() > 10){
            btnGuardar.setEnabled(true);
        }else {
            btnGuardar.setEnabled(false);
        }
    }


    public static boolean validarTarjeta(String numeroTarjeta) {
        if(numeroTarjeta.length() < 16){
            return false;
        }

        int s1 = 0, s2 = 0;
        String reversa = new StringBuffer(numeroTarjeta).reverse().toString();
        for (int i = 0; i < reversa.length(); i++) {
            int digito = Character.digit(reversa.charAt(i), 10);
            if (i % 2 == 0) {//this is for odd digits, they are 1-indexed in the algorithm
                s1 += digito;
            } else {//add 2 * digit for 0-4, add 2 * digit - 9 for 5-9
                s2 += 2 * digito;
                if (digito >= 5) {
                    s2 -= 9;
                }
            }
        }
        return (s1 + s2) % 10 == 0;
    }

    public boolean validaFechaVigencia() {
        iniYear = Calendar.getInstance().get(Calendar.YEAR);
        stringYear = String.valueOf(iniYear);
        String y = stringYear.substring(2, 4);

        yt = Integer.valueOf(y);

        if (editFecha.getText().toString().length() == 2) {
            if (Integer.parseInt(editFecha.getText().toString()) < 1 || Integer.parseInt(editFecha.getText().toString()) > 12) {
                textInputLayoutFecha.setError("Fecha invalida");
                return false;
            } else {
                textInputLayoutFecha.setErrorEnabled(false);
            }
        } else if (editFecha.getText().toString().length() == 5) {

            String[] anio_str = editFecha.getText().toString().split("/");
            int anio = Integer.parseInt(anio_str[1]);

            if (anio < yt) {

                textInputLayoutFecha.setError("Fecha invalida");
                btnGuardar.setEnabled(false);
                return false;
            } else if (anio > (yt + 20)) {

                textInputLayoutFecha.setError("Fecha invalida");
                btnGuardar.setEnabled(false);
                return false;
            } else {
                textInputLayoutFecha.setErrorEnabled(false);
            }
        } else if (editFecha.getText().toString().isEmpty()) {
            textInputLayoutFecha.setErrorEnabled(false);
            return false;
        } else {
            textInputLayoutFecha.setError("Fecha invalida");
            return false;
        }

        return true;
    }

    @OnClick(R.id.btn_back)
    public  void closed(){
        finish();
    }

    @OnClick(R.id.btn_guardar)
    public  void guardar()
    {
        Timer timerObj = new Timer();

        showDialogGeneral("Guardando...");

        guardatDatosTarjeta();

        timerObj.schedule(new TimerTask() {
            public void run() {
                hideDialogGeneral();
                validarNavegabilidad();
            }
        }, 1000);


       /* String monthString = editFecha.getText().toString();
        int month = Integer.valueOf(monthString.substring(0,2));
        String yearString = editFecha.getText().toString();
        int year = Integer.valueOf(yearString.substring(3,5));*/


       // mPresenter.generatePayment(editNombreTitular.getText().toString(),editNumTarjeta.getText().toString(),month,year,editCcv.getText().toString());
    }

    private void guardatDatosTarjeta() {
        if(mPreferences.getString(SavePreferenceInterface.TarjetasBancarias.CARD_1).isEmpty()){
            mPreferences.putString(SavePreferenceInterface.TarjetasBancarias.CARD_1,genrarJsonTarjeta());
            mPreferences.putString(SavePreferenceInterface.TarjetasBancarias.MASCARA_DE_NUMERO_1,maskCardNumber(editNumTarjeta.getText().toString(),"xxxx-xxxx-xxxx-####"));
        }else if(mPreferences.getString(SavePreferenceInterface.TarjetasBancarias.CARD_2).isEmpty()){
            mPreferences.putString(SavePreferenceInterface.TarjetasBancarias.CARD_2,genrarJsonTarjeta());
            mPreferences.putString(SavePreferenceInterface.TarjetasBancarias.MASCAR_DE_NUMERO_2,maskCardNumber(editNumTarjeta.getText().toString(),"xxxx-xxxx-xxxx-####"));

        }
    }

    private String  genrarJsonTarjeta(){
        TarjetaBancaria objTarjeta = new TarjetaBancaria();
        objTarjeta.setNombreTitular(editNombreTitular.getText().toString());
        objTarjeta.setNumeroTarjeta(editNumTarjeta.getText().toString());
        objTarjeta.setCcv(editCcv.getText().toString());
        objTarjeta.setFechaVencimiento(editFecha.getText().toString());
        objTarjeta.setTipoTarjeta(tipoTarjeta);
        objTarjeta.setNumeroTarjetaConMascara(maskCardNumber(editNumTarjeta.getText().toString(),"xxxx-xxxx-xxxx-####"));
        Gson gson = new Gson();
        String jsonObjetString = gson.toJson(objTarjeta);
        return  jsonObjetString;
    }

    private void validarNavegabilidad(){
        if(valorDeScreen == SCREEN_FOR_ACTIVITY_RESULT){
            setResult(Activity.RESULT_OK);
            finish();
        }else {
            Intent mIntent = new Intent(FormTarjetaActivity.this,SeleccionFormaPagoActivity.class);
            startActivity(mIntent);
            finish();
        }
    }

    public static Intent createIntentASeleccionPago(Context context, int value){
        Intent mIntent = new Intent( context,FormTarjetaActivity.class);
        mIntent.putExtra(SCREEN,value);
        return mIntent;
    }

    public static String maskCardNumber(String cardNumber, String mask) {

        //format the number
        int index = 0;
        StringBuilder maskedNumber = new StringBuilder();
        for (int i = 0; i < mask.length(); i++) {
            char c = mask.charAt(i);
            if (c == '#') {
                maskedNumber.append(cardNumber.charAt(index));
                index++;
            } else if (c == 'x') {
                maskedNumber.append(c);
                index++;
            } else {
                maskedNumber.append(c);
            }
        }

        //return the masked number
        return maskedNumber.toString();
    }


   /* @Override
    public void successPay() {

        mDialog = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE);
        //mDialog.getProgressHelper().setBarColor(Color.parseColor("#DF7C73"));
        mDialog.setTitleText("Pago realizado");
        mDialog.setConfirmButton("Aceptar", SweetAlertDialog::dismissWithAnimation);
        mDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sDialog) {
                    mDialog.dismiss();
                    finish();

            }
        });
        mDialog.setCancelable(false);
        mDialog.show();

    }*/

    /*private void actualizarOrden()
    {
        int id = mPreferences.getInt(SavePreferenceInterface.DatosUsuario.USER_ID);
    }*/

   /* @Override
    public void errorPay() {
        mDialog = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE);
        //mDialog.getProgressHelper().setBarColor(Color.parseColor("#DF7C73"));
        mDialog.setTitleText("Error al procesar su pago");
        mDialog.setConfirmButton("Aceptar", SweetAlertDialog::dismissWithAnimation);
        mDialog.setCancelable(false);
        mDialog.show();

    }*/

   /* @Override
    public void showDialog() {
        mDialog = new SweetAlertDialog(FormTarjetaActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        mDialog.getProgressHelper().setBarColor(Color.parseColor("#DF7C73"));
        mDialog.setTitleText("Cargando información...");
        mDialog.setCancelable(false);
        mDialog.show();

    }*/
 /*
    @Override
    public void hideDialog() {
       mDialog.dismiss();
    }

    @Override
    public void onSuccessUpdate(String message) {

    }*/

    private void showDialogGeneral(String message) {
        mDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        mDialog.getProgressHelper().setBarColor(Color.parseColor("#DF7C73"));
        mDialog.setTitleText(message);
        mDialog.setCancelable(false);
        mDialog.show();
    }

    private void hideDialogGeneral() {
        mDialog.dismiss();
    }
}
