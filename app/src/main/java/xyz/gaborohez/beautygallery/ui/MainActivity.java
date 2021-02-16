package xyz.gaborohez.beautygallery.ui;

import android.os.Bundle;

import xyz.gaborohez.beautygallery.R;
import xyz.gaborohez.beautygallery.base.BaseActivity;
import xyz.gaborohez.beautygallery.base.BasePresenter;
import xyz.gaborohez.beautygallery.databinding.ActivityMainBinding;
import xyz.gaborohez.beautygallery.ui.folder.view.FolderFragment;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new FolderFragment(), R.id.contentFragment);
    }
}