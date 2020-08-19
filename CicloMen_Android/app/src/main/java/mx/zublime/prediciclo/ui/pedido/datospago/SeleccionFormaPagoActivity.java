package mx.zublime.prediciclo.ui.pedido.datospago;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import mx.zublime.prediciclo.R;
import mx.zublime.prediciclo.data.local.SavePreferenceInterface;
import mx.zublime.prediciclo.data.local.SavePreferenceManager;
import mx.zublime.prediciclo.data.models.TarjetaBancaria;
import mx.zublime.prediciclo.ui.autenticacion.AutenticacionActivity;
import mx.zublime.prediciclo.ui.home.sheets.BottomSheetAlertBorrar;
import mx.zublime.prediciclo.ui.home.sheets.BottomSheetConfirmationDialog;
import mx.zublime.prediciclo.ui.pedido.resumencompra.ResumenActivity;

public class SeleccionFormaPagoActivity extends AppCompatActivity {

    @BindView(R.id.btn_back_medio_pago)
    ImageButton btnBack;
    @BindView(R.id.chipGroup)
    ChipGroup chipGroupContentTarjetas;
    @BindView(R.id.btn_continuar_medio_pago)
    MaterialButton btnContinuar;
    @BindView(R.id.btn_add_card)
    FloatingActionButton btnAdd;
    @BindView(R.id.chip_tarjeta_1)
    Chip chipTarjeta1;
    @BindView(R.id.chip_tarjeta_2)
    Chip chipTarjeta2;
    private boolean isChekdTarjeta1 =  false;
    private boolean isChekdTarjeta2 =  false;


