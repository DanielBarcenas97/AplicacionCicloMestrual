package mx.zublime.prediciclo.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import mx.zublime.prediciclo.R;

public class NavigatorUtils {


    public static void replaceFragmentWithAnimation(Fragment fragment, String tag, int containter, FragmentManager fragmentManager) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        transaction.replace(containter, fragment);
        transaction.addToBackStack(tag);
        transaction.commit();
    }

    public static void replaceFragmentWithAnimationNoBackStack(Fragment fragment, String tag, int containter, FragmentManager fragmentManager) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        transaction.replace(containter, fragment);
        //transaction.addToBackStack(tag);
        transaction.commit();
    }
    public static Intent createIntentFromTo(Activity from, Class<?> to, boolean finishFromActivity) {
        Intent intent = new Intent(from, to);
        if (finishFromActivity) {
            from.finish();
        }
        return intent;
    }
    public static void navigateTo(Context context, Intent intent) {
        context.startActivity(intent);
    }

}
