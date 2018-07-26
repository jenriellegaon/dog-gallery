package gallery.dogs.doggallery.view;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import gallery.dogs.doggallery.R;
import gallery.dogs.doggallery.contract.MainContract;
import gallery.dogs.doggallery.model.pojo.ResObj;
import gallery.dogs.doggallery.presenter.listeners.AboutDialog;

public class MainView extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.nav_view_left)
    NavigationView navigationViewLeft;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view);

        //Bind Views
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle;
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationViewLeft.setNavigationItemSelectedListener(this);
        onNavigationItemSelected(navigationViewLeft.getMenu().findItem(R.id.nav_home));
        navigationViewLeft.setCheckedItem(R.id.nav_home);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {

        } else if (id == R.id.nav_browse) {

        } else if (id == R.id.nav_about) {

            //displays about dialog
            new AboutDialog(MainView.this);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

    }
}
