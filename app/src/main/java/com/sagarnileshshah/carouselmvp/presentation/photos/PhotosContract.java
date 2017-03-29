package com.sagarnileshshah.carouselmvp.presentation.photos;

import android.content.Context;

import com.sagarnileshshah.carouselmvp.data.models.photo.Photo;
import com.sagarnileshshah.carouselmvp.util.mvp.IBasePresenter;
import com.sagarnileshshah.carouselmvp.util.mvp.IBaseView;

import java.util.List;

/**
 *
 */
interface PhotosContract {

    interface View extends IBaseView {

        void showPhotos(List<Photo> photos);
    }

    interface Presenter extends IBasePresenter<View> {

        void getPhotos(Context context, int page);
    }
}
