package xyz.gaborohez.beautygallery.ui.carousel.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import xyz.gaborohez.beautygallery.R;
import xyz.gaborohez.beautygallery.adapter.GalleryAdapter;
import xyz.gaborohez.beautygallery.base.BaseFragment;
import xyz.gaborohez.beautygallery.databinding.FragmentCarouselBinding;
import xyz.gaborohez.beautygallery.ui.carousel.presenter.CarouselContract;
import xyz.gaborohez.beautygallery.ui.carousel.presenter.CarouselPresenter;
import xyz.gaborohez.beautygallery.ui.pictures.view.PicturesFragment;
import xyz.gaborohez.beautygallery.viewmodel.GalleryViewModel;

public class CarouselFragment  extends BaseFragment<CarouselContract.Presenter, FragmentCarouselBinding> implements CarouselContract.View, GalleryAdapter.PhotoListener {

    private GalleryAdapter adapter;
    private GalleryViewModel viewModel;

    public static CarouselFragment newInstance() {
        CarouselFragment fragment = new CarouselFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new CarouselPresenter(this);

        viewModel = new ViewModelProvider(requireActivity()).get(GalleryViewModel.class);
        getActivity().setTitle(viewModel.getAlbumTitle());
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
        adapter = new GalleryAdapter(requireActivity(), viewModel.getPhotos(), this);
        binding.recycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPhotoClick(int position) {
        addFragment(PicturesFragment.newInstance(viewModel.getPhotos(), position), R.id.contentFragment);
    }
}