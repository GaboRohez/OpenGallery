package xyz.gaborohez.beautygallery.ui.folder.presenter;

import android.util.Log;

import java.util.List;

import io.reactivex.Observable;
import xyz.gaborohez.beautygallery.base.BasePresenter;
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
                .flatMapSingle(Observable::toList) //.flatMapSingle(g -> g.toList())
                .subscribe(group -> {
                    System.out.println("folders: "+group);
                    view.addFolder(group.get(0));
                    //folders.add(group.get(0));
                    //adapter.notifyDataSetChanged();
                }, throwable -> {
                    Log.d(TAG, "getFolders: "+throwable.getMessage());
                }));
    }
}
