package com.sagarnileshshah.carouselmvp.util.mvp;


public interface IBasePresenter<View> {

    void onViewActive(View View);

    void onViewInactive();
}
