package xyz.gaborohez.beautygallery.ui.pictures.presenter;

import xyz.gaborohez.beautygallery.base.BasePresenter;
import xyz.gaborohez.beautygallery.ui.pictures.interactor.PicturesInteractor;

public class PicturesPresenter extends BasePresenter<PicturesContract.View> implements PicturesContract.Presenter {

    private PicturesContract.Interactor interactor;

    public PicturesPresenter(PicturesContract.View view) {
        super(view);
        interactor = new PicturesInteractor();
    }
}
