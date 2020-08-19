package mx.zublime.prediciclo.ui.pedido.resumencompra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import mx.zublime.prediciclo.R;
import mx.zublime.prediciclo.data.local.SavePreferenceInterface;
import mx.zublime.prediciclo.data.local.SavePreferenceManager;
import mx.zublime.prediciclo.data.models.TarjetaBancaria;
import mx.zublime.prediciclo.ui.MainActivity;
import mx.zublime.prediciclo.ui.pedido.datospago.SeleccionFormaPagoActivity;
import mx.zublime.prediciclo.ui.pedido.orden.PreparandoOrdenActivity;

public class ResumenActivity extends AppCompatActivity {

    @BindView(R.id.tv_precio)
    TextView tvPrecio;
    @BindView(R.id.tv_direccion)
    TextView tvDireccion;
    @BindView(R.id.tv_tarjeta)
    TextView tvTarjeta;
    @BindView(R.id.img_card)
    ImageView imageViewTarjeta;
    @BindView(R.id.tv_number_phone)
    TextView tvPhone;
    @BindView(R.id.tv_name_product)
    TextView tvNombreProducto;
    @BindView(R.id.btn_confirmar_compra)
    Button mConfirmar;
    String phone;
    private String precio;
    private String numTarjeta;
    private SavePreferenceManager mPreferences;
    private int numeroDeTarjeta;
    private static final int TARJETA_1 = 1;
    private static final int TARJETA_2 = 2;
    private int tipoTarjeta;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        String direccionCompuesta = "";

        mPreferences = new SavePreferenceManager(this);
        phone = mPreferences.getString(SavePreferenceInterface.Orden.PHONE);
        precio = mPreferences.getString(SavePreferenceInterface.Product.PRECIO);
        String cp = mPreferences.getString(SavePreferenceInterface.DatosDireccion.POST_CODE);
        String city = mPreferences.getString(SavePreferenceInterface.DatosDireccion.CITY);
        String state = mPreferences.getString(SavePreferenceInterface.DatosDireccion.STATE);
        String direccion = mPreferences.getString(SavePreferenceInterface.DatosDireccion.ADDRESS_ONE);
        String direccion2 = mPreferences.getString(SavePreferenceInterface.DatosDireccion.ADDRESS_TWO);

        direccionCompuesta = direccion + " " + direccion2 + " " + city + " " + state + " " + cp;

        tvNombreProducto.setText(mPreferences.getString(SavePreferenceInterface.Product.NAME_PRODUCT));
        tvDireccion.setText(direccionCompuesta);
        tvPrecio.setText( "$" + precio);
        tvPhone.setText(phone);
        numeroDeTarjeta = mPreferences.getInt(SavePreferenceInterface.TarjetasBancarias.TARJETA_SELECCIONADA);
        String datosTarjeta = "";
        if(numeroDeTarjeta == TARJETA_1) {
            tvTarjeta.setText(mPreferences.getString(SavePreferenceInterface.TarjetasBancarias.MASCARA_DE_NUMERO_1));
            datosTarjeta = mPreferences.getString(SavePreferenceInterface.TarjetasBancarias.CARD_1);
        }else {
            tvTarjeta.setText(mPreferences.getString(SavePreferenceInterface.TarjetasBancarias.MASCAR_DE_NUMERO_2));
            datosTarjeta = mPreferences.getString(SavePreferenceInterface.TarjetasBancarias.CARD_2);
        }

        Gson gson = new Gson();
        TarjetaBancaria objTarjeta = new TarjetaBancaria();
        objTarjeta = gson.fromJson(datosTarjeta,TarjetaBancaria.class);
        int tipo = objTarjeta.getTipoTarjeta();

        switch (tipo){
            case 0:
                imageViewTarjeta.setImageResource(R.drawable.ic_credit_card);
                break;
            case 3:
                imageViewTarjeta.setImageResource(R.drawable.ic_american_black);
                break;
            case 4:
                imageViewTarjeta.setImageResource(R.drawable.ic_visa_black);
                break;
            case 5:
                imageViewTarjeta.setImageResource(R.drawable.ic_mastercard_black);
                break;
        }
    }

    @OnClick(R.id.btn_back_resumen)
    public void regresar(){
        finish();
    }

    @OnClick(R.id.btn_confirmar_compra)
    public void  confirmarCompra() {
        mConfirmar.setEnabled(false);
        mConfirmar.postDelayed(() -> mConfirmar.setEnabled(true), 3000);
        Intent mIntent = new Intent(ResumenActivity.this, PreparandoOrdenActivity.class);
        startActivity(mIntent);
    }

    @OnClick(R.id.btn_cancelar)
    public void  cancelar(){

        SweetAlertDialog dialog = new SweetAlertDialog(ResumenActivity.this,  SweetAlertDialog.WARNING_TYPE);
        dialog.setContentText("¿Estás segura que cancelar la compra?");
        dialog.setCancelable(false);
        dialog.setConfirmButton("Si",(sweet) -> {
            sweet.dismissWithAnimation();
            Intent mIntent = new Intent(ResumenActivity.this, MainActivity.class);
            mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mIntent);
            finish();
        });

        dialog.setCancelButton("No", SweetAlertDialog::dismissWithAnimation);
        dialog.show();

    }
}
