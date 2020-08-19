package mx.zublime.prediciclo.ui.pedido;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mx.zublime.prediciclo.R;
import mx.zublime.prediciclo.data.local.SavePreferenceInterface;
import mx.zublime.prediciclo.data.local.SavePreferenceManager;
import mx.zublime.prediciclo.data.models.Shipping;
import mx.zublime.prediciclo.data.models.TarjetaBancaria;
import mx.zublime.prediciclo.ui.pedido.adapters.DireccionAdapter;
import mx.zublime.prediciclo.ui.pedido.adapters.EliminarDireccionListener;
import mx.zublime.prediciclo.ui.pedido.adapters.EliminarTarjetaListener;
import mx.zublime.prediciclo.ui.pedido.adapters.TarjetaBancariaAdapter;
import mx.zublime.prediciclo.ui.pedido.datosenvio.DatosEnvioActivity;
import mx.zublime.prediciclo.ui.pedido.datospago.FormTarjetaActivity;
import mx.zublime.prediciclo.util.NavigatorUtils;

public class DatosParaPedidoActivity extends AppCompatActivity implements EliminarDireccionListener, EliminarTarjetaListener {

    @BindView(R.id.recyclerViewTarjetas)
    RecyclerView recyclerViewTarjetas;
    @BindView(R.id.recyclerDirecciones)
    RecyclerView recyclerViewDirreciones;
    @BindView(R.id.btn_continuar)
    MaterialButton btnContinuar;
    @BindView(R.id.btn_agragar_tarjeta)
    TextView btnAgregarNuevaTarjeta;
    @BindView(R.id.btn_agragar_direccion)
    TextView agregarNuevaDireccion;
    private SavePreferenceManager mPreferences;
    private DireccionAdapter direccionAdapter;
    private TarjetaBancariaAdapter tarjetaBancariaAdapter;
    private static final  int REQUEST_ADD_DIRECCION = 1;
    private  static final  int REQUEST_ADD_TARJETA = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_para_pedido);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews(){
        setupData();
        setupRecyclerViewDireccions();
        setupRecyclerViewTarjetas();
        setupAdapter();
    }

    private void setupData(){
        mPreferences = new SavePreferenceManager(this);
        mPreferences.putString(SavePreferenceInterface.DatosDireccion.ADDRESS_ONE,"sss");
        mPreferences.putString(SavePreferenceInterface.DatosDireccion.ADDRESS_TWO,"sss");
        if(!mPreferences.getString(SavePreferenceInterface.DatosDireccion.ADDRESS_ONE).isEmpty() || !mPreferences.getString(SavePreferenceInterface.DatosDireccion.ADDRESS_TWO).isEmpty()){
            setupRecyclerViewDireccions();

        }else {
            agregarUnaDireccion();
        }
    }

    private void setupAdapter(){

        direccionAdapter = new DireccionAdapter(obtenerDatosPruebaDireccion(),this);
        recyclerViewDirreciones.setAdapter(direccionAdapter);
        tarjetaBancariaAdapter = new TarjetaBancariaAdapter(obtenerDatosPruebaTrajeta(),this);
        recyclerViewTarjetas.setAdapter(tarjetaBancariaAdapter);
    }

    private List<Shipping> obtenerDatosPruebaDireccion(){
        List<Shipping> lista = new ArrayList<>();
        JsonObject json = new JsonObject();
        json.addProperty("first_name","Edgar");
        json.addProperty("last_name","Morales");
        json.addProperty("company","Zublime");
        json.addProperty("address_1","Fuente bella de las ");
        json.addProperty("address_2","Penjamo gto");
        json.addProperty("city","Guanajauato city");
        json.addProperty("postcode","36901");
        json.addProperty("country","Pais");
        json.addProperty("state","Guanajuato");

        String jsonDireccion = json.toString();

        Gson gson  = new Gson();
        Shipping shipping = new Shipping();
        shipping = gson.fromJson(jsonDireccion,Shipping.class);
        lista.add(shipping);
        lista.add(shipping);
        return  lista;

    }

    private List<TarjetaBancaria> obtenerDatosPruebaTrajeta(){
        List<TarjetaBancaria> lista = new ArrayList<>();
        JsonObject json = new JsonObject();
        json.addProperty("nombreTitular","Edgar");
        json.addProperty("numeroTarjeta","4545121214141256");
        json.addProperty("ccv","Zublime");
        json.addProperty("fechaVencimiento","20/12/2019");


        String jsonDireccion = json.toString();

        Gson gson  = new Gson();
        TarjetaBancaria tarjetaBancaria = new TarjetaBancaria();
        tarjetaBancaria = gson.fromJson(jsonDireccion,TarjetaBancaria.class);
        lista.add(tarjetaBancaria);
        lista.add(tarjetaBancaria);
        lista.add(tarjetaBancaria);

        return  lista;

    }

    private void setupRecyclerViewTarjetas() {
        recyclerViewTarjetas.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DatosParaPedidoActivity.this,LinearLayoutManager.HORIZONTAL, false);
        recyclerViewTarjetas.setLayoutManager(linearLayoutManager);
    }

    private void setupRecyclerViewDireccions(){
        recyclerViewDirreciones.setHasFixedSize(true);
        recyclerViewDirreciones.setNestedScrollingEnabled(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DatosParaPedidoActivity.this,LinearLayoutManager.VERTICAL,false);
        recyclerViewDirreciones.setLayoutManager(linearLayoutManager);

    }
    private void agregarUnaDireccion(){
        Intent intent = new Intent(DatosParaPedidoActivity.this, DatosEnvioActivity.class);
        NavigatorUtils.navigateTo(DatosParaPedidoActivity.this,intent);
    }
    
    @OnClick(R.id.btn_continuar)
    public void continuarCompra(){
        Toast.makeText(this, "Compando...", Toast.LENGTH_SHORT).show();
    }
    @OnClick(R.id.btn_agragar_tarjeta)
    public void addTarjeta(){
        Intent intent = new Intent(DatosParaPedidoActivity.this, FormTarjetaActivity.class);
        startActivityForResult(intent,REQUEST_ADD_TARJETA);
    }
    @OnClick(R.id.btn_agragar_direccion)
    public void addDireccion(){
        Intent intent = new Intent(DatosParaPedidoActivity.this, DatosEnvioActivity.class);
        startActivityForResult(intent,REQUEST_ADD_DIRECCION);
    }

    @OnClick(R.id.btn_back)
    public void  regresar(){
        finish();
    }

    @Override
    public void eliminarDireccion(int position) {
        direccionAdapter.eliminarBeneficiario(position);
        direccionAdapter.notifyDataSetChanged();
        direccionAdapter.notifyItemRemoved(position);
    }

    @Override
    public void eliminarTarjeta(int position) {
        tarjetaBancariaAdapter.eliminarTarjeta(position);
        tarjetaBancariaAdapter.notifyDataSetChanged();
        tarjetaBancariaAdapter.notifyItemRemoved(position);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQUEST_ADD_DIRECCION:
                if(resultCode == Activity.RESULT_OK){
                    updateRecyclerViewDireccion();
                }
                break;

            case REQUEST_ADD_TARJETA:
                if(resultCode == Activity.RESULT_OK){
                    updateRecyclerViewTarjetas();
                }
                break;

        }
    }

    private void updateRecyclerViewDireccion(){

    }
    private void  updateRecyclerViewTarjetas(){

    }
}
