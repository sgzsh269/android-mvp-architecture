package com.sagarnileshshah.carouselmvp.util;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.sagarnileshshah.carouselmvp.R;

/**
 * Fragment Oriented Architecture based Activity
 */
public abstract class FoaBaseActivity extends AppCompatActivity {

    public <T extends Fragment> void showFragment(Class<T> fragmentClass, Bundle bundle) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(fragmentClass.getSimpleName());
        if (fragment == null) {
            try {
                fragment = fragmentClass.newInstance();
                fragment.setArguments(bundle);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        fragmentTransaction.replace(R.id.fragmentPlaceHolder, fragment, fragmentClass.getSimpleName());
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public <T extends Fragment> void showFragment(Class<T> fragmentClass) {
        showFragment(fragmentClass, null);
    }
}


