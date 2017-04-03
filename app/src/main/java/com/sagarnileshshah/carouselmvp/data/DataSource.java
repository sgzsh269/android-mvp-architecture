package com.sagarnileshshah.carouselmvp.data;

import com.sagarnileshshah.carouselmvp.data.models.comment.Comment;
import com.sagarnileshshah.carouselmvp.data.models.photo.Photo;
import com.sagarnileshshah.carouselmvp.util.threading.MainUiThread;
import com.sagarnileshshah.carouselmvp.util.threading.ThreadExecutor;

import java.util.List;

/**
 * The interface that exposes fetching and storing data through helper methods. This is to be
 * implemented by all data sources such as
 * {@link com.sagarnileshshah.carouselmvp.data.remote.RemoteDataSource} and
 * {@link com.sagarnileshshah.carouselmvp.data.local.LocalDataSource}
 */
public abstract class DataSource {

    protected MainUiThread mainUiThread;
    protected ThreadExecutor threadExecutor;

    public DataSource(MainUiThread mainUiThread, ThreadExecutor threadExecutor) {
        this.mainUiThread = mainUiThread;
        this.threadExecutor = threadExecutor;
    }

    public interface GetPhotosCallback {
        void onSuccess(List<Photo> photos);

        void onFailure(Throwable throwable);

        void onNetworkFailure();

    }

    public interface GetCommentsCallback {

        void onSuccess(List<Comment> comments);

        void onFailure(Throwable throwable);

        void onNetworkFailure();

    }

    public abstract void getPhotos(int page, GetPhotosCallback callback);

    public abstract void getComments(String photoId, GetCommentsCallback callback);
}
