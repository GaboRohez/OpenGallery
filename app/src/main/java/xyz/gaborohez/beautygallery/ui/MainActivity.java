package xyz.gaborohez.beautygallery.ui;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.fragment.app.FragmentManager;

import xyz.gaborohez.beautygallery.R;
import xyz.gaborohez.beautygallery.base.BaseActivity;
import xyz.gaborohez.beautygallery.base.BasePresenter;
import xyz.gaborohez.beautygallery.databinding.ActivityMainBinding;
import xyz.gaborohez.beautygallery.ui.folder.view.FolderFragment;

public class MainActivity extends BaseActivity implements FragmentManager.OnBackStackChangedListener {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setElevation(0);

        //Listen for changes in the back stack
        getSupportFragmentManager().addOnBackStackChangedListener(this);
        //Handle when activity is recreated like on orientation Change
        shouldDisplayHomeUp();

        replaceFragment(new FolderFragment(), R.id.contentFragment);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getSupportFragmentManager().popBackStack();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackStackChanged() {
        shouldDisplayHomeUp();
    }

    public void shouldDisplayHomeUp(){
        //Enable Up button only  if there are entries in the back stack
        boolean canGoBack = getSupportFragmentManager().getBackStackEntryCount()>0;
        if (!canGoBack){
            setTitle(R.string.app_name);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(canGoBack);
    }

    @Override
    public boolean onSupportNavigateUp() {
        //This method is called when the up button is pressed. Just the pop back stack.
        getSupportFragmentManager().popBackStack();
        return true;
    }
}