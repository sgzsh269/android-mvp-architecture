package com.sagarnileshshah.carouselmvp.presentation.photos;

import com.sagarnileshshah.carouselmvp.data.DataRepository;
import com.sagarnileshshah.carouselmvp.data.DataSource;
import com.sagarnileshshah.carouselmvp.data.models.photo.Photo;
import com.sagarnileshshah.carouselmvp.util.threading.MainUiThread;
import com.sagarnileshshah.carouselmvp.util.threading.ThreadExecutor;
import java.util.List;

public class PhotosPresenter implements PhotosContract.Presenter {

  private PhotosContract.View view;
  private DataRepository dataRepository;
  private ThreadExecutor threadExecutor;
  private MainUiThread mainUiThread;

  public PhotosPresenter(PhotosContract.View view, DataRepository dataRepository,
      ThreadExecutor threadExecutor, MainUiThread mainUiThread) {
    this.view = view;
    this.dataRepository = dataRepository;
    this.threadExecutor = threadExecutor;
    this.mainUiThread = mainUiThread;
    view.setPresenter(this);
  }

  @Override public void getPhotos(int page) {
    view.showProgressBar();

    dataRepository.getPhotos(view.getContext(), page, new DataSource.GetPhotosCallback() {
      @Override public void onSuccess(List<Photo> photos) {
        if (view != null) {
          view.showPhotos(photos);
          view.hideProgrssBar();
        }
      }

      @Override public void onFailure(Throwable throwable) {
        if (view != null) {
          view.hideProgrssBar();
          view.showErrorMessage();
        }
      }
    });
  }

  @Override public void onPause() {
    view = null;
  }

  @Override public void onResume(PhotosContract.View view) {
    this.view = view;
  }
}
