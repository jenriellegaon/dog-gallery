package gallery.dogs.doggallery.presenter.presenter;

import android.annotation.SuppressLint;
import android.util.Log;

import gallery.dogs.doggallery.contract.MainContract;
import gallery.dogs.doggallery.model.api.APIService;
import gallery.dogs.doggallery.model.api.Client;
import gallery.dogs.doggallery.model.pojo.ResObj;
import gallery.dogs.doggallery.model.pojo.ResObjSingle;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter implements MainContract.Calls {

    private String TAG = "MAIN PRESENTER";
    private MainContract.View view;

    public MainPresenter(MainContract.View view) {
        this.view = view;
    }


    @SuppressLint("CheckResult")
    @Override
    public void getHomeImages() {
        getHomeImagesbservable().subscribeWith(getHomeImagesObserver());
    }

    @SuppressLint("CheckResult")
    @Override
    public void getSingleImage(String breed) {
        getSingleImageObservable(breed).subscribeWith(getSingleImageObserver());
    }


    //OBSERVABLES
    /**********************************************************************************************/
    public Observable<ResObj> getHomeImagesbservable() {
        return Client.getRetrofit().create(APIService.class)
                .getHomeImages()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Observable<ResObjSingle> getSingleImageObservable(String breed) {
        return Client.getRetrofit().create(APIService.class)
                .getDogBreed(breed)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    /**********************************************************************************************/
    //OBSERVABLES


    //OBSERVERS
    /**********************************************************************************************/
    public DisposableObserver<ResObj> getHomeImagesObserver() {
        return new DisposableObserver<ResObj>() {

            @Override
            public void onNext(@NonNull ResObj resObj) {
                view.showHomeImages(resObj);
                dispose();
                getHomeImagesObserver().dispose();
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "Error" + e);
                e.printStackTrace();
                view.showError("Error Fetching Data");
                dispose();
                getHomeImagesObserver().dispose();
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "Completed");
                dispose();
                getHomeImagesObserver().dispose();
            }
        };
    }


    public DisposableObserver<ResObjSingle> getSingleImageObserver() {
        return new DisposableObserver<ResObjSingle>() {

            @Override
            public void onNext(@NonNull ResObjSingle resObjSingle) {
                view.showSingleImage(resObjSingle);
                dispose();
                getSingleImageObserver().dispose();
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "Error" + e);
                e.printStackTrace();
                view.showError("Error Fetching Data");
                dispose();
                getSingleImageObserver().dispose();
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "Completed");
                dispose();
                getSingleImageObserver().dispose();
            }
        };
    }


    /**********************************************************************************************/
    //OBSERVERS


}
