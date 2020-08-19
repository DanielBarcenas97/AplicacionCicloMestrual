package mx.zublime.prediciclo.base;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import mx.zublime.prediciclo.ui.autenticacion.AutenticacionActivity;

public class BaseActivity extends AppCompatActivity
{
    public void closeActivity()
    {
        Intent intent = new Intent(this, AutenticacionActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        this.finish();
    }
}
