package gallery.dogs.doggallery.contract;

import gallery.dogs.doggallery.model.pojo.ResObj;

public interface MainContract {

    interface View {
        void showToast(String message);
        void showError(String error);
        void showHomeImages(ResObj resObj);
    }

    interface Calls {
        void getHomeImages();
    }

    //ENDLESS RECYCLERVIEW
    interface OnBottomReachedListener {
        void onBottomReached(int position);
    }
}
