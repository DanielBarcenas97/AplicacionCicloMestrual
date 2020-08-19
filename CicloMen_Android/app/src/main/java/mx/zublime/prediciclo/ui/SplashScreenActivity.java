package mx.zublime.prediciclo.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import mx.zublime.prediciclo.R;
import mx.zublime.prediciclo.data.local.SavePreferenceInterface;
import mx.zublime.prediciclo.data.local.SavePreferenceManager;
import mx.zublime.prediciclo.ui.autenticacion.AutenticacionActivity;
import mx.zublime.prediciclo.util.NavigatorUtils;

public class SplashScreenActivity extends AppCompatActivity {

    private SavePreferenceManager mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (!task.isSuccessful()) {
                    Log.wtf("ZUBLIME", "getInstanceId failed", task.getException());
                    return;
                }

                // Get new Instance ID token
                String token = task.getResult().getToken();

                Log.wtf("ZUBLIME", token);
            }
        });

        setContentView(R.layout.activity_splash_screen);
        iniciarSplashScreen();
    }

    private void iniciarSplashScreen(){
        mPreferences = new SavePreferenceManager(this);

        new Handler().postDelayed(() ->
        {
            if(!mPreferences.getBoolean(SavePreferenceInterface.Autentication.IS_LOGIN)){
                Intent intent = new Intent(SplashScreenActivity.this,AutenticacionActivity.class);
                startActivity(intent);
                finish();
               /* NavigatorUtils.navigateTo(SplashScreenActivity.this,NavigatorUtils.createIntentFromTo(
                        SplashScreenActivity.this, AutenticacionActivity.class,true));*/
            }else {
                Intent intent = new Intent(SplashScreenActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
               /* NavigatorUtils.navigateTo(SplashScreenActivity.this,NavigatorUtils.createIntentFromTo(
                       SplashScreenActivity.this,MainActivity.class,true));*/
            }

        },1500);
    }
}
