package xyz.gaborohez.beautygallery.base;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public abstract class BaseActivity<P> extends AppCompatActivity implements BaseView {

    protected P presenter;


    @Override
    protected void onDestroy() {
        if (presenter != null){
            ((BasePresenter) presenter).detachView();
        }
        super.onDestroy();
    }

    @Override
    public void showLoader(boolean visible) {

    }

    protected void addFragment(Fragment fragment, int id) {
        getSupportFragmentManager().beginTransaction()
                .add(id, fragment, fragment.getClass().getName())
                .addToBackStack(fragment.getClass().getName())
                .commitAllowingStateLoss();
    }


    protected void replaceFragment(Fragment fragment, int id) {
        getSupportFragmentManager().beginTransaction()
                .replace(id, fragment, fragment.getClass().getName())
                .commit();
    }
}
