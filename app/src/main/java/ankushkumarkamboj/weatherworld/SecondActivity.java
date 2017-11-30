package ankushkumarkamboj.weatherworld;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class SecondActivity extends AppCompatActivity implements MenuItem.OnMenuItemClickListener {
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView snavigationView;
    Menu menu;
    MenuItem smap, sabout, ssetting, shome;
    SharedPreferences sf;
    Toolbar toolbar;
    FrameLayout frameLayout;
    MenuItem menuItem;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_screen);
        context = this.getBaseContext();
        sf = PreferenceManager.getDefaultSharedPreferences(this);
        frameLayout = (FrameLayout) findViewById(R.id.fLayout);
        getFragmentManager().beginTransaction().add(R.id.fLayout, new Fragment7()).commit();
        toolbar =(Toolbar) findViewById(R.id.stoolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawer = (DrawerLayout) findViewById(R.id.dLayout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        snavigationView = (NavigationView) findViewById(R.id.navView);
        snavigationView.setItemIconTintList(null);
        menu = snavigationView.getMenu();
        shome = menu.findItem(R.id.home);
        shome.setOnMenuItemClickListener(this);
        smap = menu.findItem(R.id.map);
        smap.setOnMenuItemClickListener(this);
        sabout = menu.findItem(R.id.about);
        sabout.setOnMenuItemClickListener(this);
        ssetting = menu.findItem(R.id.setting);
        ssetting.setOnMenuItemClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        drawer.closeDrawer(GravityCompat.START);
        this.menuItem = item;
        CountDownTimer countDownTimer = new CountDownTimer(300,100) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(context, MainActivity.class));
                        break;
                    case R.id.map:
                        startActivity(new Intent(context, MyMap.class));
                        break;
                    case R.id.about:;
                        startActivity(new Intent(context, About.class));
                        break;
                    case R.id.setting:
                        startActivity(new Intent(context, SecondActivity.class));
                        break;
                }
            }
        };
        countDownTimer.start();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return false;
    }
}
