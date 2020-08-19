package mx.zublime.prediciclo.ui.configuraciontur;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import mx.zublime.prediciclo.R;
import mx.zublime.prediciclo.util.NavigatorUtils;

public class StepOneFragment extends Fragment {

    @BindView(R.id.calendar_periodo)
    CalendarView calendarPeriodo;
    @BindView(R.id.btn_previous_step)
    MaterialButton mPrevioButton;
    @BindView(R.id.btn_next_step)
    MaterialButton mSiguienteButton;
    @BindView(R.id.content_controler_linearlayout)
    LinearLayout mContentController;

    private Context mContext;
    private View mView;
    private Unbinder mUbinder;
    private Window mWindow;
    private static final String PERIODO_KEY = "mx.zublime.prediciclo.ui.configuraciontur.periodos_key";
    private String mPeriodo;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity)
        {
            mContext = context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_step_one, container, false);
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
        mPrevioButton.setVisibility(View.INVISIBLE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0,0,0);

        //calendar.set(1990,calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        mPeriodo = format.format(calendar.getTime());

        calendarPeriodo.setOnDateChangeListener((calendarView, i, i1, i2) ->
        {
            Calendar selected = Calendar.getInstance();
            // year, mounth, day
            selected.set(i, i1, i2, 0,0,0);

            Calendar days_before_today = Calendar.getInstance();
            days_before_today.setTimeInMillis(calendar.getTimeInMillis() - (6 * 24 * 3600 * 1000) );

            if(selected.getTimeInMillis() < days_before_today.getTimeInMillis()) {
                calendarPeriodo.setDate(calendar.getTimeInMillis());
                Toast.makeText(getContext(), "No puedes seleccionar una fecha 6 días antes del día de hoy", Toast.LENGTH_SHORT).show();

            } else if (selected.getTimeInMillis() > calendar.getTimeInMillis()) {
                calendarPeriodo.setDate(calendar.getTimeInMillis());
                Toast.makeText(getContext(), "No puedes seleccionar una fecha posterior a hoy", Toast.LENGTH_SHORT).show();

            } else {
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                mPeriodo = formatter.format(selected.getTime());
            }


        });

        mWindow = getActivity().getWindow();
        mWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        mWindow.setStatusBarColor(mContext.getResources().getColor(R.color.color_step_1));
        mContentController.setBackgroundColor(mContext.getResources().getColor(R.color.color_step_1));
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

    public static String getPeriodo(Bundle bundle)
    {
        return bundle.getString(PERIODO_KEY);
    }

    @OnClick(R.id.btn_next_step)
    public void onSiguienteButtonClicked()
    {
        Fragment stepTwo = new StepTwoFragment();
        Bundle args = new Bundle();
        args.putString(PERIODO_KEY,mPeriodo);
        stepTwo.setArguments(args);
        NavigatorUtils.replaceFragmentWithAnimation(stepTwo,"STEP TWO",R.id.frame_layout_content,getFragmentManager());
    }
}
