package com.sagarnileshshah.carouselmvp.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.sagarnileshshah.carouselmvp.R;
import com.sagarnileshshah.carouselmvp.ui.photos.PhotosFragment;
import com.sagarnileshshah.carouselmvp.util.BaseFragmentInteractionListener;
import com.sagarnileshshah.carouselmvp.util.FoaBaseActivity;
import com.sagarnileshshah.carouselmvp.util.NetworkHelper;

import static android.net.ConnectivityManager.CONNECTIVITY_ACTION;

/**
 * The container responsible for showing and destroying relevant {@link Fragment}, handling
 * back and up navigation using the Fragment back stack and maintaining global state
 * and event subscriptions. This is based on the Fragment Oriented Architecture explained here
 * http://vinsol.com/blog/2014/09/15/advocating-fragment-oriented-applications-in-android/
 */
public class MainActivity extends FoaBaseActivity implements BaseFragmentInteractionListener {

    @BindView(R.id.fragmentPlaceHolder)
    FrameLayout fragmentPlaceholder;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tvOfflineMode)
    TextView tvOfflineMode;

    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;

    private IntentFilter connectivityIntentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        showFragment(PhotosFragment.class);
        connectivityIntentFilter = new IntentFilter(CONNECTIVITY_ACTION);
    }


    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(connectivityBroadcastReceiver, connectivityIntentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(connectivityBroadcastReceiver);
        super.onPause();
    }


    BroadcastReceiver connectivityBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!NetworkHelper.getInstance().isNetworkAvailable(context)) {
                tvOfflineMode.setVisibility(View.VISIBLE);
            } else {
                tvOfflineMode.setVisibility(View.GONE);
            }
        }
    };


    @Override
    public void resetToolBarScroll() {
        appBarLayout.setExpanded(true, true);
    }
}
