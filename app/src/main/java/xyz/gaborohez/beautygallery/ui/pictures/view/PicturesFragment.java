package xyz.gaborohez.beautygallery.ui.pictures.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.SnapHelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import xyz.gaborohez.beautygallery.R;
import xyz.gaborohez.beautygallery.adapter.GalleryAdapter;
import xyz.gaborohez.beautygallery.adapter.PictureAdapter;
import xyz.gaborohez.beautygallery.base.BaseFragment;
import xyz.gaborohez.beautygallery.databinding.FragmentPicturesBinding;
import xyz.gaborohez.beautygallery.ui.folder.presenter.FolderContract;
import xyz.gaborohez.beautygallery.ui.folder.presenter.FolderPresenter;
import xyz.gaborohez.beautygallery.ui.pictures.presenter.PicturesContract;
import xyz.gaborohez.beautygallery.ui.pictures.presenter.PicturesPresenter;

public class PicturesFragment extends BaseFragment<PicturesContract.Presenter, FragmentPicturesBinding> implements PicturesContract.View {

    private static final String ARG_LIST = "list";
    private static final String ARG_POSITION = "position";

    private int position;
    private List<String> list;
    private PictureAdapter adapter;

    public static PicturesFragment newInstance(List<String> list, int position) {
        PicturesFragment fragment = new PicturesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        args.putStringArrayList(ARG_LIST, (ArrayList<String>) list);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new PicturesPresenter(this);
        if (getArguments() != null) {
            position = getArguments().getInt(ARG_POSITION);
            list = getArguments().getStringArrayList(ARG_LIST);
        }
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
        adapter = new PictureAdapter(requireActivity(), list);
        binding.recycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        binding.recycler.scrollToPosition(position);    //  start recyclerview in item selected
    }
}