package com.sagarnileshshah.carouselmvp.data;

import android.content.Context;

import com.sagarnileshshah.carouselmvp.data.local.LocalDataSource;
import com.sagarnileshshah.carouselmvp.data.models.comment.Comment;
import com.sagarnileshshah.carouselmvp.data.models.photo.Photo;
import com.sagarnileshshah.carouselmvp.util.NetworkHelper;

import java.util.List;

/**
 * The primary class for the presenters that extend
 * {@link com.sagarnileshshah.carouselmvp.util.mvp.BasePresenter} to interact with
 * for fetching and storing data.
 * It is the middleman in front of all data sources such as
 * {@link com.sagarnileshshah.carouselmvp.data.remote.RemoteDataSource}
 * and {@link com.sagarnileshshah.carouselmvp.data.local.LocalDataSource} and delegates the work to
 * them depending on conditions such as network availability, etc.
 */
public class DataRepository {

    private DataSource remoteDataSource;
    private DataSource localDataSource;
    private NetworkHelper networkHelper;

    private static DataRepository dataRepository;

    public DataRepository(DataSource remoteDataSource, DataSource localDataSource,
            NetworkHelper networkHelper) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
        this.networkHelper = networkHelper;
    }

    public static synchronized DataRepository getInstance(DataSource remoteDataSource,
            DataSource localDataSource,
            NetworkHelper networkHelper) {
        if (dataRepository == null) {
            dataRepository = new DataRepository(remoteDataSource, localDataSource, networkHelper);
        }
        return dataRepository;
    }

    public void getPhotos(Context context, int page, final DataSource.GetPhotosCallback callback) {
        if (networkHelper.isNetworkAvailable(context)) {
            remoteDataSource.getPhotos(page, new DataSource.GetPhotosCallback() {
                @Override
                public void onSuccess(List<Photo> photos) {
                    callback.onSuccess(photos);
                    ((LocalDataSource) localDataSource).storePhotos(photos);
                }

                @Override
                public void onFailure(Throwable throwable) {
                    callback.onFailure(throwable);
                }

                @Override
                public void onNetworkFailure() {
                    callback.onNetworkFailure();
                }
            });
        } else {
            localDataSource.getPhotos(page, callback);
        }
    }

    public void getComments(Context context, final Photo photo,
            final DataSource.GetCommentsCallback callback) {
        if (networkHelper.isNetworkAvailable(context)) {
            remoteDataSource.getComments(photo.getId(), new DataSource.GetCommentsCallback() {
                @Override
                public void onSuccess(List<Comment> comments) {
                    callback.onSuccess(comments);
                    ((LocalDataSource) localDataSource).storeComments(photo, comments);
                }

                @Override
                public void onFailure(Throwable throwable) {
                    callback.onFailure(throwable);
                }

                @Override
                public void onNetworkFailure() {
                    callback.onNetworkFailure();
                }
            });
        } else {
            localDataSource.getComments(photo.getId(), callback);
        }
    }

    public void destroyInstance() {
        dataRepository = null;
    }
}
