package com.sagarnileshshah.carouselmvp.data;

import com.sagarnileshshah.carouselmvp.data.models.comment.Comment;
import com.sagarnileshshah.carouselmvp.data.models.photo.Photo;
import com.sagarnileshshah.carouselmvp.util.threading.MainUiThread;
import com.sagarnileshshah.carouselmvp.util.threading.ThreadExecutor;
import java.util.List;

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
  }

  public interface GetCommentsCallback {

    void onSuccess(List<Comment> comments);

    void onFailure(Throwable throwable);
  }

  abstract public void getPhotos(int page, GetPhotosCallback callback);

  abstract public void getComments(String photoId, GetCommentsCallback callback);
}
