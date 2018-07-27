package gallery.dogs.doggallery.contract;

import gallery.dogs.doggallery.model.pojo.ResObj;
import gallery.dogs.doggallery.model.pojo.ResObjSingle;

public interface MainContract {

    interface View {
        void showToast(String message);
        void showError(String error);
        void showHomeImages(ResObj resObj);

        void showSingleImage(ResObjSingle resObjSingle);
        void initViews();
    }

    interface Calls {
        void getHomeImages();

        void getSingleImage(String breed);
    }

    //ENDLESS RECYCLERVIEW
    interface OnBottomReachedListener {
        void onBottomReached(int position);
    }
}
