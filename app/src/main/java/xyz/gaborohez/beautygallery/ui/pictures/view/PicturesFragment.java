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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import xyz.gaborohez.beautygallery.adapter.PictureAdapter;
import xyz.gaborohez.beautygallery.base.BaseFragment;
import xyz.gaborohez.beautygallery.databinding.FragmentPicturesBinding;
import xyz.gaborohez.beautygallery.ui.pictures.presenter.PicturesContract;
import xyz.gaborohez.beautygallery.ui.pictures.presenter.PicturesPresenter;
import xyz.gaborohez.beautygallery.viewmodel.GalleryViewModel;

public class PicturesFragment extends BaseFragment<PicturesContract.Presenter, FragmentPicturesBinding> implements PicturesContract.View {

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

        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

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
    }

    private void loadImages() {
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(binding.recycler);  //  look like vertical viewpager

        binding.recycler.setHasFixedSize(true);
        binding.recycler.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        adapter = new PictureAdapter(requireActivity(), viewModel.getPhotos());
        binding.recycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        binding.recycler.scrollToPosition(viewModel.getItemPosition());    //  start recyclerview in item selected
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
}