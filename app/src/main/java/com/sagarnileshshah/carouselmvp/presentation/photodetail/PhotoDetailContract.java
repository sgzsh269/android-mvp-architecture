package com.sagarnileshshah.carouselmvp.presentation.photodetail;

import android.content.Context;

import com.sagarnileshshah.carouselmvp.data.models.comment.Comment;
import com.sagarnileshshah.carouselmvp.data.models.photo.Photo;
import com.sagarnileshshah.carouselmvp.util.BasePresenter;
import com.sagarnileshshah.carouselmvp.util.BaseView;

import java.util.List;

interface PhotoDetailContract {

    interface View {

        void showPhoto(Photo photo);

        void showComments(List<Comment> comments);

        void showErrorMessage();

        Context getContext();

        void showProgressBar();

        void hideProgressBar();
    }

    interface Presenter {

        void getComments(String photoId);

        void onPause();

        void onResume(View view);
    }
}
