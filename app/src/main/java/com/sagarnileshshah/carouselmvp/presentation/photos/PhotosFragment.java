package com.sagarnileshshah.carouselmvp.presentation.photos;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.sagarnileshshah.carouselmvp.R;
import com.sagarnileshshah.carouselmvp.data.DataRepository;
import com.sagarnileshshah.carouselmvp.data.local.LocalDataSource;
import com.sagarnileshshah.carouselmvp.data.local.LocalDatabase;
import com.sagarnileshshah.carouselmvp.data.models.photo.Photo;
import com.sagarnileshshah.carouselmvp.data.remote.RemoteDataSource;
import com.sagarnileshshah.carouselmvp.di.Injection;
import com.sagarnileshshah.carouselmvp.presentation.photodetail.PhotoDetailFragment;
import com.sagarnileshshah.carouselmvp.util.BaseFragmentInteractionListener;
import com.sagarnileshshah.carouselmvp.util.FoaBaseActivity;
import com.sagarnileshshah.carouselmvp.util.ItemClickSupport;
import com.sagarnileshshah.carouselmvp.util.Properties;
import com.sagarnileshshah.carouselmvp.util.mvp.BaseView;
import com.sagarnileshshah.carouselmvp.util.threading.MainUiThread;
import com.sagarnileshshah.carouselmvp.util.threading.ThreadExecutor;
import com.sagarnileshshah.carouselmvp.util.EndlessRecyclerViewScrollListener;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class PhotosFragment extends BaseView implements PhotosContract.View {

    @BindView(R.id.rvPhotos)
    RecyclerView rvPhotos;

    private PhotosRecyclerAdapter recyclerAdapter;
    private List<Photo> photos;
    private EndlessRecyclerViewScrollListener endlessScrollListener;
    private PhotosContract.Presenter presenter;
    private boolean isCreated;
    private BaseFragmentInteractionListener fragmentInteractionListener;


    public PhotosFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        photos = new ArrayList<>();
        ThreadExecutor threadExecutor = ThreadExecutor.getInstance();
        MainUiThread mainUiThread = MainUiThread.getInstance();
        DatabaseDefinition databaseDefinition = FlowManager.getDatabase(LocalDatabase.class);
        DataRepository dataRepository = Injection.provideDataRepository(mainUiThread, threadExecutor, databaseDefinition);
        presenter = new PhotosPresenter(this, dataRepository, threadExecutor, mainUiThread);
        isCreated = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photos, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recyclerAdapter = new PhotosRecyclerAdapter(this, photos);
        rvPhotos.setAdapter(recyclerAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvPhotos.setLayoutManager(linearLayoutManager);

        endlessScrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                getPhotos(page);
            }
        };

        rvPhotos.addOnScrollListener(endlessScrollListener);

        ItemClickSupport.addTo(rvPhotos).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Photo photo = photos.get(position);
                Parcelable parcelable = Parcels.wrap(photo);
                Bundle bundle = new Bundle();
                bundle.putParcelable(Properties.BUNDLE_KEY_PHOTO, parcelable);
                fragmentInteractionListener.showFragment(PhotoDetailFragment.class, bundle, true);
            }
        });
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
    public void showPhotos(List<Photo> photos) {
        this.photos.addAll(photos);
        recyclerAdapter.notifyItemRangeInserted(this.photos.size(), photos.size());
    }


    @Override
    public void onResume() {
        super.onResume();
        presenter.onViewActive(this);
        if (isCreated) {
            getPhotos(1);
            isCreated = false;
        }

        fragmentInteractionListener.resetToolBarScroll();
    }

    @Override
    public void onPause() {
        presenter.onViewInactive();
        super.onPause();
    }

    private void getPhotos(int page) {
        presenter.getPhotos(getContext(), page);
    }
}