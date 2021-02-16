package xyz.gaborohez.beautygallery.ui.folder.interactor;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import xyz.gaborohez.beautygallery.ui.folder.presenter.FolderContract;

public class FolderInteractor implements FolderContract.Interactor {

    @Override
    public Observable<String> getFolders(List<String> images) {
        return Observable.fromIterable(images)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
