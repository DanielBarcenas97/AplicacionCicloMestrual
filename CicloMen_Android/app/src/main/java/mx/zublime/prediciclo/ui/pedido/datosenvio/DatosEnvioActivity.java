package mx.zublime.prediciclo.ui.pedido.datosenvio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.ImageButton;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import mx.zublime.prediciclo.R;
import mx.zublime.prediciclo.data.local.SavePreferenceInterface;
import mx.zublime.prediciclo.data.local.SavePreferenceManager;
import mx.zublime.prediciclo.data.models.ResponseRegistrerCustomerOpenPay;
import mx.zublime.prediciclo.ui.pedido.datosenvio.mvpenvio.EnvioContract;
import mx.zublime.prediciclo.ui.pedido.datosenvio.mvpenvio.EnvioPresenter;
import mx.zublime.prediciclo.ui.pedido.datospago.FormTarjetaActivity;
import mx.zublime.prediciclo.ui.pedido.datospago.SeleccionFormaPagoActivity;
import mx.zublime.prediciclo.util.NavigatorUtils;

public class DatosEnvioActivity extends AppCompatActivity implements EnvioContract.EnvioView {


    @BindView(R.id.btn_back)
    ImageButton btnback;
    @BindView(R.id.nombre_textinput_layout)
    TextInputLayout textInputLayoutNombre;
    @BindView(R.id.nombre_edittext)
    TextInputEditText tvNombre;
    @BindView(R.id.apellido_textinputlayout)
    TextInputLayout textInputLayoutApellido;
    @BindView(R.id.apellido_edittext)
    TextInputEditText tvApellido;
    @BindView(R.id.direccion_1_textinputlayout)
    TextInputLayout textInputLayotDireccion;
    @BindView(R.id.direccion_1_edittext)
    TextInputEditText tvDireccion;
    @BindView(R.id.direccion_2_textinputlayout)
    TextInputLayout textInputLayoutDireccionDos;
    @BindView(R.id.direccion_2_edittext)
    TextInputEditText tvDireccionDos;
    @BindView(R.id.pais_textinputlayout)
    TextInputLayout textInputLayoutPais;
    @BindView(R.id.pais_edittext)
    TextInputEditText tvPais;
    @BindView(R.id.estado_textinputlayout)
    TextInputLayout textInputLayoutEstado;
    @BindView(R.id.estado_edittext)
    TextInputEditText tvEstado;
    @BindView(R.id.ciudad_textinputlayout)
    TextInputLayout textInputLayoutCiudad;
    @BindView(R.id.ciudad_edittext)
    TextInputEditText tvCiudad;
    @BindView(R.id.cp_textinputlayout)
    TextInputLayout textInputLayoutCp;
    @BindView(R.id.cp_edittext)
    TextInputEditText tvCp;
    @BindView(R.id.telefono_textinputlayout)
    TextInputLayout textInputLayoutTelefono;
    @BindView(R.id.telefono_edittext)
    TextInputEditText tvTelefono;
    @BindView(R.id.btn_guardar)
    MaterialButton btnGuardar;
    private String nombre;
    private String apellido;
    private String direccion;
    private String direccionDos;
    private String ciudad;
    private String estado;
    private String cp;
    private String pais;
    private String telefono;
    private EnvioPresenter mPresenter;
    private SavePreferenceManager mPreferences;
    private String idCliente;
    private SweetAlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_envio);
        ButterKnife.bind(this);
        initViews();
        setupListeners();
        setupData();
    }

    private void initViews(){
        mPresenter = new EnvioPresenter(this);
        mPreferences = new SavePreferenceManager(this);
    }

    private void  setupData(){
      idCliente = String.valueOf(mPreferences.getInt(SavePreferenceInterface.DatosUsuario.USER_ID));
      tvNombre.setText(mPreferences.getString(SavePreferenceInterface.DatosDireccion.FIRST_NAME));
      tvApellido.setText(mPreferences.getString(SavePreferenceInterface.DatosDireccion.LAST_NAME));
      tvDireccion.setText(mPreferences.getString(SavePreferenceInterface.DatosDireccion.ADDRESS_ONE));
      tvDireccionDos.setText(mPreferences.getString(SavePreferenceInterface.DatosDireccion.ADDRESS_TWO));
      tvEstado.setText(mPreferences.getString(SavePreferenceInterface.DatosDireccion.STATE));
      tvCiudad.setText(mPreferences.getString(SavePreferenceInterface.DatosDireccion.CITY));
      tvCp.setText(mPreferences.getString(SavePreferenceInterface.DatosDireccion.POST_CODE));
      tvTelefono.setText(mPreferences.getString(SavePreferenceInterface.Orden.PHONE));
    }

    private void  setupListeners(){
        tvNombre.addTextChangedListener(new TextWatcher() {
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
        tvApellido.addTextChangedListener(new TextWatcher() {
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

        tvDireccion.addTextChangedListener(new TextWatcher() {
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

        tvDireccionDos.addTextChangedListener(new TextWatcher() {
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

        tvCiudad.addTextChangedListener(new TextWatcher() {
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

        tvEstado.addTextChangedListener(new TextWatcher() {
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

        tvCp.addTextChangedListener(new TextWatcher() {
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

        tvTelefono.addTextChangedListener(new TextWatcher() {
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

    @OnClick(R.id.btn_back)
    public  void closed(){
        finish();
    }
    @OnClick(R.id.btn_guardar)
    public void guardarInformacion(){
       mPresenter.onUpdateInfoAddress(idCliente,createRqts());
    }

    private void  validarCampos(){
        if(!TextUtils.isEmpty(tvNombre.getText()) && !TextUtils.isEmpty(tvApellido.getText()) && !TextUtils.isEmpty(tvDireccion.getText())
                 && !TextUtils.isEmpty(tvNombre.getText()) && !TextUtils.isEmpty(tvCiudad.getText())
                && !TextUtils.isEmpty(tvEstado.getText()) && !TextUtils.isEmpty(tvCp.getText()) && !TextUtils.isEmpty(tvTelefono.getText())){
            btnGuardar.setEnabled(true);
        }else {
            btnGuardar.setEnabled(false);
        }
    }

    private JsonObject createRqts(){
        JsonObject object = new JsonObject();
        JsonObject shipping = new JsonObject();
        object.addProperty("first_name",tvNombre.getText().toString());
        object.addProperty("last_name",tvApellido.getText().toString());
        object.addProperty("address_1",tvDireccion.getText().toString());
        object.addProperty("address_2",tvDireccionDos.getText().toString());
        object.addProperty("city",tvCiudad.getText().toString());
        object.addProperty("postcode",tvCp.getText().toString());
        object.addProperty("country","MX");
        object.addProperty("state", tvEstado.getText().toString());
        shipping.add("shipping",object);

        return shipping;
    }

    @Override
    public void showDialog() {
        mDialog = new SweetAlertDialog(DatosEnvioActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        mDialog.getProgressHelper().setBarColor(Color.parseColor("#DF7C73"));
        mDialog.setTitleText("Cargando información...");
        mDialog.setCancelable(false);
        mDialog.show();

    }

    @Override
    public void hideDialog() {
       mDialog.dismiss();
    }

    @Override
    public void successUpdate() {
        mDialog = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE);
        //mDialog.getProgressHelper().setBarColor(Color.parseColor("#DF7C73"));
        mDialog.setTitleText("Información actualizada");
        mDialog.setConfirmButton("Aceptar", SweetAlertDialog::dismissWithAnimation);
        mDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sDialog) {
                guardarDatosLocales();
                mDialog.dismiss();
                mPresenter.registrerCustomerOpenPay(createRqtsCustomer());
            }
        });
        mDialog.setCancelable(false);
        mDialog.show();

    }

    private JsonObject createRqtsCustomer(){
        String user = mPreferences.getString(SavePreferenceInterface.DatosUsuario.EMAIL);
        String pass = mPreferences.getString(SavePreferenceInterface.DatosUsuario.PASSWORD);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("PHP_AUTH_USER",user);
        jsonObject.addProperty("PHP_AUTH_PW",pass);
        jsonObject.addProperty("first_name",tvNombre.getText().toString());
        jsonObject.addProperty("last_name ",tvApellido.getText().toString());
        jsonObject.addProperty("phone_number",tvTelefono.getText().toString());
        jsonObject.addProperty("line1",tvDireccion.getText().toString());
        jsonObject.addProperty("line2",tvDireccionDos.getText().toString());
        jsonObject.addProperty("state",tvEstado.getText().toString());
        jsonObject.addProperty("postal_code",tvCp.getText().toString());
        jsonObject.addProperty("city",tvCiudad.getText().toString());
        jsonObject.addProperty("country","MX");
        return  jsonObject;
    }

    private boolean validateCards(){
       if(!mPreferences.getString(SavePreferenceInterface.TarjetasBancarias.CARD_1).isEmpty() ||
              !mPreferences.getString(SavePreferenceInterface.TarjetasBancarias.CARD_2).isEmpty() ){
           return true;
       }else {
           // agregar tarjeta de
           return false;
       }
    }

    private void guardarDatosLocales(){
        mPreferences.putString(SavePreferenceInterface.DatosDireccion.ADDRESS_ONE,tvDireccion.getText().toString());
        mPreferences.putString(SavePreferenceInterface.DatosDireccion.ADDRESS_TWO,tvDireccionDos.getText().toString());
        mPreferences.putString(SavePreferenceInterface.Orden.PHONE,tvTelefono.getText().toString());
    }

    @Override
    public void errorUpdate() {
        mDialog = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE);
        //mDialog.getProgressHelper().setBarColor(Color.parseColor("#DF7C73"));
        mDialog.setTitleText("Error al actualizar");
        mDialog.setConfirmButton("Aceptar", SweetAlertDialog::dismissWithAnimation);
        mDialog.setCancelable(false);
        mDialog.show();

    }

    @Override
    public void setRegistrerCustomerOpenPay(ResponseRegistrerCustomerOpenPay response) {
        if(response.getData() != null){
            mPreferences.putString(SavePreferenceInterface.DatosUsuario.CUSTOMER_ID_OPEN_PAY,response.getData().getOpenpay_customer_id());
        }
        if(!validateCards()){
            startActivity( FormTarjetaActivity.createIntentASeleccionPago(DatosEnvioActivity.this,2));
        }else {
            // chips de tarjetas
            Intent mIntent = new Intent(DatosEnvioActivity.this,SeleccionFormaPagoActivity.class);
            startActivity(mIntent);
        }
    }
}
