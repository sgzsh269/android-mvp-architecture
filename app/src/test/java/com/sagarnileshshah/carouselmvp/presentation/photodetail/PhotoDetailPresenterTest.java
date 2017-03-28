package com.sagarnileshshah.carouselmvp.presentation.photodetail;

import com.sagarnileshshah.carouselmvp.data.DataRepository;
import com.sagarnileshshah.carouselmvp.data.DataRepositoryTest;
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

    @Captor
    private ArgumentCaptor<DataSource.GetCommentsCallback> getCommentsCallbackCaptor;

    private PhotoDetailPresenter photoDetailPresenter;
    private Photo photo;

    @Before
    public void setup(){

        photoDetailPresenter = new PhotoDetailPresenter(mockView, mockDataRepository, mockThreadExecutor, mockMainUiThread);
        photo = new Photo();

    }

    @Test
    public void getComments_testWithActiveView() {

        photoDetailPresenter.getComments(photo);

        verify(mockView).showProgressBar();
        verify(mockDataRepository).getComments(eq(mockView.getContext()), eq(photo), getCommentsCallbackCaptor.capture());

        DataSource.GetCommentsCallback getCommentsCallback = getCommentsCallbackCaptor.getValue();

        List<Comment> comments = new ArrayList<>();
        photoDetailPresenter.onResume(mockView);
        getCommentsCallback.onSuccess(comments);
        verify(mockView).showComments(comments);
        verify(mockView).hideProgressBar();
    }

    @Test
    public void getComments_testWithNonActiveView() {

        photoDetailPresenter.getComments(photo);

        verify(mockView).showProgressBar();
        verify(mockDataRepository).getComments(eq(mockView.getContext()), eq(photo), getCommentsCallbackCaptor.capture());

        DataSource.GetCommentsCallback getCommentsCallback = getCommentsCallbackCaptor.getValue();

        List<Comment> comments = new ArrayList<>();
        photoDetailPresenter.onPause();
        getCommentsCallback.onSuccess(comments);
        verify(mockView, never()).showComments(comments);
        verify(mockView, never()).hideProgressBar();
    }
}
