package com.sagarnileshshah.carouselmvp.presentation.photodetail;

import com.sagarnileshshah.carouselmvp.data.DataRepository;
import com.sagarnileshshah.carouselmvp.data.DataSource;
import com.sagarnileshshah.carouselmvp.data.models.comment.Comment;
import com.sagarnileshshah.carouselmvp.data.models.photo.Photo;
import com.sagarnileshshah.carouselmvp.util.threading.MainUiThread;
import com.sagarnileshshah.carouselmvp.util.threading.ThreadExecutor;

import java.util.List;

/**
 * Created by sshah on 3/25/17.
 */

public class PhotoDetailPresenter implements PhotoDetailContract.Presenter {

    private PhotoDetailContract.View view;
    private DataRepository dataRepository;
    private ThreadExecutor threadExecutor;
    private MainUiThread mainUiThread;

    public PhotoDetailPresenter(PhotoDetailContract.View view, DataRepository dataRepository,
                                ThreadExecutor threadExecutor, MainUiThread mainUiThread) {
        this.view = view;
        this.dataRepository = dataRepository;
        this.threadExecutor = threadExecutor;
        this.mainUiThread = mainUiThread;
    }

    @Override
    public void getComments(Photo photo) {
        view.showProgressBar();

        dataRepository.getComments(view.getContext(), photo, new DataSource.GetCommentsCallback() {
            @Override
            public void onSuccess(List<Comment> comments) {
                if (view != null) {
                    view.showComments(comments);
                    view.hideProgressBar();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                if (view != null) {
                    view.hideProgressBar();
                    view.showErrorMessage();
                }
            }
        });
    }

    @Override
    public void onPause() {
        view = null;
    }

    @Override
    public void onResume(PhotoDetailContract.View view) {
        this.view = view;
    }
}
