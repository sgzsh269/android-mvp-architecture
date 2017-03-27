package com.sagarnileshshah.carouselmvp.presentation.photodetail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.bumptech.glide.Glide;
import com.sagarnileshshah.carouselmvp.R;
import com.sagarnileshshah.carouselmvp.data.DataRepository;
import com.sagarnileshshah.carouselmvp.data.local.LocalDataSource;
import com.sagarnileshshah.carouselmvp.data.models.comment.Comment;
import com.sagarnileshshah.carouselmvp.data.models.photo.Photo;
import com.sagarnileshshah.carouselmvp.data.remote.RemoteDataSource;
import com.sagarnileshshah.carouselmvp.presentation.photos.PhotosPresenter;
import com.sagarnileshshah.carouselmvp.presentation.photos.PhotosRecyclerAdapter;
import com.sagarnileshshah.carouselmvp.util.EndlessRecyclerViewScrollListener;
import com.sagarnileshshah.carouselmvp.util.Properties;
import com.sagarnileshshah.carouselmvp.util.threading.MainUiThread;
import com.sagarnileshshah.carouselmvp.util.threading.ThreadExecutor;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import static com.sagarnileshshah.carouselmvp.util.Properties.PHOTO_URL;

public class PhotoDetailFragment extends Fragment implements PhotoDetailContract.View {

    @BindView(R.id.ivPhoto)
    ImageView ivPhoto;

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.rvComments)
    RecyclerView rvComments;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private CommentsRecyclerAdapter recyclerAdapter;
    private EndlessRecyclerViewScrollListener endlessScrollListener;
    private PhotoDetailContract.Presenter presenter;
    private Photo photo;
    private List<Comment> comments;

    public PhotoDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            photo = Parcels.unwrap(getArguments().getParcelable(Properties.BUNDLE_KEY_PHOTO));
        }
        ThreadExecutor threadExecutor = ThreadExecutor.getInstance();
        MainUiThread mainUiThread = MainUiThread.getInstance();
        RemoteDataSource remoteDataSource = RemoteDataSource.getInstance(mainUiThread, threadExecutor);
        LocalDataSource localDataSource = LocalDataSource.getInstance(mainUiThread, threadExecutor);
        DataRepository dataRepository = DataRepository.getInstance(remoteDataSource, localDataSource);
        presenter = new PhotoDetailPresenter(this, dataRepository, threadExecutor, mainUiThread);
        comments = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerAdapter = new CommentsRecyclerAdapter(comments);
        rvComments.setAdapter(recyclerAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvComments.setLayoutManager(linearLayoutManager);

        showPhoto(photo);
        presenter.getComments(photo.getId());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onPause() {
        presenter.onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume(this);
    }

    @Override
    public void showPhoto(Photo photo) {
        tvTitle.setText(photo.getTitle());
        String photoUrl = String.format(PHOTO_URL, photo.getFarm(), photo.getServer(), photo.getId(), photo.getSecret());
        Glide.with(this).load(photoUrl).into(ivPhoto);
    }

    @Override
    public void showComments(List<Comment> comments) {
        if(comments != null) {
            this.comments.addAll(comments);
            recyclerAdapter.notifyItemRangeInserted(this.comments.size(), comments.size());
        }
    }

    @Override
    public void showErrorMessage() {
        Toast.makeText(getContext(), getResources().getString(R.string.error_msg), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }
}
