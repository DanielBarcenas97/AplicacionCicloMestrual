package mx.zublime.prediciclo.util;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class Forms {

    public static void bloquerFormulariosOneButton(List<TextInputEditText> listInputs, MaterialButton button){
        if(listInputs!= null && !listInputs.isEmpty() && button != null){
            button.setEnabled(false);
            for (TextInputEditText input: listInputs) {
                input.setEnabled(false);
            }
        }
    }
    public static void   desbloquerFormulariosOneButton(List<TextInputEditText> listInputs, MaterialButton button){
        if(listInputs != null && !listInputs.isEmpty() && button!= null){
            button.setEnabled(true);
            for (TextInputEditText input: listInputs) {
                input.setEnabled(true);
            }
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
