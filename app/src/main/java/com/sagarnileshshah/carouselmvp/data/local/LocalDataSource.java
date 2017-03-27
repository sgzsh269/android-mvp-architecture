package com.sagarnileshshah.carouselmvp.data.local;

import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;
import com.sagarnileshshah.carouselmvp.data.DataSource;
import com.sagarnileshshah.carouselmvp.data.models.photo.Photo;
import com.sagarnileshshah.carouselmvp.util.threading.MainUiThread;
import com.sagarnileshshah.carouselmvp.util.threading.ThreadExecutor;

import java.util.List;

public class LocalDataSource extends DataSource {

    private static LocalDataSource localDataSource;

    private LocalDataSource(MainUiThread mainUiThread, ThreadExecutor threadExecutor) {
        super(mainUiThread, threadExecutor);
    }

    public static synchronized LocalDataSource getInstance(MainUiThread mainUiThread,
                                              ThreadExecutor threadExecutor) {
        if (localDataSource == null) {
            localDataSource = new LocalDataSource(mainUiThread, threadExecutor);
        }
        return localDataSource;
    }

    @Override
    public void getPhotos(final int page, final GetPhotosCallback callback) {

        threadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                final List<Photo> photos = SQLite.select()
                        .from(Photo.class)
                        .limit(10)
                        .offset((page - 1) * 10)
                        .queryList();


                mainUiThread.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(photos);
                    }
                });
            }
        });
    }

    @Override
    public void getComments(String photoId, GetCommentsCallback callback) {

    }


    public void storePhotos(final List<Photo> photos) {
        DatabaseDefinition database = FlowManager.getDatabase(LocalDatabase.class);
        Transaction transaction = database.beginTransactionAsync(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                for (Photo photo : photos) {
                    photo.save();
                }
            }
        }).build();
        transaction.execute();
    }
}
