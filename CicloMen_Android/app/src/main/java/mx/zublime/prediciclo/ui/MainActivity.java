package mx.zublime.prediciclo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import mx.zublime.prediciclo.R;
import mx.zublime.prediciclo.base.BaseActivity;
import mx.zublime.prediciclo.ui.home.HomeFragment;
import mx.zublime.prediciclo.ui.more.MoreFragment;
import mx.zublime.prediciclo.ui.tienda.TiendaFragment;
import com.crashlytics.android.Crashlytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends BaseActivity
{

    public @BindView(R.id.bottom_navigation) BottomNavigationView mBottomNavigation;

    public static Fragment mHomeFragment;
    public static Fragment mStoreFragment;
    public static Fragment mMoreFragment;
    private final FragmentManager mFragmentManager = getSupportFragmentManager();
    public static Fragment mActive = mHomeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Fabric.with(this, new Crashlytics());

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mBottomNavigation.setOnNavigationItemSelectedListener(mSelectedListener);

        mHomeFragment = new HomeFragment();
        mStoreFragment = new TiendaFragment();
        mMoreFragment = new MoreFragment();

        mFragmentManager.beginTransaction().add(R.id.main_container,mHomeFragment,"1").commit();
        mFragmentManager.beginTransaction().add(R.id.main_container,mStoreFragment,"2").commit();
        mFragmentManager.beginTransaction().add(R.id.main_container,mMoreFragment,"3").commit();

        mFragmentManager.beginTransaction().hide(mMoreFragment).hide(mStoreFragment).commit();

        mActive = mHomeFragment;
    }

    @Override
    protected void onNewIntent (Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(getIntent().hasExtra("TAG")){
            int TAG = getIntent().getExtras().getInt("TAG", 0);

            switch (TAG) {
                case 1:

                    mFragmentManager.beginTransaction().hide(mStoreFragment).hide(mMoreFragment).show(mHomeFragment).commit();
                    mActive = mHomeFragment;

                    break;

                case 2:

                    mFragmentManager.beginTransaction().hide(mHomeFragment).hide(mMoreFragment).show(mStoreFragment).commit();
                    mActive = mStoreFragment;

                    break;

                case 3:

                    mFragmentManager.beginTransaction().hide(mHomeFragment).hide(mStoreFragment).show(mMoreFragment).commit();
                    mActive = mMoreFragment;

                    break;
            }
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mSelectedListener = item ->
    {
        switch (item.getItemId())
        {

            case R.id.home_item:
                if (mActive != mHomeFragment)
                {
                    mFragmentManager.beginTransaction().hide(mActive).show(mHomeFragment).commit();
                    mActive = mHomeFragment;
                }
                //changeFragment(mHomeFragment);
                return true;
            case R.id.comprar_item:
                if (mActive != mStoreFragment)
                {
                    mFragmentManager.beginTransaction().hide(mActive).show(mStoreFragment).commit();
                    mActive = mStoreFragment;
                }
                //changeFragment(mStoreFragment);
                return true;
            case R.id.mas_item:
                if (mActive != mMoreFragment)
                {
                    mFragmentManager.beginTransaction().hide(mActive).show(mMoreFragment).commit();
                    mActive = mMoreFragment;
                }
                //changeFragment(mMoreFragment);
                return true;
        }
        return false;
    };

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}

