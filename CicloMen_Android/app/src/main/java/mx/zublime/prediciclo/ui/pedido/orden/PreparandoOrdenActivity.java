package mx.zublime.prediciclo.ui.pedido.orden;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.google.api.client.json.Json;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mx.openpay.android.Openpay;
import mx.zublime.prediciclo.Prediciclo;
import mx.zublime.prediciclo.R;
import mx.zublime.prediciclo.data.local.SavePreferenceInterface;
import mx.zublime.prediciclo.data.local.SavePreferenceManager;
import mx.zublime.prediciclo.data.models.TarjetaBancaria;
import mx.zublime.prediciclo.ui.MainActivity;
import mx.zublime.prediciclo.ui.pedido.datospago.mvpopenpay.OpenPayContract;
import mx.zublime.prediciclo.ui.pedido.datospago.mvpopenpay.OpenPayPresenter;
import mx.zublime.prediciclo.ui.pedido.ordenmvp.OrderContract;
import mx.zublime.prediciclo.ui.pedido.ordenmvp.OrderPresenter;

public class PreparandoOrdenActivity extends AppCompatActivity implements OpenPayContract.OpenPayView , OrderContract.OrderView {

    @BindView(R.id.linear_content_status)
    LinearLayout linearLayoutContentEstatus;
    @BindView(R.id.message_content)
    LinearLayout linearLayoutProcesando;
    @BindView(R.id.tv_text_status)
    TextView tvEstatus;
    @BindView(R.id.lottie_status)
    LottieAnimationView lottieEstatus;
    private SavePreferenceManager mPreferences;
    private OpenPayPresenter mPresenterOpenPay;
    private OrderPresenter mPresenterOrder;
    private int idOrder ;
    private boolean successfull = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preparando_orden);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews(){
        mPreferences = new  SavePreferenceManager(this);
        mPresenterOpenPay = new OpenPayPresenter(this);
        mPresenterOrder = new OrderPresenter(this);
        linearLayoutProcesando.setVisibility(View.VISIBLE);
        mPresenterOrder.onCreateOrder(createRqts());
    }

    @OnClick(R.id.btn_aceptar)
    public void aceptar() {

        Intent mIntent = new Intent(PreparandoOrdenActivity.this, MainActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        if(successfull) {

            /* getSupportFragmentManager().beginTransaction()
                    .hide(MainActivity.mStoreFragment)
                    .show(MainActivity.mActive).commit();*/

            mIntent.putExtra("TAG", 1);

        } else {

            mIntent.putExtra("TAG", 2);

        }

        startActivity(mIntent);
        finish();

    }

    private JsonObject createRqts() {

        int userId = mPreferences.getInt(SavePreferenceInterface.DatosUsuario.USER_ID);
        String fisrtName = mPreferences.getString(SavePreferenceInterface.DatosDireccion.FIRST_NAME);
        String lastName = mPreferences.getString(SavePreferenceInterface.DatosDireccion.LAST_NAME);
        String address1 = mPreferences.getString(SavePreferenceInterface.DatosDireccion.ADDRESS_ONE);
        String address2 = mPreferences.getString(SavePreferenceInterface.DatosDireccion.ADDRESS_TWO);
        String city = mPreferences.getString(SavePreferenceInterface.DatosDireccion.CITY);
        String  state = mPreferences.getString(SavePreferenceInterface.DatosDireccion.STATE);
        String cp = mPreferences.getString(SavePreferenceInterface.DatosDireccion.POST_CODE);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("customer_id",userId);
        jsonObject.addProperty("payment_method","bacs");
        jsonObject.addProperty("payment_method_title","Direct Bank Transfe");
        jsonObject.addProperty("set_paid",false);

        JsonObject jsonShipping = new JsonObject();

         jsonShipping.addProperty("first_name",fisrtName);
        jsonShipping.addProperty("last_name",lastName);
        jsonShipping.addProperty("address_1",address1);
        jsonShipping.addProperty("address_2",address2);
        jsonShipping.addProperty("city",city);
        jsonShipping.addProperty("state",state);
        jsonShipping.addProperty("postcode",cp);
        jsonShipping.addProperty("country","MX");
        jsonObject.add("shipping",jsonShipping);

        JsonArray jsonArrayItems = new JsonArray();
        JsonObject objLines = new JsonObject();
        objLines.addProperty("product_id",520);
        objLines.addProperty("quantity",1);
        jsonArrayItems.add(objLines);
        jsonObject.add("line_items",jsonArrayItems);

        JsonArray jsonArrayShippingsLine = new JsonArray();
        JsonObject jsonShippingLines =  new JsonObject();
        jsonShippingLines.addProperty("method_id","flat_rate");
        jsonShippingLines.addProperty("method_title","Envío gratuito");
        jsonShippingLines.addProperty("total","0");
        jsonArrayShippingsLine.add(jsonShippingLines);
        jsonObject.add("shipping_lines",jsonArrayShippingsLine);

        Log.w("json",jsonObject.toString());

        return jsonObject;

    }


    @Override
    public void successPay(String source_id) {
        String session = Prediciclo.getOpenPay().getDeviceCollectorDefaultImpl().setup(this);
        String user = mPreferences.getString(SavePreferenceInterface.DatosUsuario.EMAIL);
        String password = mPreferences.getString(SavePreferenceInterface.DatosUsuario.PASSWORD);
        mPresenterOpenPay.generarCargo(user,password,source_id,session,idOrder);
    }

    private void actualizarOrden()
    {
        //int id = mPreferences.getInt(SavePreferenceInterface.DatosUsuario.USER_ID);
        JsonObject obj = new JsonObject();
        obj.addProperty("set_paid",true);
        mPresenterOrder.onUpdateOrder(String.valueOf(idOrder),obj);
    }

    @Override
    public void errorPay() {
        showErrorAnimation();
    }

    @Override
    public void onSuccessCharge() {
        actualizarOrden();
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void hideDialog() {

    }

    @Override
    public void onSuccessCreate(int idOr) {
        idOrder = idOr;
        pagarPedidoConOpenPay();
    }
    private void pagarPedidoConOpenPay(){
        String tarjeta = "";
        if(mPreferences.getInt(SavePreferenceInterface.TarjetasBancarias.TARJETA_SELECCIONADA) == 1){
            tarjeta = mPreferences.getString(SavePreferenceInterface.TarjetasBancarias.CARD_1);
        }else {
            tarjeta = mPreferences.getString(SavePreferenceInterface.TarjetasBancarias.CARD_2);
        }
        Gson gson = new Gson();
        TarjetaBancaria objTarjeta = new TarjetaBancaria();
        objTarjeta = gson.fromJson(tarjeta,TarjetaBancaria.class);
        String date = objTarjeta.getFechaVencimiento();
        int month = 0;
        int year = 0;
        if(date != null){
             month = Integer.valueOf(date.substring(0,2));
             year = Integer.valueOf(date.substring(3,5));

        }
        mPresenterOpenPay.generatePayment(objTarjeta.getNombreTitular(),objTarjeta.getNumeroTarjeta(),month,year,objTarjeta.getCcv());

    }

    @Override
    public void onSuccessUpdate() {
        linearLayoutProcesando.setVisibility(View.GONE);
        linearLayoutContentEstatus.setVisibility(View.VISIBLE);
        tvEstatus.setText(getString(R.string.successful));
        lottieEstatus.setAnimation(R.raw.successful);
        lottieEstatus.setRepeatMode(LottieDrawable.REVERSE);
        successfull = true;
    }

    @Override
    public void onErrorCreate() {
        showErrorAnimation();
    }

    @Override
    public void onErrorUpdate() {
       showErrorAnimation();
    }

    @Override
    public void showError() {
        linearLayoutProcesando.setVisibility(View.GONE);
        linearLayoutContentEstatus.setVisibility(View.VISIBLE);
        lottieEstatus.setAnimation(R.raw.error);
        lottieEstatus.setRepeatMode(LottieDrawable.REVERSE);
        tvEstatus.setText(getString(R.string.error_general));
        successfull = false;
    }
    @Override
    public void onErrorCharge() {
      showErrorAnimation();
    }

    private void showErrorAnimation(){
        linearLayoutProcesando.setVisibility(View.GONE);
        linearLayoutContentEstatus.setVisibility(View.VISIBLE);
        lottieEstatus.setAnimation(R.raw.error);
        lottieEstatus.setRepeatMode(LottieDrawable.REVERSE);
        tvEstatus.setText(getString(R.string.error));
        successfull = false;
    }
}



