package gallery.dogs.doggallery.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import gallery.dogs.doggallery.R;
import gallery.dogs.doggallery.contract.MainContract;
import gallery.dogs.doggallery.model.pojo.ResObj;
import gallery.dogs.doggallery.model.pojo.ResObjSingle;
import gallery.dogs.doggallery.presenter.presenter.MainPresenter;

public class SecondaryView extends AppCompatActivity implements MainContract.View {

    @BindView(R.id.toolbar2)
    Toolbar toolbar;
    @BindView(R.id.breedSpinner)
    MaterialSpinner spinner;
    @BindView(R.id.dogImage)
    ImageView dogImage;
    @BindView(R.id.btnFetch)
    Button btnFetch;

    String breed;
    MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondary_view);

        //Bind views
        ButterKnife.bind(this);

        //Initialize views
        initViews();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAndRemoveTask();
    }

    @Override
    public void initViews() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAndRemoveTask();
            }
        });


        final List breeds = Arrays.asList(getResources().getStringArray(R.array.breeds));


        breed = "affenpinscher";

        spinner.setAllCaps(true);
        spinner.setItems(getResources().getStringArray(R.array.breeds));
        spinner.setSelectedIndex(0);
        spinner.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {
            @Override
            public void onNothingSelected(MaterialSpinner spinner) {
                breed = breeds.get(0).toString();
                breed = "affenpinscher";
            }
        });

        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                breed = item;
            }
        });

        btnFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (breed.equals(null)) {
                    breed = "affenpinscher";
                }

                presenter = new MainPresenter(SecondaryView.this);
                presenter.getSingleImage(breed);

            }
        });
    }


    @Override
    public void showHomeImages(ResObj resObj) {
    }


    @Override
    public void showToast(String message) {
        Toast.makeText(SecondaryView.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String error) {
        showToast(error);
    }


    @Override
    public void showSingleImage(ResObjSingle resObjSingle) {

        Log.d("RESULT", resObjSingle.getStatus());

        if (resObjSingle.getStatus().equals("success")) {

            final RequestOptions requestOptions = new RequestOptions();

            Glide.with(this)
                    .load(resObjSingle.getMessage())
                    .apply(requestOptions.placeholder(R.drawable.placeholder_white))
                    .apply(requestOptions.error(R.drawable.placeholder_unavailable_white))
                    .apply(requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL))
                    .apply(requestOptions.centerCrop())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            Log.d("FAILED", e.getMessage());

                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            Log.d("GLIDE", "READY");
                            return false;
                        }
                    })
                    .into(dogImage);


            Log.d("URL FETCHED!", resObjSingle.getMessage());
        } else {
            Log.d("RESPONSE ERROR! ", "Response error");

        }

    }
}
