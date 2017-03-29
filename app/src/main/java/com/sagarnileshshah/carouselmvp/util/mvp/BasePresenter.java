package com.sagarnileshshah.carouselmvp.util.mvp;

/**
 * Created by sshah on 3/29/17.
 */

public class BasePresenter<View> implements IBasePresenter<View> {

    protected View view;

    @Override
    public void onViewActive(View view) {
        this.view = view;
    }

    @Override
    public void onViewInactive() {
        view = null;
    }
}
