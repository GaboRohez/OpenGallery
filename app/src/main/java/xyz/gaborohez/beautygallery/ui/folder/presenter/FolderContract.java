package xyz.gaborohez.beautygallery.ui.folder.presenter;

import java.util.List;

import io.reactivex.Observable;
import xyz.gaborohez.beautygallery.base.BaseView;
import xyz.gaborohez.beautygallery.data.FolderPOJO;

public interface FolderContract {
    interface View extends BaseView {

        void addFolder(FolderPOJO folder, List<String> imageList);
    }

    interface Presenter {

        void getFolders(List<String> images);
    }

    interface Interactor {

        Observable<String> getFolders(List<String> images);
    }
}
