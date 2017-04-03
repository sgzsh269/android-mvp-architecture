package com.sagarnileshshah.carouselmvp.ui.photodetail;

import com.sagarnileshshah.carouselmvp.data.DataRepository;
import com.sagarnileshshah.carouselmvp.data.DataSource;
import com.sagarnileshshah.carouselmvp.data.models.comment.Comment;
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
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import android.content.Context;

@RunWith(MockitoJUnitRunner.class)
public class PhotoDetailPresenterTest {

    @Mock
    private PhotoDetailContract.View mockView;

    @Mock
    private DataRepository mockDataRepository;

    @Mock
    private ThreadExecutor mockThreadExecutor;

    @Mock
    private MainUiThread mockMainUiThread;

    @Mock
    private Context mockContext;

    @Captor
    private ArgumentCaptor<DataSource.GetCommentsCallback> getCommentsCallbackCaptor;

    private PhotoDetailPresenter photoDetailPresenter;
    private Photo photo;

    @Before
    public void setup() {

        photoDetailPresenter = new PhotoDetailPresenter(mockView, mockDataRepository,
                mockThreadExecutor, mockMainUiThread);
        photo = new Photo();

    }

    @Test
    public void getComments_testWithActiveView() {

        photoDetailPresenter.getComments(mockContext, photo);

        verify(mockView).setProgressBar(true);
        verify(mockDataRepository).getComments(eq(mockContext), eq(photo),
                getCommentsCallbackCaptor.capture());

        DataSource.GetCommentsCallback getCommentsCallback = getCommentsCallbackCaptor.getValue();

        List<Comment> comments = new ArrayList<>();
        photoDetailPresenter.onViewActive(mockView);
        getCommentsCallback.onSuccess(comments);
        verify(mockView).showComments(comments);
        verify(mockView).setProgressBar(false);
    }

    @Test
    public void getComments_testWithNonActiveView() {

        photoDetailPresenter.getComments(mockContext, photo);

        verify(mockView).setProgressBar(true);
        verify(mockDataRepository).getComments(eq(mockContext), eq(photo),
                getCommentsCallbackCaptor.capture());

        DataSource.GetCommentsCallback getCommentsCallback = getCommentsCallbackCaptor.getValue();

        List<Comment> comments = new ArrayList<>();
        photoDetailPresenter.onViewInactive();
        getCommentsCallback.onSuccess(comments);
        verify(mockView, never()).showComments(comments);
        verify(mockView, never()).setProgressBar(false);
    }
}
