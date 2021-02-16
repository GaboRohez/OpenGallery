package xyz.gaborohez.beautygallery.base;

import android.os.Bundle;
import android.view.Menu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment<T, B> extends Fragment implements BaseView {

    protected T presenter;
    protected B binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onDestroy() {
        if (binding != null){
            binding = null;
        }

        if (presenter != null){
            ((BasePresenter) presenter).detachView();
        }
        super.onDestroy();
    }

    @Override
    public void showLoader(boolean visible) {
        if (getActivity() instanceof BaseActivity){
            ((BaseActivity) getActivity()).showLoader(visible);
        }
    }

    protected void addFragment(Fragment fragment, int id){
        if (getActivity() instanceof BaseActivity){
            ((BaseActivity) getActivity()).addFragment(fragment, id);
        }
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        menu.clear();
    }
}
