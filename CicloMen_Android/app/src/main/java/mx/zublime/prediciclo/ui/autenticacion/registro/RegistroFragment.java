package mx.zublime.prediciclo.ui.autenticacion.registro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.pedant.SweetAlert.SweetAlertDialog;
import mx.zublime.prediciclo.R;
import mx.zublime.prediciclo.data.local.SavePreferenceInterface;
import mx.zublime.prediciclo.data.local.SavePreferenceManager;
import mx.zublime.prediciclo.ui.autenticacion.registro.registromvp.RegistroContract;
import mx.zublime.prediciclo.ui.autenticacion.registro.registromvp.RegistroPresenter;
import mx.zublime.prediciclo.ui.configuraciontur.ConfiguracionTurActivity;
import mx.zublime.prediciclo.util.Forms;
import mx.zublime.prediciclo.util.NavigatorUtils;

public class RegistroFragment extends Fragment implements RegistroContract.RegistroContractView
{
    @BindView(R.id.email_textinputlayout) TextInputLayout textInputLayoutEmail;
    @BindView(R.id.password_textinputlayout) TextInputLayout textInputLayoutPassword;
    @BindView(R.id.email_edittext) TextInputEditText editTextEmail;
    @BindView(R.id.password_edittext) TextInputEditText editTextPassword;
    @BindView(R.id.btn_registrar) MaterialButton buttonRegistrar;
    @BindView(R.id.tv_btn_registrar) MaterialTextView btnLogin;
    @BindView(R.id.contnet_registro) ScrollView contentPadre;

    private Context mContext;
    private SavePreferenceManager mPreferences;
    private View mView;
    private Unbinder mUnbinder;
    private SweetAlertDialog mDialog;
    private RegistroPresenter mPresenter;

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof Activity )
        {
            mContext = context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // asignación de valores, com parametros etc, que no sean compontes de vista
        mPreferences = new SavePreferenceManager(mContext);
        mPresenter = new RegistroPresenter(this);
    }

    // lanzamiento de la vista
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.fragment_registro, container, false);
        mUnbinder = ButterKnife.bind(this,mView);
        return mView;
    }

    // aseguramiento de la vista
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init()
    {
        setWatchers();
    }

    private void setWatchers()
    {
        TextWatcher correoWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                if (textInputLayoutEmail.isErrorEnabled())
                    textInputLayoutEmail.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        TextWatcher passwordWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (textInputLayoutPassword.isErrorEnabled())
                    textInputLayoutPassword.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        editTextPassword.addTextChangedListener(passwordWatcher);
        editTextEmail.addTextChangedListener(correoWatcher);
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
    }

    private boolean validarDatos() {
        String email =(editTextEmail.getText() != null) ? editTextEmail.getText().toString() : "";
        String password = (editTextPassword.getText() != null) ? editTextPassword.getText().toString() : "";
        boolean correo;
        boolean pass;
        Pattern pattern = Patterns.EMAIL_ADDRESS;

        if(pattern.matcher(email).matches()) {
            textInputLayoutEmail.setErrorEnabled(false);
            correo = true;
        } else {
            textInputLayoutEmail.setErrorEnabled(true);
            textInputLayoutEmail.setError(getResources().getString(R.string.email_invalido));
            correo = false;
        }

        if(password.isEmpty()) {
            pass = false;
            textInputLayoutPassword.setErrorEnabled(true);
            textInputLayoutPassword.setError(getResources().getString(R.string.campo_obligatorio));
        } else if(password.length() < 6) {
            pass = false;
            textInputLayoutPassword.setErrorEnabled(true);
            textInputLayoutPassword.setError(getResources().getString(R.string.pass_lengh_min));
        }  else {
            textInputLayoutPassword.setErrorEnabled(false);
            pass = true;
        }

        return correo && pass;
    }

    private void  siguintePantallaDeConfiguracion()
    {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        mPresenter.registrarUsuario(email,password);
    }

    private void guardarDatos(int id)
    {
      mPreferences.putString(SavePreferenceInterface.DatosUsuario.EMAIL,editTextEmail.getText().toString());
      mPreferences.putString(SavePreferenceInterface.DatosUsuario.PASSWORD,editTextPassword.getText().toString());
      mPreferences.putInt(SavePreferenceInterface.DatosUsuario.USER_ID,id);
    }

    private void regresarAtras(){
        getFragmentManager().popBackStack();
    }

    @Override
    public void onDestroyView()
    {
        if(mUnbinder != null)
        {
            mUnbinder = null;
            mPresenter.onDestroy();
        }
        super.onDestroyView();
    }

    @OnClick({R.id.btn_registrar,R.id.tv_btn_registrar})
    public void onClickListener(View view)
    {
        switch (view.getId())
        {
            case R.id.btn_registrar:
                if(validarDatos())
                {
                    siguintePantallaDeConfiguracion();
                }
                break;
            case R.id.tv_btn_registrar:
                regresarAtras();
                break;

        }
    }

    @Override
    public void showAlert(String message)
    {
        mDialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
        mDialog.getProgressHelper().setBarColor(Color.parseColor("#DF7C73"));
        mDialog.setTitleText(message);
        mDialog.setCancelable(false);
        mDialog.show();
    }

    @Override

    public void hideAlert()
    {
        mDialog.dismissWithAnimation();
    }

    @Override
    public void onSuccess(int id)
    {
        guardarDatos(id);
    }

    @Override
    public void onSuccessRegistro()
    {
        Intent intent = new Intent(mContext,ConfiguracionTurActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onFailure(String error)
    {
        mDialog = new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE);
        //mDialog.getProgressHelper().setBarColor(Color.parseColor("#DF7C73"));
        mDialog.setTitleText(error);
        mDialog.setConfirmButton("Aceptar", (dialog) ->{
            dialog.dismiss();
            getFragmentManager().popBackStack();
        });
        mDialog.setCancelable(false);
        mDialog.show();
    }
}