    private String tarjeta1;
    private String tarjeta2;
    private static final int REQUEST_CODE_ADD_CARD = 200;
    private SavePreferenceManager mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_forma_pago);
        ButterKnife.bind(this);
        initViews();

    }

    private void initViews(){
        mPreferences = new SavePreferenceManager(this);
        dataSetup();
        llenarListChips();
        validarBotonAdd();
        listenersRemoveChips();
        listenerSelectedChips();
    }

    private void listenerSelectedChips(){
        chipTarjeta1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mPreferences.putInt(SavePreferenceInterface.Orden.TARJETA_SELECCIONADA,1);
                if(!isChekdTarjeta1) {
                    btnContinuar.setEnabled(true);
                    mPreferences.putInt(SavePreferenceInterface.TarjetasBancarias.TARJETA_SELECCIONADA,1);
                    isChekdTarjeta1 = true;
                }else {
                    btnContinuar.setEnabled(false);
                    isChekdTarjeta1 = false;
                }

                if(isChekdTarjeta2) {
                    btnContinuar.setEnabled(true);
                }

            }
        });

        chipTarjeta2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mPreferences.putInt(SavePreferenceInterface.Orden.TARJETA_SELECCIONADA,2);
                if(!isChekdTarjeta2){
                    btnContinuar.setEnabled(true);
                    mPreferences.putInt(SavePreferenceInterface.TarjetasBancarias.TARJETA_SELECCIONADA,2);
                    isChekdTarjeta2 = true;
                }else {
                    btnContinuar.setEnabled(false);
                    isChekdTarjeta2 = false;
                }

                if(isChekdTarjeta1) {
                    btnContinuar.setEnabled(true);
                }
            }
        });
    }

    private void dataSetup()
    {
         isChekdTarjeta1 =  false;
         isChekdTarjeta2 =  false;
        tarjeta1 = mPreferences.getString(SavePreferenceInterface.TarjetasBancarias.CARD_1);
        tarjeta2 =  mPreferences.getString(SavePreferenceInterface.TarjetasBancarias.CARD_2);

    }

    private void  llenarListChips(){
        if(!tarjeta1.isEmpty()){
            chipTarjeta1.setVisibility(View.VISIBLE);
            seleccionarTarjeta(1);
        }
        if(!tarjeta2.isEmpty() ) {
            chipTarjeta2.setVisibility(View.VISIBLE);
            seleccionarTarjeta(2);
        }

    }
    private void validarBotonAdd(){
        if(!tarjeta1.isEmpty() && !tarjeta2.isEmpty()){
            btnAdd.setVisibility(View.GONE);

        }else {
            btnAdd.setVisibility(View.VISIBLE);

        }
    }

    private void listenersRemoveChips(){

        chipTarjeta1.setOnCloseIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SweetAlertDialog dialog = new SweetAlertDialog(SeleccionFormaPagoActivity.this,  SweetAlertDialog.WARNING_TYPE);
                dialog.setContentText("¿Estás segura que deseas eliminar esta tarjeta?");
                dialog.setCancelable(false);
                dialog.setConfirmButton("Si",(sweet) -> {
                    sweet.dismissWithAnimation();

                    chipTarjeta1.setSelected(false);
                    chipTarjeta1.setChecked(false);
                    isChekdTarjeta1 = false;

                    //chipGroupContentTarjetas.removeView(chipTarjeta1);
                    chipTarjeta1.setVisibility(View.GONE);
                    mPreferences.putString(SavePreferenceInterface.TarjetasBancarias.CARD_1,"");
                    dataSetup();
                    validarBotonAdd();

                });
                dialog.setCancelButton("No", SweetAlertDialog::dismissWithAnimation);
                dialog.show();


            }
        });

        chipTarjeta2.setOnCloseIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SweetAlertDialog dialog = new SweetAlertDialog(SeleccionFormaPagoActivity.this,  SweetAlertDialog.WARNING_TYPE);
                dialog.setContentText("¿Estás segura que deseas eliminar esta tarjeta?");
                dialog.setCancelable(false);
                dialog.setConfirmButton("Si",(sweet) -> {
                    sweet.dismissWithAnimation();

                    chipTarjeta2.setSelected(false);
                    chipTarjeta2.setChecked(false);
                    isChekdTarjeta2 = false;

                    // chipGroupContentTarjetas.removeView(chipTarjeta2);
                    chipTarjeta2.setVisibility(View.GONE);
                    mPreferences.putString(SavePreferenceInterface.TarjetasBancarias.CARD_2,"");
                    dataSetup();
                    validarBotonAdd();

                });
                dialog.setCancelButton("No", SweetAlertDialog::dismissWithAnimation);
                dialog.show();



            }
        });
    }

    private void seleccionarTarjeta(int position){
        String jsonString = "";
        TarjetaBancaria objetoTarjeta = new TarjetaBancaria();
        Gson gson = new Gson();

        if(position == 1) {
            jsonString = mPreferences.getString(SavePreferenceInterface.TarjetasBancarias.CARD_1);
            objetoTarjeta = gson.fromJson(jsonString,TarjetaBancaria.class);
            chipTarjeta1.setText(objetoTarjeta.getNumeroTarjetaConMascara());
        }else {
            jsonString = mPreferences.getString(SavePreferenceInterface.TarjetasBancarias.CARD_2);
            objetoTarjeta = gson.fromJson(jsonString,TarjetaBancaria.class);
            chipTarjeta2.setText(objetoTarjeta.getNumeroTarjetaConMascara());
        }


    }

    @OnClick(R.id.btn_continuar_medio_pago)
    public void  continuarCompra(){
        Intent mInten = new Intent(SeleccionFormaPagoActivity.this, ResumenActivity.class);
        startActivity(mInten);
    }
    @OnClick(R.id.btn_add_card)
    public void agregarTarjeta(){


            startActivityForResult(FormTarjetaActivity.createIntentASeleccionPago(SeleccionFormaPagoActivity.this,
                    1),REQUEST_CODE_ADD_CARD);

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        dataSetup();
        llenarListChips();
        validarBotonAdd();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_ADD_CARD){
            if(resultCode == Activity.RESULT_OK){
                dataSetup();
                validarBotonAdd();
                llenarListChips();
            }
        }
    }

    @OnClick(R.id.btn_back_medio_pago)
    public void  regresar(){
        finish();
    }
}
