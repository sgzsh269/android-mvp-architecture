package com.sagarnileshshah.carouselmvp.ui.photos;

import android.content.Context;

import com.sagarnileshshah.carouselmvp.data.models.photo.Photo;
import com.sagarnileshshah.carouselmvp.util.mvp.IBasePresenter;
import com.sagarnileshshah.carouselmvp.util.mvp.IBaseView;

import java.util.List;

/**
 * The interface that exposes the functionalities of a Photo View and Presenter
 */
interface PhotosContract {

    interface View extends IBaseView {

        void showPhotos(List<Photo> photos);

        void shouldShowPlaceholderText();
    }

    interface Presenter extends IBasePresenter<View> {

        void getPhotos(Context context, int page);
    }
}
