package xyz.gaborohez.beautygallery.ui.carousel.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import xyz.gaborohez.beautygallery.R;
import xyz.gaborohez.beautygallery.adapter.FolderAdapter;
import xyz.gaborohez.beautygallery.adapter.GalleryAdapter;
import xyz.gaborohez.beautygallery.base.BaseFragment;
import xyz.gaborohez.beautygallery.databinding.FragmentCarouselBinding;
import xyz.gaborohez.beautygallery.databinding.FragmentFolderBinding;
import xyz.gaborohez.beautygallery.ui.carousel.presenter.CarouselContract;
import xyz.gaborohez.beautygallery.ui.carousel.presenter.CarouselPresenter;
import xyz.gaborohez.beautygallery.ui.folder.presenter.FolderContract;
import xyz.gaborohez.beautygallery.ui.folder.presenter.FolderPresenter;
import xyz.gaborohez.beautygallery.utils.ImagesGallery;

public class CarouselFragment  extends BaseFragment<CarouselContract.Presenter, FragmentCarouselBinding> implements CarouselContract.View, GalleryAdapter.PhotoListener {

    private static final String TAG = "CarouselFragment";

    private static final String ARG_TITLE = "title";
    private static final String ARG_PATHS = "paths";

    private String title;
    private List<String> paths;
    private GalleryAdapter adapter;

    public static CarouselFragment newInstance(List<String> paths, String title) {
        CarouselFragment fragment = new CarouselFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putStringArrayList(ARG_PATHS, (ArrayList<String>) paths);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new CarouselPresenter(this);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_TITLE);
            paths = getArguments().getStringArrayList(ARG_PATHS);

            getActivity().setTitle(title);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCarouselBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadImages();
    }

    private void loadImages() {
        binding.recycler.setHasFixedSize(true);
        binding.recycler.setLayoutManager(new GridLayoutManager(requireActivity(), 3));
        adapter = new GalleryAdapter(requireActivity(), paths, this);
        binding.recycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPhotoClick(String path) {
        Log.d(TAG, "onPhotoClick: "+path);
    }
}