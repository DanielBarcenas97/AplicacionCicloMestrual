package mx.zublime.prediciclo.ui.more;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.pedant.SweetAlert.SweetAlertDialog;
import mx.zublime.prediciclo.R;
import mx.zublime.prediciclo.base.BaseActivity;
import mx.zublime.prediciclo.data.local.SavePreferenceInterface;
import mx.zublime.prediciclo.data.local.SavePreferenceManager;
import mx.zublime.prediciclo.ui.MainActivity;
import mx.zublime.prediciclo.ui.autenticacion.AutenticacionActivity;
import mx.zublime.prediciclo.ui.home.interfaz.ISelectedButtonCommunication;
import mx.zublime.prediciclo.ui.home.sheets.BottomSheetAlertBorrar;
import mx.zublime.prediciclo.ui.perfil.PerfilActivity;
import mx.zublime.prediciclo.ui.tienda.TiendaFragment;
import mx.zublime.prediciclo.util.NavigatorUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoreFragment extends Fragment implements View.OnClickListener, MoreContract.MoreViewContract, ISelectedButtonCommunication
{


     @BindView(R.id.btn_edit)
     FloatingActionButton btnEdit;
     @BindView(R.id.tv_alias)
    TextView textViewAlias;
    @BindView(R.id.tv_email)
    TextView textEmail;
    @BindView(R.id.btn_exit)
    MaterialButton btnCerrarSesion;
    @BindView(R.id.btn_comprar)
    MaterialButton btnComprar;
    @BindView(R.id.btn_limpiar)
    MaterialButton btnLimpiar;
    @BindView(R.id.btnTerminos)
    MaterialButton btnTerminos;
    @BindView(R.id.btnPoliticas)
    MaterialButton btnPoliticas;
    @BindView(R.id.btnHelp)
    MaterialButton btnHelp;

    private Context mContext;
    private View mView;
    private Unbinder mUnbinder;
    private SavePreferenceManager mPreferences;
    private MorePresenter mPresenter;
    private SweetAlertDialog mDialog;

    private static final String URL_TERMINOS = "https://prediciclo.zublime.mx/terminos-y-condiciones/";
    private static final String URL_POLITICA = "https://prediciclo.zublime.mx/politica-privacidad/";
    private static final String URL_HELP = "https://prediciclo.zublime.mx/centro-de-ayuda/";

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity)
        {
            mContext = context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_more, container, false);
        mUnbinder = ButterKnife.bind(this,mView);
        mPresenter = new MorePresenter(this);

        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    private void initViews(){
        mPreferences = new SavePreferenceManager(mContext);
       // textViewAlias.setText(!(mPreferences.getString(SavePreferenceInterface.DatosUsuario.USER_APODO)).isEmpty() ? mPreferences.getString(SavePreferenceInterface.DatosUsuario.USER_APODO) : "Prediciclo" );
        if(mPreferences.getString(SavePreferenceInterface.DatosUsuario.USER_APODO).isEmpty()){
            textViewAlias.setText("Usuaria");
        }else {
            textViewAlias.setText(mPreferences.getString(SavePreferenceInterface.DatosUsuario.USER_APODO));
        }
        textEmail.setText((mPreferences.getString(SavePreferenceInterface.DatosUsuario.EMAIL)) != null ? mPreferences.getString(SavePreferenceInterface.DatosUsuario.EMAIL) : "");

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(getActivity(), PerfilActivity.class);
                getActivity().startActivity(mIntent);
            }
        });

        btnCerrarSesion.setOnClickListener(this);
        btnComprar.setOnClickListener(this);
        btnLimpiar.setOnClickListener(this);
        btnTerminos.setOnClickListener(this);
        btnPoliticas.setOnClickListener(this);
        btnHelp.setOnClickListener(this);
    }


    @Override
    public void onDestroyView() {
        if(mUnbinder != null){
            mUnbinder = null;
        }
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
        textViewAlias.setText((mPreferences.getString(SavePreferenceInterface.DatosUsuario.USER_APODO)) != null ? mPreferences.getString(SavePreferenceInterface.DatosUsuario.USER_APODO): "Usuaria" );
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_exit:
                cerrarSesion();
                break;

            case R.id.btn_comprar:

                ((MainActivity)getActivity()).mBottomNavigation.setSelectedItemId(R.id.comprar_item);

                MainActivity.mActive = MainActivity.mStoreFragment;

                getFragmentManager().beginTransaction().hide(this)
                    .show(MainActivity.mActive).commit();

                break;

            case R.id.btn_limpiar:
                BottomSheetAlertBorrar alert = new BottomSheetAlertBorrar(this);
                alert.show(getFragmentManager(),"CLEAR");
                break;

            case R.id.btnTerminos:
                navigateToWebView(URL_TERMINOS);
                break;

            case R.id.btnPoliticas:
                navigateToWebView(URL_POLITICA);
                break;
            case R.id.btnHelp:
                navigateToWebView(URL_HELP);
                break;
        }
    }

    private void navigateToWebView(String url){
      Intent intent =  WebViewActivity.createIntent(getActivity(),url);
      startActivity(intent);
    }

    private void cerrarSesion()
    {
        SweetAlertDialog dialog = new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE);
        dialog.setContentText("¿Estás segura que deseas cerrar tu sesión?");
        dialog.setCancelable(false);
        dialog.setConfirmButton("Si",(sweet) -> {
            sweet.dismissWithAnimation();
            mPreferences.removeAllPreferences();
            Intent mIntent = new Intent(mContext, AutenticacionActivity.class);
            getActivity().startActivity(mIntent);
            getActivity().finish();
        });
        dialog.setCancelButton("No", SweetAlertDialog::dismissWithAnimation);
        dialog.show();
    }

    @Override
    public void closeActivity()
    {
        mDialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
        mDialog.setCancelable(false);
        mDialog.setContentText("Acceso no autorizado, cerrando sesión...");
        mDialog.show();
        new Handler().postDelayed(() -> {
            mDialog.dismissWithAnimation();
            ((BaseActivity)getActivity()).closeActivity();
        }, 3000);
        mPreferences.putBoolean(SavePreferenceInterface.Autentication.IS_LOGIN,false);
    }

    @Override
    public void showDialog(String message)
    {

        mDialog = new SweetAlertDialog(mContext,SweetAlertDialog.PROGRESS_TYPE);
        mDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorPrimary));
        mDialog.setContentText(message);
        mDialog.setCancelable(false);
        mDialog.show();
    }

    @Override
    public void hideDialog()
    {
        mDialog.dismissWithAnimation();
    }

    @Override
    public void onResultFragmentClicked(boolean result)
    {
        if (result)
        {
            String user = mPreferences.getString(SavePreferenceInterface.DatosUsuario.EMAIL);
            String password = mPreferences.getString(SavePreferenceInterface.DatosUsuario.PASSWORD);
            mPresenter.clearCalendar(user,password);
        }
    }

    @Override
    public void onResultSaveDate(String date) {

    }

    @Override
    public void onResultSaveDateAndClear(String date)
    {

    }

    @Override
    public void onResultSavePrueba(boolean isPositive) {

    }

    @Override
    public void onResultReniciarMenstruacion(boolean result) {

    }
}
