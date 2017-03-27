package com.sagarnileshshah.carouselmvp.presentation;

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
import butterknife.BindView;
import butterknife.ButterKnife;
import com.sagarnileshshah.carouselmvp.R;
import com.sagarnileshshah.carouselmvp.presentation.photos.PhotosFragment;
import com.sagarnileshshah.carouselmvp.util.FoaBaseActivity;

public class MainActivity extends FoaBaseActivity implements PhotosFragment.OnFragmentInteractionListener {

  @BindView(R.id.fragmentPlaceHolder) FrameLayout fragmentPlaceholder;
  @BindView(R.id.toolbar) Toolbar toolbar;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);
    showFragment(PhotosFragment.class);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();

    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

}
