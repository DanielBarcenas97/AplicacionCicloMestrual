package mx.zublime.prediciclo.ui.tienda;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.pedant.SweetAlert.SweetAlertDialog;
import mx.zublime.prediciclo.R;
import mx.zublime.prediciclo.data.local.SavePreferenceInterface;
import mx.zublime.prediciclo.data.local.SavePreferenceManager;
import mx.zublime.prediciclo.data.models.Direccion;
import mx.zublime.prediciclo.ui.pedido.DatosParaPedidoActivity;
import mx.zublime.prediciclo.ui.pedido.datosenvio.DatosEnvioActivity;
import mx.zublime.prediciclo.ui.tienda.mvpretrieveacustomer.RetriveCustomerContract;
import mx.zublime.prediciclo.ui.tienda.mvpretrieveacustomer.RetriveCustomerPresenter;
import mx.zublime.prediciclo.ui.tienda.mvptienda.TiendaContract;
import mx.zublime.prediciclo.ui.tienda.mvptienda.TiendaPresenter;
import mx.zublime.prediciclo.util.NavigatorUtils;


public class TiendaFragment extends Fragment implements TiendaContract.tiendaView, RetriveCustomerContract.RetriveCustomerView {


    @BindView(R.id.img_product)  ImageView imageViewProduct;
    @BindView(R.id.tv_product_name) TextView tvName;
    @BindView(R.id.tv_description) TextView tvDescription;
    @BindView(R.id.tv_price) TextView tvPrice;
    @BindView(R.id.btn_mas) MaterialButton btnMas;
    @BindView(R.id.tv_price_regular) TextView tvPriceRegular;
    @BindView(R.id.content_construccion) ConstraintLayout contentConstruccion;
    @BindView(R.id.content_producto) ConstraintLayout contentProducto;
    @BindView(R.id.btn_guardar)
    MaterialButton btnComprar;

