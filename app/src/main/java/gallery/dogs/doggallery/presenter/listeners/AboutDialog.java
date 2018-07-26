package gallery.dogs.doggallery.presenter.listeners;

import android.annotation.SuppressLint;
import android.content.Context;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;

import gallery.dogs.doggallery.R;

public class AboutDialog {

    @SuppressLint("ResourceAsColor")
    public AboutDialog(final Context context) {

        boolean wrapInScrollView = true;
        new MaterialDialog.Builder(context)
                .title("About")
                .titleColor(R.color.colorPrimaryDark)
                .titleGravity(GravityEnum.CENTER)
                .customView(R.layout.content_about, wrapInScrollView)
                .show();
    }

}