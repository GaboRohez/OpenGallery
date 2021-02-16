package xyz.gaborohez.beautygallery.ui.carousel.presenter;

import xyz.gaborohez.beautygallery.base.BasePresenter;
import xyz.gaborohez.beautygallery.ui.carousel.interactor.CarouselInteractor;

public class CarouselPresenter  extends BasePresenter<CarouselContract.View> implements CarouselContract.Presenter{

    private CarouselContract.Interactor interactor;

    public CarouselPresenter(CarouselContract.View view) {
        super(view);
        interactor = new CarouselInteractor();
    }
}
