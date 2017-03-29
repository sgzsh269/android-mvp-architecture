package com.sagarnileshshah.carouselmvp.presentation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.sagarnileshshah.carouselmvp.R;
import com.sagarnileshshah.carouselmvp.presentation.photos.PhotosFragment;
import com.sagarnileshshah.carouselmvp.util.FoaBaseActivity;
import com.sagarnileshshah.carouselmvp.util.NetworkHelper;

import static android.net.ConnectivityManager.CONNECTIVITY_ACTION;
import static android.view.View.GONE;

public class MainActivity extends FoaBaseActivity {

    @BindView(R.id.fragmentPlaceHolder)
    FrameLayout fragmentPlaceholder;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tvOfflineMode)
    TextView tvOfflineMode;

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
            if(!NetworkHelper.getInstance().isNetworkAvailable(context)){
                tvOfflineMode.setVisibility(View.VISIBLE);
            } else {
                tvOfflineMode.setVisibility(View.GONE);
            }
        }
    };
}
