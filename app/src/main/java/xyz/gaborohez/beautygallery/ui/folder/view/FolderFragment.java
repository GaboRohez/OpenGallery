package xyz.gaborohez.beautygallery.ui.folder.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import xyz.gaborohez.beautygallery.R;
import xyz.gaborohez.beautygallery.adapter.FolderAdapter;
import xyz.gaborohez.beautygallery.base.BaseFragment;
import xyz.gaborohez.beautygallery.constants.AppConstants;
import xyz.gaborohez.beautygallery.databinding.ActivityMainBinding;
import xyz.gaborohez.beautygallery.databinding.FragmentFolderBinding;
import xyz.gaborohez.beautygallery.ui.MainActivity;
import xyz.gaborohez.beautygallery.ui.folder.presenter.FolderContract;
import xyz.gaborohez.beautygallery.ui.folder.presenter.FolderPresenter;
import xyz.gaborohez.beautygallery.utils.ImagesGallery;

import static xyz.gaborohez.beautygallery.constants.AppConstants.MY_PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE;


public class FolderFragment extends BaseFragment<FolderContract.Presenter, FragmentFolderBinding> implements FolderContract.View, FolderAdapter.PhotoListener {

    private static final String TAG = "FolderFragment";

    private List<String> images;
    private List<String> folders;
    private FolderAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        folders = new ArrayList<>();
        presenter = new FolderPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFolderBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checkStoragePermission();
    }

    public boolean checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(requireActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                showAlertDialog();

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(requireActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE);
            }
            return false;
        } else {
            loadImages();
            return true;
        }
    }

    private void showAlertDialog(){
        new AlertDialog.Builder(requireContext())
                .setTitle(R.string.title_write_permission)
                .setMessage(String.format(getString(R.string.text_write_permission), getString(R.string.app_name)))
                .setPositiveButton(R.string.ok_message, (dialogInterface, i) -> {
                    //Prompt the user once explanation has been shown
                    ActivityCompat.requestPermissions(requireActivity(),
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE);
                })
                .setNegativeButton(R.string.cancel_message, (dialog, which) -> showSnackbar())
                .create()
                .show();
    }

    private void showSnackbar() {
        Snackbar snackbar = Snackbar
                .make(binding.getRoot(), R.string.manually_settings, Snackbar.LENGTH_LONG)
                .setAction(R.string.settings, view -> {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", requireActivity().getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                });

        snackbar.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission is granted. Continue the action or workflow
                    // in your app.
                    loadImages();
                }  else {
                    showAlertDialog();
                }
                return;
        }
    }

    private void loadImages() {

        binding.recycler.setHasFixedSize(true);
        binding.recycler.setLayoutManager(new GridLayoutManager(requireActivity(), 3));
        adapter = new FolderAdapter(requireActivity(), folders, this);
        binding.recycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        images = ImagesGallery.listOfImages(requireActivity());

        if (images != null && !images.isEmpty()){
            binding.tvNoImages.setVisibility(View.GONE);
            presenter.getFolders(images);
        }
    }

    @Override
    public void addFolder(String folder) {
        folders.add(folder);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPhotoClick(String path) {
        Log.d(TAG, "onPhotoClick: "+path);
    }
}