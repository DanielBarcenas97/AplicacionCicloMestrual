package mx.zublime.prediciclo.data.local;

import android.content.Context;
import android.content.SharedPreferences;

public class SavePreferenceManager {
    private Context mcontext;
    private SharedPreferences mSharedPreferences;

    public SavePreferenceManager(Context context){
        this.mcontext = context;
        mSharedPreferences = mcontext.getSharedPreferences("Prediciclo",Context.MODE_PRIVATE);
    }

    // getters
    public String getString(String key) {
        return mSharedPreferences.getString(key, "");
    }

    public int getInt(String key) {
        return mSharedPreferences.getInt(key, 0);
    }

    public long getLong(String key) {
        return mSharedPreferences.getLong(key, 0);
    }

    public boolean getBoolean(String key) {
        return mSharedPreferences.getBoolean(key, false);
    }

    // puts o setters

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public void putInt(String key, int value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public void putLong(String key, long value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public void remove(String key) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }
    public void removeAllPreferences(){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
