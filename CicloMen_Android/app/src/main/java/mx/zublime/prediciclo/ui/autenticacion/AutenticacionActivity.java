package mx.zublime.prediciclo.ui.autenticacion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import mx.zublime.prediciclo.R;
import mx.zublime.prediciclo.ui.autenticacion.login.LoginFragment;
import mx.zublime.prediciclo.util.Forms;

public class AutenticacionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autenticacion);
        initView();
    }
    private void initView(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        LoginFragment fragmentLogin = new LoginFragment();
        fragmentTransaction.add(R.id.content_autentication_activity, fragmentLogin);
        fragmentTransaction.commit();
    }

}
