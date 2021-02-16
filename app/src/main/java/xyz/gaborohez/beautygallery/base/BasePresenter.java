package xyz.gaborohez.beautygallery.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BasePresenter<T> {

    protected T view = null;
    private BaseView baseView;
    private CompositeDisposable compositeDisposable;

    protected boolean isVisibleAttached(){
        return view != null;
    }

    public BasePresenter(T view){
        this.view = view;
        compositeDisposable = new CompositeDisposable();
    }

    protected void addSubscription(Disposable disposable){
        compositeDisposable.add(disposable);
    }

    void detachView(){
        this.view = view;
        compositeDisposable.clear();
    }
}
