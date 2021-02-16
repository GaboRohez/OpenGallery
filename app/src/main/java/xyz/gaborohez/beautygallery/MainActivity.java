package xyz.gaborohez.beautygallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import xyz.gaborohez.beautygallery.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements FolderAdapter.PhotoListener {

    private static final String TAG = "MainActivity";

    private List<String> images;
    private List<String> folders;
    private ActivityMainBinding binding;
    private final int MY_PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        folders = new ArrayList<>();

        checkLocationPermission();
        File root = new File(Environment.getExternalStorageDirectory().getAbsolutePath());

    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                showAlertDialog();

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
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
        new AlertDialog.Builder(this)
                .setTitle(R.string.title_write_permission)
                .setMessage(String.format(getString(R.string.text_write_permission), getString(R.string.app_name)))
                .setPositiveButton(R.string.ok_message, (dialogInterface, i) -> {
                    //Prompt the user once explanation has been shown
                    ActivityCompat.requestPermissions(MainActivity.this,
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
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
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

        /*binding.recycler.setHasFixedSize(true);
        binding.recycler.setLayoutManager(new GridLayoutManager(this, 4));

        images = ImagesGallery.listOfImages(this);

        GalleryAdapter adapter = new GalleryAdapter(this, images, this);
        binding.recycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();*/

        binding.recycler.setHasFixedSize(true);
        binding.recycler.setLayoutManager(new GridLayoutManager(this, 2));
        FolderAdapter adapter = new FolderAdapter(this, folders, this);
        binding.recycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        images = ImagesGallery.listOfImages(this);

        Observable.fromIterable(images)
                .groupBy(r -> {
                    String[] path = r.split("/");
                    return path[path.length-2];
                })
                .flatMapSingle(Observable::toList) //.flatMapSingle(g -> g.toList())
                .subscribe(group -> {
                    System.out.println("folders: "+group);
                    folders.add(group.get(0));
                    adapter.notifyDataSetChanged();
                });

    }

    @Override
    public void onPhotoClick(String path) {
        Log.d(TAG, "onPhotoClick: "+path);
    }

}