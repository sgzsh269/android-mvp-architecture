package com.sagarnileshshah.carouselmvp.ui.photodetail;

import android.content.Context;

import com.sagarnileshshah.carouselmvp.data.models.comment.Comment;
import com.sagarnileshshah.carouselmvp.data.models.photo.Photo;
import com.sagarnileshshah.carouselmvp.util.mvp.IBasePresenter;
import com.sagarnileshshah.carouselmvp.util.mvp.IBaseView;

import java.util.List;

/**
 * The interface that exposes the functionalities of a Photo Detail View and Presenter
 */
interface PhotoDetailContract {

    interface View extends IBaseView {

        void showPhoto(Photo photo);

        void showComments(List<Comment> comments);

        void shouldShowPlaceholderText();
    }

    interface Presenter extends IBasePresenter<View> {

        void getComments(Context context, Photo photo);
    }
}
