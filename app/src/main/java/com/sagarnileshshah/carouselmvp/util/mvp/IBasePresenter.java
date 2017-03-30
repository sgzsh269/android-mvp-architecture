package com.sagarnileshshah.carouselmvp.util.mvp;


public interface IBasePresenter<ViewT> {

    void onViewActive(ViewT view);

    void onViewInactive();
}