    private Context mContex;
    private Unbinder mUnbinder;
    private SweetAlertDialog mDialog;
    private TiendaPresenter mPresenter;
    private String descriptionLong;
    private String descriptionShort;
    private boolean isColaps = false;
    private RetriveCustomerPresenter mPresenterRetrive;
    private String idCliente;
    private SavePreferenceManager mPreferences;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof Activity ){
            this.mContex = context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_tienda, container, false);
        mUnbinder = ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    private void  initViews(){
        mPresenter = new TiendaPresenter(this);
        mPresenterRetrive = new RetriveCustomerPresenter(this);
        mPreferences = new SavePreferenceManager(mContex);
        mPresenter.onGetProduct();
        //idCliente = mPreferences.getString(SavePreferenceInterface.DatosUsuario.USER_ID);

    }

    @OnClick(R.id.btn_mas)
    public void showDescripcion(){
        if(isColaps){
            isColaps = false;
            tvDescription.setText(Html.fromHtml(descriptionShort));
            btnMas.setText("Ver mas");
        }else {
            isColaps = true;
            tvDescription.setText(Html.fromHtml(descriptionLong));
            btnMas.setText("Mostrar menos");
        }
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden)
           mPresenter.onGetProduct();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @OnClick(R.id.btn_guardar)
    public void productPay(){
        String id = String.valueOf( mPreferences.getInt(SavePreferenceInterface.DatosUsuario.USER_ID));
        mPresenterRetrive.onRetriveCustomer(id);
    }


    private void showDialogGeneral(String message){
        mDialog = new SweetAlertDialog(mContex, SweetAlertDialog.PROGRESS_TYPE);
        mDialog.getProgressHelper().setBarColor(Color.parseColor("#DF7C73"));
        mDialog.setTitleText(message);
        mDialog.setCancelable(false);
        mDialog.show();
    }

    private void hideDialogGeneral(){
        mDialog.dismiss();
    }

    @Override
    public void showDialog() {
       showDialogGeneral("Cargando...");

    }

    @Override
    public void hideDialog() {
       hideDialogGeneral();
    }

    @Override
    public void showProductNormal(String name, String descripction, String shortDescription, String price, String linkImage) {
        llenarDatos(name,descripction,shortDescription,price,"",linkImage);
    }

    @Override
    public void showProductOfert(String name, String descripction, String shortDescription, String price, String regularPrice, String linkImage) {
        llenarDatos(name,descripction,shortDescription,price,regularPrice,linkImage);

    }

    @Override
    public void showProductoNotStock(String name, String descripction, String shortDescription, String price, String regularPrice, String linkImage) {
        llenarDatos(name,descripction,shortDescription,price,regularPrice,linkImage);
        btnMas.setEnabled(false);
    }

    @Override
    public void showConstructionScreen() {
        contentProducto.setVisibility(View.GONE);
        contentConstruccion.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showDialogRetrive() {
        showDialogGeneral("Cargando información...");
    }

    @Override
    public void hideAlertRetrive() {
        hideDialogGeneral();
    }

    @Override
    public void saveInfoCustomer(String first_name, String last_name, String company, String address_1, String address_2, String city, String postcode, String country, String state, boolean infoCompleta) {
        mPreferences.putString(SavePreferenceInterface.DatosDireccion.FIRST_NAME,first_name);
        mPreferences.putString(SavePreferenceInterface.DatosDireccion.LAST_NAME,last_name);
        mPreferences.putString(SavePreferenceInterface.DatosDireccion.COMPANY,company);
        mPreferences.putString(SavePreferenceInterface.DatosDireccion.ADDRESS_ONE,address_1);
        mPreferences.putString(SavePreferenceInterface.DatosDireccion.ADDRESS_TWO,address_2);
        mPreferences.putString(SavePreferenceInterface.DatosDireccion.CITY,city);
        mPreferences.putString(SavePreferenceInterface.DatosDireccion.POST_CODE,postcode);
        mPreferences.putString(SavePreferenceInterface.DatosDireccion.CONTRY,country);
        mPreferences.putString(SavePreferenceInterface.DatosDireccion.STATE,state);
        validateInfoCustomer(infoCompleta);
    }

    private void  validateInfoCustomer(boolean infoCompleta){

      if(infoCompleta){
          Intent mIntent = new Intent(getActivity(), DatosEnvioActivity.class);
          NavigatorUtils.navigateTo(mContex,mIntent);
          /*Intent mIntent = new Intent(getActivity(),DatosParaPedidoActivity.class);
          NavigatorUtils.navigateTo(mContex,mIntent);*/
      }else {
          Intent mIntent = new Intent(getActivity(), DatosEnvioActivity.class);
          NavigatorUtils.navigateTo(mContex,mIntent);
      }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    private void llenarDatos(String name, String descripction, String shortDescription, String price, String regularPrice, String linkImage) {

        contentProducto.setVisibility(View.VISIBLE);
        loadProductImage(linkImage);

        descriptionShort = shortDescription;
        descriptionLong = descripction;
        tvName.setText(name);
        mPreferences.putString(SavePreferenceInterface.Product.NAME_PRODUCT,name);
        tvDescription.setText(Html.fromHtml(shortDescription));

        if(!regularPrice.equals("")) {
            tvPriceRegular.setVisibility(View.VISIBLE);
            tvPriceRegular.setText("$" + regularPrice);
            tvPriceRegular.setPaintFlags(tvPriceRegular.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            mPreferences.putString(SavePreferenceInterface.Product.PRECIO,regularPrice);
        }else {
            mPreferences.putString(SavePreferenceInterface.Product.PRECIO,price);
        }

        tvPrice.setText("$"  + price);
        mPreferences.putString(SavePreferenceInterface.Product.PRECIO,price);

    }

    private void loadProductImage(String url) {
                Glide
                .with(mContex)
                .load(url)
                .centerCrop()
                .into(imageViewProduct);
    }

    private String replaceTextProduct(String text) {
        String textFormat = text.replaceAll("\\(", "").replaceAll("\\)","");
        return textFormat.replaceAll("titulo del producto","");
    }

    private String replaceTextHtml(String text) {
        text = text.replaceAll("\\<.*?\\>", "");
        text = text.replaceAll("\\n]","");
        return text;
    }


}
