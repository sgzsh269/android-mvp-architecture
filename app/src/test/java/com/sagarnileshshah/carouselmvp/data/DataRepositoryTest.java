package com.sagarnileshshah.carouselmvp.data;

import android.content.Context;

import com.sagarnileshshah.carouselmvp.data.local.LocalDataSource;
import com.sagarnileshshah.carouselmvp.data.models.photo.Photo;
import com.sagarnileshshah.carouselmvp.data.remote.RemoteDataSource;
import com.sagarnileshshah.carouselmvp.util.NetworkHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DataRepositoryTest {

    @Mock
    LocalDataSource mockLocalDataSource;

    @Mock
    RemoteDataSource mockRemoteDataSource;

    @Mock
    NetworkHelper mockNetworkHelper;

    @Mock
    Context mockContext;

    @Mock
    DataSource.GetPhotosCallback mockGetPhotosCallback;

    @Captor
    ArgumentCaptor<DataSource.GetPhotosCallback> getPhotosCallbackCaptor;

    private DataRepository dataRepository;

    @Before
    public void setup() {
        dataRepository = DataRepository.getInstance(mockRemoteDataSource, mockLocalDataSource,
                mockNetworkHelper);
    }

    @After
    public void tearDown() {
        dataRepository.destroyInstance();
    }

    @Test
    public void getPhotos_shouldCallRemoteDataSourceAndStoreLocally() {
        int page = 1;
        when(mockNetworkHelper.isNetworkAvailable(mockContext)).thenReturn(true);

        dataRepository.getPhotos(mockContext, page, mockGetPhotosCallback);

        verify(mockRemoteDataSource).getPhotos(eq(page), getPhotosCallbackCaptor.capture());

        List<Photo> photos = new ArrayList<>();
        getPhotosCallbackCaptor.getValue().onSuccess(photos);

        verify(mockLocalDataSource).storePhotos(photos);
        verify(mockGetPhotosCallback).onSuccess(photos);
    }

    @Test
    public void getPhotos_shouldCallLocalDataSource() {
        int page = 1;
        when(mockNetworkHelper.isNetworkAvailable(mockContext)).thenReturn(false);

        dataRepository.getPhotos(mockContext, page, mockGetPhotosCallback);

        verify(mockRemoteDataSource, never()).getPhotos(eq(page),
                getPhotosCallbackCaptor.capture());
        verify(mockLocalDataSource).getPhotos(page, mockGetPhotosCallback);
    }

}
