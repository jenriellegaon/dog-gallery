package gallery.dogs.doggallery.view;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import gallery.dogs.doggallery.R;
import gallery.dogs.doggallery.contract.MainContract;
import gallery.dogs.doggallery.model.pojo.ResObj;
import gallery.dogs.doggallery.presenter.adapters.HomeAdapter;
import gallery.dogs.doggallery.presenter.listeners.AboutDialog;
import gallery.dogs.doggallery.presenter.presenter.MainPresenter;

public class MainView extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.nav_view_left)
    NavigationView navigationViewLeft;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.swipy)
    SwipyRefreshLayout swipy;

    @BindView(R.id.rv)
    RecyclerView rv;

    HomeAdapter homeAdapter;
    MainPresenter presenter;

    public static List<String> imagesList = new ArrayList<>();
    StaggeredGridLayoutManager staggeredGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view);

        //Bind Views
        ButterKnife.bind(this);

        //Initialize Views
        initViews();

        //Initialize MVP
        presenter = new MainPresenter(this);

    }

    @Override
    public void initViews() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle;
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationViewLeft.setNavigationItemSelectedListener(this);
        onNavigationItemSelected(navigationViewLeft.getMenu().findItem(R.id.nav_home));
        navigationViewLeft.setCheckedItem(R.id.nav_home);

        staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        rv.setLayoutManager(staggeredGridLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setHasFixedSize(true);

        homeAdapter = new HomeAdapter(imagesList, rv, MainView.this);
        rv.setAdapter(homeAdapter);
        homeAdapter.notifyDataSetChanged();

        swipy.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {

                //Clear list then initiate presenter
                //Enable footer = enable loading more images
                imagesList.clear();
                presenter.getHomeImages();
                homeAdapter.enableFooter(true);
            }
        });

        homeAdapter.setOnBottomReachedListener(new MainContract.OnBottomReachedListener() {
            @Override
            public void onBottomReached(int position) {

                //Load more set of images
                presenter.getHomeImages();
                homeAdapter.enableFooter(true);
            }
        });


    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            presenter = new MainPresenter(this);
            presenter.getHomeImages();
            showToast("HOME");
        } else if (id == R.id.nav_browse) {
            showToast("BROWSE");
        } else if (id == R.id.nav_about) {
            //displays about dialog
            new AboutDialog(MainView.this);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(MainView.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String error) {
        showToast(error);
    }

    @Override
    public void showHomeImages(ResObj resObj) {

        Log.d("RESULT", resObj.getStatus());

        if (resObj.getStatus().equals("success")) {

            imagesList.addAll(resObj.getMessage());
            homeAdapter.notifyDataSetChanged();
            swipy.setRefreshing(false);

            Log.d("URL ADDED", resObj.getMessage().toString());
        } else {
            Log.d("RESPONSE ERROR! ", "Response error");
            homeAdapter.enableFooter(false);
        }

    }


}
