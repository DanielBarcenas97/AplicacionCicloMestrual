package mx.zublime.prediciclo.ui.configuraciontur;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import mx.zublime.prediciclo.R;
import mx.zublime.prediciclo.ui.MainActivity;
import mx.zublime.prediciclo.util.NavigatorUtils;

public class ConfiguracionTurActivity extends AppCompatActivity
{

    //binding view whit code
    @BindView(R.id.frame_layout_content) FrameLayout frameLayoutContent;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion_tur);
        unbinder = ButterKnife.bind(this);
        initView();
    }


    private void initView()
    {
        NavigatorUtils.replaceFragmentWithAnimation(new StepOneFragment(),"STEP ONE",R.id.frame_layout_content,getSupportFragmentManager());
    }

    @Override
    protected void onDestroy()
    {
        if(unbinder != null)
        {
            unbinder = null;
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed()
    {
        //anteriorPasoBack();
       // super.onBackPressed();
    }

}
