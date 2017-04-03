package com.sagarnileshshah.carouselmvp.util;


import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Base {@link Fragment} listener interface to be implemented by the hosting
 * {@link android.support.v4.app.ActivityCompat}. It should be extended by a
 * Fragment custom listener interface if any.
 */
public interface BaseFragmentInteractionListener {

    /**
     * Expands {@link android.support.v7.widget.Toolbar} to normal position if collapsed.
     */
    void resetToolBarScroll();

    /**
     * Used by a {@link Fragment} to show another Fragment.
     *
     * @param fragmentClass a {@link Fragment} class
     * @param bundle a {@link Bundle}
     * @param addToBackStack a boolean to add transaction to fragment back stack
     * @param <T> a generic type to indicate type/subclass of {@link Fragment}
     */
    <T extends Fragment> void showFragment(Class<T> fragmentClass, Bundle bundle,
            boolean addToBackStack);
}
