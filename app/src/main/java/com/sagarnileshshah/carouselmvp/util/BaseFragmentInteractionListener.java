package com.sagarnileshshah.carouselmvp.util;


import android.os.Bundle;
import android.support.v4.app.Fragment;

public interface BaseFragmentInteractionListener {

    void resetToolBarScroll();

    <T extends Fragment> void showFragment(Class<T> fragmentClass, Bundle bundle, boolean addToBackStack);
}
