package mx.zublime.prediciclo.ui.autenticacion.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.pedant.SweetAlert.SweetAlertDialog;
import mx.zublime.prediciclo.R;
import mx.zublime.prediciclo.data.local.SavePreferenceInterface;
import mx.zublime.prediciclo.data.local.SavePreferenceManager;
import mx.zublime.prediciclo.data.models.ResponseUser;
import mx.zublime.prediciclo.ui.MainActivity;
import mx.zublime.prediciclo.ui.autenticacion.login.mvpLogin.LoginContract;
import mx.zublime.prediciclo.ui.autenticacion.login.mvpLogin.LoginPresenter;
import mx.zublime.prediciclo.ui.autenticacion.registro.RegistroFragment;
import mx.zublime.prediciclo.ui.configuraciontur.ConfiguracionTurActivity;
import mx.zublime.prediciclo.util.Forms;
import mx.zublime.prediciclo.util.NavigatorUtils;

public class LoginFragment extends Fragment implements LoginContract.LoginContracView, View.OnClickListener {
    @BindView(R.id.email_edittext)
    TextInputEditText editTextEmail;
    @BindView(R.id.email_textinputlayout)
    TextInputLayout textInputLayoutEmail;
    @BindView(R.id.password_textinputlayout)
    TextInputLayout textInputLayoutPassword;
    @BindView(R.id.password_edittext)
    TextInputEditText editTextPassword;
    @BindView(R.id.iniciar_sesion_button)
    MaterialButton buttonSiguiente;
    @BindView(R.id.tv_btn_registrar)
    MaterialTextView btnRegistrar;
    @BindView(R.id.content_login)
    ScrollView contenedorPadre;
    @BindView(R.id.tv_forgon_password)
    MaterialTextView mOlvidarPassword;

