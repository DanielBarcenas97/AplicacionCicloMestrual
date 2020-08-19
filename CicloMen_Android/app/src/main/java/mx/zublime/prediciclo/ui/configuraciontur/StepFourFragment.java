package mx.zublime.prediciclo.ui.configuraciontur;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.pedant.SweetAlert.SweetAlertDialog;
import mx.zublime.prediciclo.R;
import mx.zublime.prediciclo.data.local.SavePreferenceInterface;
import mx.zublime.prediciclo.data.local.SavePreferenceManager;
import mx.zublime.prediciclo.ui.MainActivity;
import mx.zublime.prediciclo.ui.configuraciontur.mvp.ConfigurationContract;
import mx.zublime.prediciclo.ui.configuraciontur.mvp.ConfigurationPresenter;

public class StepFourFragment extends Fragment implements ConfigurationContract.ConfigurationContractView
{

    @BindView(R.id.calendar_fecha_nacimiento)
    DatePicker fecha;
    @BindView(R.id.btn_previous_step) MaterialButton mPrevioButton;
    @BindView(R.id.btn_next_step) MaterialButton mSiguienteButton;
    @BindView(R.id.content_controler_linearlayout) LinearLayout mContent;


    private Context mContext;
    private View mView;
    private Unbinder mUbinder;
    private Window mWindow;
    private SweetAlertDialog mDialog;
    private SavePreferenceManager mPreferences;
    private ConfigurationPresenter mPresenter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            mContext = context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPreferences = new SavePreferenceManager(mContext);
        mPresenter = new ConfigurationPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_step_four, container, false);
        mUbinder = ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    private void initViews()
    {
        mWindow = getActivity().getWindow();
        mWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        mWindow.setStatusBarColor(mContext.getResources().getColor(R.color.color_step_4));
        mContent.setBackgroundColor(mContext.getResources().getColor(R.color.color_step_4));
        Calendar c = Calendar.getInstance();
        fecha.updateDate(1990, c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onDestroyView()
    {
        if (mUbinder != null)
        {
            mUbinder = null;
        }
        super.onDestroyView();
    }

    @OnClick(R.id.btn_previous_step)
    public void onPreviousStepClicked()
    {
        getFragmentManager().popBackStack();
    }

    @OnClick(R.id.btn_next_step)
    public void onNextStepClicked()
    {
        Bundle args = getArguments();
        String inicio = StepOneFragment.getPeriodo(args);
        String duracion = StepTwoFragment.getDuracionPeriodo(args);
        String duracionCiclo = StepThreeFragment.getDuracionCiclo(args);
        String passwordNew = mPreferences.getString(SavePreferenceInterface.DatosUsuario.PASSWORD);
        String date = fecha.getYear() + "-" + (fecha.getMonth()+1) + "-" + fecha.getDayOfMonth();




        int id = mPreferences.getInt(SavePreferenceInterface.DatosUsuario.USER_ID);
        Log.wtf("INICIO",inicio);
        Log.wtf("DURACION",duracion);
        Log.wtf("CICLO",duracionCiclo);
        Log.wtf("DATE",date);
        mPresenter.establecerDatos(inicio,duracion,duracionCiclo,date,id);
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
    public void onFailure(String message)
    {
        mDialog = new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE);
        //mDialog.getProgressHelper().setBarColor(Color.parseColor("#DF7C73"));
        mDialog.setTitleText(message);
        mDialog.setConfirmButton("Aceptar", SweetAlertDialog::dismissWithAnimation);
        mDialog.setCancelable(false);
        mDialog.show();
    }

    @Override
    public void exit()
    {
        Bundle args = getArguments();
        String inicio = StepOneFragment.getPeriodo(args);
        String duracion = StepTwoFragment.getDuracionPeriodo(args);
        String duracionCiclo = StepThreeFragment.getDuracionCiclo(args);
        String date = fecha.getYear() + "-" + fecha.getMonth() + "-" + fecha.getDayOfMonth();
        mPreferences.putString(SavePreferenceInterface.DatosUsuario.FECHA_NACIMIENTO,date);
        mPreferences.putString(SavePreferenceInterface.DatosBiologicos.DURACION_CICLO_MENSTRUAL,duracionCiclo);
        mPreferences.putString(SavePreferenceInterface.DatosBiologicos.DURACION_PERIODO_MENSTRUAL,duracion);
        mPreferences.putString(SavePreferenceInterface.DatosBiologicos.FECHA_INICIO_PERIDO,inicio);
        mPreferences.putBoolean(SavePreferenceInterface.DatosBiologicos.CONFIGURACION_TERMINADA,true);
        Intent main = new Intent(mContext, MainActivity.class);
        startActivity(main);
        getActivity().finish();
    }
}
