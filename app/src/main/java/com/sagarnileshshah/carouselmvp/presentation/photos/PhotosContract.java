package com.sagarnileshshah.carouselmvp.presentation.photos;

import android.content.Context;

import com.sagarnileshshah.carouselmvp.data.models.photo.Photo;
import com.sagarnileshshah.carouselmvp.util.BasePresenter;
import com.sagarnileshshah.carouselmvp.util.BaseView;
import java.util.List;

/**
 *
 */
interface PhotosContract {

  interface View extends BaseView<Presenter> {

    void showPhotos(List<Photo> photos);

    void showProgressBar();

    void hideProgrssBar();

    void showErrorMessage();

    Context getContext();
  }

  interface Presenter extends BasePresenter<View> {

    void getPhotos(int page);

    void onPause();

    void onResume(View view);
  }
}
