package com.sagarnileshshah.carouselmvp.data;

import android.content.Context;

import com.sagarnileshshah.carouselmvp.data.local.LocalDataSource;
import com.sagarnileshshah.carouselmvp.data.models.comment.Comment;
import com.sagarnileshshah.carouselmvp.data.models.photo.Photo;
import com.sagarnileshshah.carouselmvp.data.remote.RemoteDataSource;
import com.sagarnileshshah.carouselmvp.util.NetworkHelper;

import java.util.List;

public class DataRepository {

    private RemoteDataSource remoteDataSource;
    private LocalDataSource localDataSource;
    private static DataRepository dataRepository;

    public DataRepository(RemoteDataSource remoteDataSource, LocalDataSource localDataSource) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
    }

    public static synchronized DataRepository getInstance(RemoteDataSource remoteDataSource,
                                                          LocalDataSource localDataSource) {
        if (dataRepository == null) {
            dataRepository = new DataRepository(remoteDataSource, localDataSource);
        }
        return dataRepository;
    }

    public void getPhotos(Context context, int page, final DataSource.GetPhotosCallback callback) {
        if (NetworkHelper.isInternetAvailable(context)) {
            remoteDataSource.getPhotos(page, new DataSource.GetPhotosCallback() {
                @Override
                public void onSuccess(List<Photo> photos) {
                    callback.onSuccess(photos);
                    localDataSource.storePhotos(photos);
                }

                @Override
                public void onFailure(Throwable throwable) {
                    callback.onFailure(throwable);
                }
            });
        } else {
            localDataSource.getPhotos(page, callback);
        }
    }

    public void getComments(Context context, String photoId, final DataSource.GetCommentsCallback callback) {
        if (NetworkHelper.isInternetAvailable(context)) {
            remoteDataSource.getComments(photoId, new DataSource.GetCommentsCallback() {
                @Override
                public void onSuccess(List<Comment> comments) {
                    callback.onSuccess(comments);
                }

                @Override
                public void onFailure(Throwable throwable) {
                    callback.onFailure(throwable);
                }
            });
        } else {
            localDataSource.getComments(photoId, callback);
        }
    }
}
