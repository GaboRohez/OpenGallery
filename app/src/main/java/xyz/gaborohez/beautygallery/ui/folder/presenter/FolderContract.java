package xyz.gaborohez.beautygallery.ui.folder.presenter;

import java.util.List;

import io.reactivex.Observable;
import xyz.gaborohez.beautygallery.base.BaseView;

public interface FolderContract {
    interface View extends BaseView {

        void addFolder(String folder);
    }

    interface Presenter {

        void getFolders(List<String> images);
    }

    interface Interactor {

        Observable<String> getFolders(List<String> images);
    }
}
