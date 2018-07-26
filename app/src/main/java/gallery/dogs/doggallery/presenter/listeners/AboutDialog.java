package gallery.dogs.doggallery.presenter.listeners;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

import gallery.dogs.doggallery.R;

public class AboutDialog {

    public AboutDialog(final Context context) {

        boolean wrapInScrollView = true;
        new MaterialDialog.Builder(context)
                .title("")
                .customView(R.layout.content_about, wrapInScrollView)
                .show();
    }

}