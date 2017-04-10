package com.sagarnileshshah.carouselmvp.ui.photodetail;

import static com.sagarnileshshah.carouselmvp.util.Properties.PHOTO_URL;

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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.sagarnileshshah.carouselmvp.R;
import com.sagarnileshshah.carouselmvp.data.DataRepository;
import com.sagarnileshshah.carouselmvp.data.local.LocalDatabase;
import com.sagarnileshshah.carouselmvp.data.models.comment.Comment;
import com.sagarnileshshah.carouselmvp.data.models.photo.Photo;
import com.sagarnileshshah.carouselmvp.di.Injection;
import com.sagarnileshshah.carouselmvp.util.BaseFragmentInteractionListener;
import com.sagarnileshshah.carouselmvp.util.EndlessRecyclerViewScrollListener;
import com.sagarnileshshah.carouselmvp.util.Properties;
import com.sagarnileshshah.carouselmvp.util.mvp.BaseView;
import com.sagarnileshshah.carouselmvp.util.threading.MainUiThread;
import com.sagarnileshshah.carouselmvp.util.threading.ThreadExecutor;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * The {@link Fragment} that receives photo data from
 * {@link com.sagarnileshshah.carouselmvp.ui.photos.PhotosFragment}
 * via a {@link Bundle} and comment data from its
 * {@link PhotoDetailContract.Presenter}. It then renders the photo and its list of comments.
 */
public class PhotoDetailFragment extends BaseView implements PhotoDetailContract.View {

    @BindView(R.id.ivPhoto)
    ImageView ivPhoto;

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.rvComments)
    RecyclerView rvComments;

    @BindView(R.id.tvPlaceholder)
    TextView tvPlaceholder;

    private CommentsRecyclerAdapter recyclerAdapter;
    private EndlessRecyclerViewScrollListener endlessScrollListener;
    private PhotoDetailContract.Presenter presenter;
    private Photo photo;
    private List<Comment> comments;
    private BaseFragmentInteractionListener fragmentInteractionListener;

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
        DatabaseDefinition databaseDefinition = FlowManager.getDatabase(LocalDatabase.class);
        DataRepository dataRepository = Injection.provideDataRepository(mainUiThread,
                threadExecutor, databaseDefinition);
        presenter = new PhotoDetailPresenter(this, dataRepository, threadExecutor, mainUiThread);
        comments = new ArrayList<>();
        setRetainInstance(true);
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
        rvComments.setNestedScrollingEnabled(true);

        showPhoto(photo);
        presenter.getComments(getContext().getApplicationContext(), photo);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentInteractionListener = (BaseFragmentInteractionListener) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onPause() {
        presenter.onViewInactive();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onViewActive(this);
        fragmentInteractionListener.resetToolBarScroll();
    }

    @Override
    public void showPhoto(Photo photo) {
        tvTitle.setText(photo.getTitle());
        String photoUrl = String.format(PHOTO_URL, photo.getFarm(), photo.getServer(),
                photo.getId(), photo.getSecret());
        Glide.with(this).load(photoUrl).placeholder(R.drawable.drawable_placeholder).error(
                R.drawable.drawable_placeholder).into(ivPhoto);
    }

    @Override
    public void showComments(List<Comment> comments) {
        if (comments != null) {
            recyclerAdapter.addAll(comments);
        }
    }

    @Override
    public void shouldShowPlaceholderText() {
        if (comments.isEmpty()) {
            tvPlaceholder.setVisibility(View.VISIBLE);
        } else {
            tvPlaceholder.setVisibility(View.GONE);
        }
    }


}
