package xyz.gaborohez.beautygallery.ui.folder.presenter;

import android.util.Log;

import java.util.List;

import io.reactivex.Observable;
import xyz.gaborohez.beautygallery.base.BasePresenter;
import xyz.gaborohez.beautygallery.data.FolderPOJO;
import xyz.gaborohez.beautygallery.ui.folder.interactor.FolderInteractor;

public class FolderPresenter extends BasePresenter<FolderContract.View> implements FolderContract.Presenter {

    private static final String TAG = "FolderPresenter";

    private FolderContract.Interactor interactor;

    public FolderPresenter(FolderContract.View view) {
        super(view);
        this.interactor = new FolderInteractor();
    }

    @Override
    public void getFolders(List<String> images) {
        addSubscription(interactor.getFolders(images)
                .groupBy(r -> {
                    String[] path = r.split("/");
                    return path[path.length-2];
                })
                .flatMapSingle(Observable::toList)
                .subscribe(group -> {
                    System.out.println("folders: "+group);
                    view.addFolder(new FolderPOJO(group.get(0), group.size()), group);
                }, throwable -> {
                    Log.d(TAG, "getFolders: "+throwable.getMessage());
                }));
    }
}
