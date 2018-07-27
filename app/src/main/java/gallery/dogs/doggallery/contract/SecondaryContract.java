package gallery.dogs.doggallery.contract;

import gallery.dogs.doggallery.model.pojo.ResObjSingle;

public interface SecondaryContract {

    interface View {
        void initViews();

        void showToast(String message);

        void showError(String error);

        void showSingleImage(ResObjSingle resObjSingle);
    }

}
