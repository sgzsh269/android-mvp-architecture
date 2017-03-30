package com.sagarnileshshah.carouselmvp.data.remote;

import com.sagarnileshshah.carouselmvp.data.DataSource;
import com.sagarnileshshah.carouselmvp.util.threading.MainUiThread;
import com.sagarnileshshah.carouselmvp.util.threading.ThreadExecutor;

/**
 * Created by sshah on 3/28/17.
 */

public class FakeRemoteDataSource extends DataSource {

    public static FakeRemoteDataSource fakeRemoteDataSource;

    public FakeRemoteDataSource(MainUiThread mainUiThread, ThreadExecutor threadExecutor) {
        super(mainUiThread, threadExecutor);
    }

    @Override
    public void getPhotos(int page, GetPhotosCallback callback) {

    }

    @Override
    public void getComments(String photoId, GetCommentsCallback callback) {

    }

    public static synchronized FakeRemoteDataSource getInstance(MainUiThread mainUiThread,
            ThreadExecutor threadExecutor) {
        if (fakeRemoteDataSource == null) {
            fakeRemoteDataSource = new FakeRemoteDataSource(mainUiThread, threadExecutor);
        }
        return fakeRemoteDataSource;
    }
}
