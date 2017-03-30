package com.sagarnileshshah.carouselmvp.util.mvp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sagarnileshshah.carouselmvp.R;

import butterknife.BindView;


public abstract class BaseView extends Fragment implements IBaseView {

    @BindView(R.id.progressBar)
    protected ProgressBar progressBar;

    @Override
    public void showToastMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setProgressBar(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
