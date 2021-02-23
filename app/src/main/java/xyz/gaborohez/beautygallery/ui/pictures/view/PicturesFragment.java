package xyz.gaborohez.beautygallery.ui.pictures.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.SnapHelper;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import xyz.gaborohez.beautygallery.R;
import xyz.gaborohez.beautygallery.adapter.PictureAdapter;
import xyz.gaborohez.beautygallery.base.BaseFragment;
import xyz.gaborohez.beautygallery.databinding.FragmentPicturesBinding;
import xyz.gaborohez.beautygallery.ui.pictures.presenter.PicturesContract;
import xyz.gaborohez.beautygallery.ui.pictures.presenter.PicturesPresenter;
import xyz.gaborohez.beautygallery.viewmodel.GalleryViewModel;

public class PicturesFragment extends BaseFragment<PicturesContract.Presenter, FragmentPicturesBinding> implements PicturesContract.View, PictureAdapter.PictureIn {

    private static final String TAG = "PicturesFragment";

    private boolean isVisible = true;
    private PictureAdapter adapter;
    private GalleryViewModel viewModel;

    public static PicturesFragment newInstance() {
        PicturesFragment fragment = new PicturesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new PicturesPresenter(this);

        viewModel = new ViewModelProvider(requireActivity()).get(GalleryViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPicturesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadImages();
        hideSystemUI();
    }

    private void loadImages() {
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(binding.recycler);  //  look like vertical viewpager

        binding.recycler.setHasFixedSize(true);
        binding.recycler.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        adapter = new PictureAdapter(requireActivity(), viewModel.getPhotos(), this);
        binding.recycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        binding.recycler.scrollToPosition(viewModel.getItemPosition());    //  start recyclerview in item selected
    }

    private void showHideActionBar() {
        if (isVisible){
            hideSystemUI();
            isVisible = false;
        }else {
            showSystemUI();
            isVisible = true;
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = requireActivity().getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    // Shows the system bars by removing all the flags
    // except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = requireActivity().getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (!isVisible)
            showSystemUI();

        requireActivity().setTheme(R.style.Theme_BeautyGallery);
    }

    @Override
    public void onItemClick(String path) {
        showHideActionBar();
    }
}