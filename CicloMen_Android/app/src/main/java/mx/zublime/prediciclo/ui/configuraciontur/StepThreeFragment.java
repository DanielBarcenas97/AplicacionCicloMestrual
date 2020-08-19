package mx.zublime.prediciclo.ui.configuraciontur;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.shawnlin.numberpicker.NumberPicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import mx.zublime.prediciclo.R;
import mx.zublime.prediciclo.util.NavigatorUtils;


public class StepThreeFragment extends Fragment {

    @BindView(R.id.number_picker_ciclo)
    NumberPicker numberPickerClico;
    @BindView(R.id.btn_previous_step)
    MaterialButton mPrevioButton;
    @BindView(R.id.btn_next_step)
    MaterialButton mSiguienteButton;
    @BindView(R.id.content_controler_linearlayout)
    LinearLayout mContent;
    private Context mContext;
    private Unbinder mUnbinder;
    private View mView;
    private String[] diasCicloArray;
    private String duracionCiclo;
    private Window mWindow;
    private static final String DURACION_CICLO_KEY = "mx.zublime.prediciclo.ui.configuraciontur.duracion_ciclo_key";

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_step_three, container, false);
        mUnbinder = ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        diasCicloArray = new String[9];
        llenarNumberPiker();
        listenerPiker();
        mWindow = getActivity().getWindow();
        mWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        mWindow.setStatusBarColor(mContext.getResources().getColor(R.color.color_step_3));
        mContent.setBackgroundColor(mContext.getResources().getColor(R.color.color_step_3));
        duracionCiclo = String.valueOf(numberPickerClico.getValue());
    }

    private void llenarNumberPiker()
    {
        numberPickerClico.setMaxValue(6);
        numberPickerClico.setMinValue(3);
        numberPickerClico.setValue(5);
//
//        int inicioCiclo = 2;
//        for (int inicio = 0; inicio <= 8; inicio++) {
//            diasCicloArray[inicio] = String.valueOf(inicioCiclo += 1);
//        }
//        numberPickerClico.setDisplayedValues(diasCicloArray);
    }

    private void listenerPiker()
    {

        numberPickerClico.setOnClickListener(view -> Log.d("NUMBERPIKER", "Click on current value"));
        numberPickerClico.setOnValueChangedListener((picker, oldVal, newVal) -> duracionCiclo = String.valueOf(picker.getValue()));
    }

    @OnClick(R.id.btn_previous_step)
    public void onPreviousStepClicked()
    {
        getFragmentManager().popBackStack();
    }

    @OnClick(R.id.btn_next_step)
    public void onNextStepClicked()
    {
        Fragment stepTwo = new StepFourFragment();
        Bundle args = getArguments();
        args.putString(DURACION_CICLO_KEY,duracionCiclo);
        stepTwo.setArguments(args);
        NavigatorUtils.replaceFragmentWithAnimation(stepTwo,"STEP FOUR",R.id.frame_layout_content,getFragmentManager());
    }

    @Override
    public void onDestroyView() {
        if (mUnbinder != null) {
            mUnbinder = null;
        }
        super.onDestroyView();
    }

    public static String getDuracionCiclo(Bundle args)
    {
        return args.getString(DURACION_CICLO_KEY);
    }
}