    private Context mContext;
    private LoginPresenter mPrsenter;
    private Unbinder mUnbinder;
    private View view;
    private static final String TAG_FRAGMENT_REGISTRO = "fragment_registro";
    private SavePreferenceManager mPreferences;
    private SweetAlertDialog mDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            mContext = context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPrsenter = new LoginPresenter(this);
        mPreferences = new SavePreferenceManager(mContext);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        btnRegistrar.setOnClickListener(this);
        buttonSiguiente.setOnClickListener(this);
        contenedorPadre.setOnClickListener(this);
        setWatchers();
    }

    private void setWatchers() {
        TextWatcher correoWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iniciar_sesion_button:
                iniciarSesion();
                break;

            case R.id.tv_btn_registrar:
                registrarUsuario();
                //Intent i = new Intent(mContext, ConfiguracionTurActivity.class);
                //startActivity(i);
                break;
        }

    }

    private void iniciarSesion() {
        String emailUser = (editTextEmail.getText() != null) ? editTextEmail.getText().toString() : "";
        textInputLayoutEmail.setErrorEnabled(false);
        if (textInputLayoutPassword.getVisibility() == View.GONE) {
            if (validarCorreo(emailUser)) {
                textInputLayoutEmail.setErrorEnabled(false);
                Forms.bloquerFormulariosOneButton(addInputs(), buttonSiguiente);
                mPrsenter.autenticarUsuarioStepOne(emailUser);
            } else {
                textInputLayoutEmail.setErrorEnabled(true);
                textInputLayoutEmail.setError("Correo no válido");
            }
        } else {
            String password = (editTextPassword.getText() != null) ? editTextPassword.getText().toString() : "";
            if (!password.isEmpty()) {
                textInputLayoutPassword.setErrorEnabled(false);
                Forms.bloquerFormulariosOneButton(addInputs(), buttonSiguiente);
                mPrsenter.autenticarUsuarioStepTwo(emailUser, password);
            } else {
                textInputLayoutPassword.setErrorEnabled(true);
                textInputLayoutPassword.setError("Campo obligatorio *");
            }
        }
    }

    private List<TextInputEditText> addInputs() {
        List<TextInputEditText> listInputs = new ArrayList<>();
        listInputs.add(editTextEmail);
        listInputs.add(editTextEmail);
        return listInputs;
    }

    private boolean validarCorreo(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    private void registrarUsuario() {
        NavigatorUtils.replaceFragmentWithAnimation(new RegistroFragment(), TAG_FRAGMENT_REGISTRO, R.id.content_autentication_activity, getFragmentManager());
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void setAutenticarUsuarioStepOneExito() {
        Forms.desbloquerFormulariosOneButton(addInputs(), buttonSiguiente);
        buttonSiguiente.setText(getResources().getString(R.string.inicia_sesion_textview));
        textInputLayoutPassword.setVisibility(View.VISIBLE);
    }

    @Override
    public void setAutenticarUsuarioStepOneError(String message) {
        Forms.desbloquerFormulariosOneButton(addInputs(), buttonSiguiente);
        textInputLayoutEmail.setErrorEnabled(true);
        textInputLayoutEmail.setError(message);
    }

    @Override
    public void setAutenticarUsuarioStepTwoExito(String userId) {
        Forms.desbloquerFormulariosOneButton(addInputs(), buttonSiguiente);
        mPreferences.putBoolean(SavePreferenceInterface.Autentication.IS_LOGIN, true);
        mPreferences.putString(SavePreferenceInterface.DatosUsuario.EMAIL, editTextEmail.getText().toString());
        mPreferences.putString(SavePreferenceInterface.DatosUsuario.PASSWORD, editTextPassword.getText().toString());
        mPreferences.putInt(SavePreferenceInterface.DatosUsuario.USER_ID, Integer.valueOf(userId));
        mPrsenter.consultarInfo(editTextEmail.getText().toString(), editTextPassword.getText().toString());
        //siguientePantalla();
    }

    private void siguientePantalla() {

        if (mPreferences.getBoolean(SavePreferenceInterface.DatosBiologicos.CONFIGURACION_TERMINADA)) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        } else {
            Intent mIntent = new Intent(getActivity(), ConfiguracionTurActivity.class);
            startActivity(mIntent);
            getActivity().finish();
        }
    }

    @Override
    public void setAutenticarUsuarioStepTwoError(String message) {
        mPreferences.putBoolean(SavePreferenceInterface.Autentication.IS_LOGIN, false);
        Forms.desbloquerFormulariosOneButton(addInputs(), buttonSiguiente);
        //Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        SweetAlertDialog dialog = new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE);
        dialog.setContentText(message);
        dialog.setConfirmButton("Aceptar", SweetAlertDialog::dismissWithAnimation);
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public void setPasswordDialog(String message)
    {
        mDialog = new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE);
        mDialog.setContentText(message);
        mDialog.setCancelable(false);
        mDialog.setConfirmButton("Aceptar",SweetAlertDialog::dismissWithAnimation);
        mDialog.show();
    }

    @Override
    public void showDialog(String message) {
        mDialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
        mDialog.getProgressHelper().setBarColor(Color.parseColor("#DF7C73"));
        mDialog.setTitleText(message);
        mDialog.setCancelable(false);
        mDialog.show();
    }

    @Override
    public void hideDialog() {
        mDialog.dismiss();
    }

    @Override
    public void setShowError(String message)
    {
        mDialog = new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE);
        mDialog.setContentText(message);
        mDialog.setConfirmButton("Aceptar",SweetAlertDialog::dismissWithAnimation);
        mDialog.setCancelable(false);
        mDialog.show();
    }

    @Override
    public void saveUserInfo(ResponseUser response)
    {
        String fechaNacimiento = (response.getData().getFechaNacimiento() != null) ? response.getData().getFechaNacimiento() : "";
        String fechaInicioPeriodo = (response.getData().getFechaInicioPeriodo() != null) ? response.getData().getFechaInicioPeriodo() : "";
        String duracionCiclo = (response.getData().getDuracionCiclo() != null) ? response.getData().getDuracionCiclo() : "";
        String duracionPeriodo = (response.getData().getDuracionPeriodo() != null) ? response.getData().getDuracionPeriodo() : "";
        mPreferences.putString(SavePreferenceInterface.DatosUsuario.FECHA_NACIMIENTO,fechaNacimiento);
        mPreferences.putString(SavePreferenceInterface.DatosBiologicos.DURACION_CICLO_MENSTRUAL,duracionCiclo);
        mPreferences.putString(SavePreferenceInterface.DatosBiologicos.DURACION_PERIODO_MENSTRUAL,duracionPeriodo);
        mPreferences.putString(SavePreferenceInterface.DatosBiologicos.FECHA_INICIO_PERIDO,fechaInicioPeriodo);

        if (fechaInicioPeriodo.isEmpty() || fechaNacimiento.isEmpty() || duracionCiclo.isEmpty() || duracionPeriodo.isEmpty()) {
            mPreferences.putBoolean(SavePreferenceInterface.DatosBiologicos.CONFIGURACION_TERMINADA, false);
            mPreferences.putBoolean(SavePreferenceInterface.Autentication.IS_LOGIN, false);
        }else {
            mPreferences.putBoolean(SavePreferenceInterface.DatosBiologicos.CONFIGURACION_TERMINADA, true);
            mPreferences.putBoolean(SavePreferenceInterface.Autentication.IS_LOGIN, true);
        }

        siguientePantalla();
    }

    @OnClick(R.id.content_login)
    public void onClickOutside() {
        Forms.hideKeyboard(getActivity());
    }

    @OnClick(R.id.tv_forgon_password)
    public void onViewClicked()
    {
        if (!editTextEmail.getText().toString().isEmpty())
        {
            String email = editTextEmail.getText().toString();
            SweetAlertDialog dialog = new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE);
            dialog.setCancelable(false);
            dialog.setContentText("Te enviaremos por correo electrónico las instrucciones para restablecer tu contraseña");
            dialog.setConfirmButton("Ok", (sweet) -> {
                sweet.dismissWithAnimation();
                mPrsenter.recuperarPassword(email);
            });
            dialog.setCancelButton("Cancelar", SweetAlertDialog::dismissWithAnimation);
            dialog.show();
        }
        else
        {
            SweetAlertDialog dialog = new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE);
            dialog.setCancelable(false);
            dialog.setContentText("Antes debes ingresar tu correo electrónico");
            dialog.show();
        }
    }
}
