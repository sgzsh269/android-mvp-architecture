package com.sagarnileshshah.carouselmvp.presentation.photos;


import com.sagarnileshshah.carouselmvp.data.DataRepository;
import com.sagarnileshshah.carouselmvp.data.DataRepositoryTest;
import com.sagarnileshshah.carouselmvp.data.DataSource;
import com.sagarnileshshah.carouselmvp.data.models.photo.Photo;
import com.sagarnileshshah.carouselmvp.util.threading.MainUiThread;
import com.sagarnileshshah.carouselmvp.util.threading.ThreadExecutor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PhotoPresenterTest {

    @Mock
    private PhotosContract.View mockView;

    @Mock
    private DataRepository mockDataRepository;

    @Mock
    private ThreadExecutor mockThreadExecutor;

    @Mock
    private MainUiThread mockMainUiThread;

    @Captor
    private ArgumentCaptor<DataSource.GetPhotosCallback> getPhotosCallbackCaptor;

    private PhotosPresenter photosPresenter;

    @Before
    public void setup(){

        photosPresenter = new PhotosPresenter(mockView, mockDataRepository, mockThreadExecutor, mockMainUiThread);

    }

    @Test
    public void getPhotos_testWithActiveView() {
        int page = 2;
        photosPresenter.getPhotos(page);

        verify(mockView).showProgressBar();
        verify(mockDataRepository).getPhotos(eq(mockView.getContext()), eq(page), getPhotosCallbackCaptor.capture());

        DataSource.GetPhotosCallback getPhotosCallback = getPhotosCallbackCaptor.getValue();
        List<Photo> photos = new ArrayList<>();

        photosPresenter.onResume(mockView);
        getPhotosCallback.onSuccess(photos);
        verify(mockView).showPhotos(photos);
        verify(mockView).hideProgressBar();
    }

    @Test
    public void getPhotos_testWithNonActiveView() {
        int page = 2;
        photosPresenter.getPhotos(page);

        verify(mockView).showProgressBar();
        verify(mockDataRepository).getPhotos(eq(mockView.getContext()), eq(page), getPhotosCallbackCaptor.capture());

        DataSource.GetPhotosCallback getPhotosCallback = getPhotosCallbackCaptor.getValue();
        List<Photo> photos = new ArrayList<>();

        photosPresenter.onPause();
        getPhotosCallback.onSuccess(photos);
        verify(mockView, never()).showPhotos(photos);
        verify(mockView, never()).hideProgressBar();
    }


}
