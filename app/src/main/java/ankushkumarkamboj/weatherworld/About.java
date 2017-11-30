package ankushkumarkamboj.weatherworld;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class About extends AppCompatActivity implements MenuItem.OnMenuItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    SharedPreferences sf;
    DrawerLayout drawer;
    ActionBar actionBar;
    ActionBarDrawerToggle toggle;
    SwipeRefreshLayout swipeRefresh;
    NavigationView anavigationView;
    Menu menu;
    Context context;
    MenuItem menuItem;
    Toolbar toolbar;
    MenuItem aHome, aMap, aAbout, aSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this.getBaseContext();
        sf = PreferenceManager.getDefaultSharedPreferences(this);
        if (sf.getString("theme", "Light").contentEquals("Light")) {
            setTheme(R.style.l);
        } else {
            setTheme(R.style.d);
        }
        setContentView(R.layout.about);
        toolbar = (Toolbar) findViewById(R.id.atoolbar);
        setSupportActionBar(toolbar);
        getSupportFragmentManager().beginTransaction().replace(R.id.afLayout, new Fragment4()).commit();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawer = (DrawerLayout) findViewById(R.id.aLayout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.aRefresh);
        swipeRefresh.setOnRefreshListener(this);
        anavigationView = (NavigationView) findViewById(R.id.anavView);
        anavigationView.setItemIconTintList(null);
        menu = anavigationView.getMenu();
        aHome = menu.findItem(R.id.home);
        aHome.setOnMenuItemClickListener(this);
        aMap = menu.findItem(R.id.map);
        aMap.setOnMenuItemClickListener(this);
        aAbout = menu.findItem(R.id.about);
        aAbout.setOnMenuItemClickListener(this);
        aSetting = menu.findItem(R.id.setting);
        aSetting.setOnMenuItemClickListener(this);
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
                    case R.id.about:
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
    public void onRefresh() {
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return false;
    }
}
