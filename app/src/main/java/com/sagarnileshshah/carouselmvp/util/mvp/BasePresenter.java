package com.sagarnileshshah.carouselmvp.util.mvp;

/**
 * Created by sshah on 3/29/17.
 */

public class BasePresenter<ViewT> implements IBasePresenter<ViewT> {

    protected ViewT view;

    @Override
    public void onViewActive(ViewT view) {
        this.view = view;
    }

    @Override
    public void onViewInactive() {
        view = null;
    }
}
