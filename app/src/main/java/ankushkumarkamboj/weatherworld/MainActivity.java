package ankushkumarkamboj.weatherworld;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MenuItem.OnMenuItemClickListener {
    DrawerLayout drawer;
    NavigationView nav_view;
    ActionBarDrawerToggle toggle;
    Menu menu;
    MenuItem item0, item1, item2, map, moon, about, setting;
    double sLat, sLon;
    Toolbar toolbar;
    Geocoder geocoder;
    Address sAddress;
    List<Address> addList = null;
    List<Address> list = new ArrayList<>();
    SharedPreferences sf;
    LinearLayout ll;
    EditText et;
    ViewPager viewPager;
    PagerSlidingTabStrip tabStrip;
    ViewPagerAdapter viewPagerAdapter;
    Context context;
    String tab0;
    int tabPosition;
    ProgressDialog pd;
    Handler handler = new Handler();
    String tttt, a0, a1, etText;
    TextView textView;
    ConnectivityInfo cInfo;
    MenuItem menuItem;
    MyDatabase mdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this.getBaseContext();
        sf = PreferenceManager.getDefaultSharedPreferences(context);
        if (sf.getString("theme", "Light").contentEquals("Light")) {
            setTheme(R.style.l);
        } else {
            setTheme(R.style.d);
        }
        setContentView(R.layout.main_screen);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        switch ((int) sf.getLong("zero", 0000)) {
            case 1:
                toolbar.setTitle("Current Weather");
                break;
            case 2:
                toolbar.setTitle("Hourly Weather");
                break;
            case 3:
                toolbar.setTitle("Daily Weather");
                break;
            case 4:
                toolbar.setTitle("Moon Phase");
                break;
            default:
                toolbar.setTitle("Home");
        }
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabStrip);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), context);
        tab0 = sf.getString("0", "");
        viewPagerAdapter.addItemTab(tab0);
        if (!(sf.getString("1", "").contentEquals(""))) {
            viewPagerAdapter.addItemTab(sf.getString("1", ""));
        }
        if (!(sf.getString("2", "").contentEquals(""))) {
            viewPagerAdapter.addItemTab(sf.getString("2", ""));
        }
        if (!(sf.getString("3", "").contentEquals(""))) {
            viewPagerAdapter.addItemTab(sf.getString("3", ""));
        }
        if (!(sf.getString("4", "").contentEquals(""))) {
            viewPagerAdapter.addItemTab(sf.getString("4", ""));
        }
        viewPager.setAdapter(viewPagerAdapter);
        tabStrip.setViewPager(viewPager);
        ll = (LinearLayout) tabStrip.getChildAt(0);
        TextView tvvvv = new TextView(context);
        tvvvv.setText(tab0);
        ll.addView(tvvvv);
        tabStrip.notifyDataSetChanged();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        tabStrip.setOnPageChangeListener(pageChangeListener);
        nav_view = (NavigationView) findViewById(R.id.navigationView);
        nav_view.setItemIconTintList(null);
        mdb = new MyDatabase(context);
        menu = nav_view.getMenu();
        item0 = menu.findItem(R.id.item0);
        item1 = menu.findItem(R.id.item1);
        item2 = menu.findItem(R.id.item2);
        map = menu.findItem(R.id.map);
        moon = menu.findItem(R.id.moon);
        about = menu.findItem(R.id.about);
        setting = menu.findItem(R.id.setting);
        item0.setOnMenuItemClickListener(this);
        item1.setOnMenuItemClickListener(this);
        item2.setOnMenuItemClickListener(this);
        map.setOnMenuItemClickListener(this);
        moon.setOnMenuItemClickListener(this);
        about.setOnMenuItemClickListener(this);
        setting.setOnMenuItemClickListener(this);
    }

    ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            tabPosition = position;
            CountDownTimer countDownTimer = new CountDownTimer(330, 330) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    viewPagerAdapter.notifyDataSetChanged();
                }
            };
            countDownTimer.start();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        if (item.getItemId() == R.id.addCity) {
            if (ll.getChildCount() < 5) {
                MyDialog myDailog = new MyDialog(this);
                myDailog.setTitle("Add Location Manually");
                et = new EditText(this);
                et.setHint("city");
                myDailog.setView(et);
                myDailog.setButton(DialogInterface.BUTTON_POSITIVE, "SEARCH NOW", listener1);
                myDailog.setButton(DialogInterface.BUTTON_NEGATIVE, "CANCEL", listener2);
                myDailog.setCancelable(false);
                myDailog.create();
                myDailog.show();
            } else {

                Toast.makeText(this, "You have reached to maxium no. of Limit", Toast.LENGTH_SHORT).show();
            }
            return true;
        } else {
            TextView tvv = (TextView) ll.getChildAt(tabPosition);
            String sss = (String) tvv.getText();
            if (tabPosition == 0) {
                Toast.makeText(this, "Default Location Couldn't be Delete", Toast.LENGTH_SHORT).show();
            } else {
                if (sss.equals(sf.getString("1", ""))) {
                    sf.edit().remove("1").apply();
                } else if (sss.equals(sf.getString("2", ""))) {
                    sf.edit().remove("2").apply();
                } else if (sss.equals(sf.getString("3", ""))) {
                    sf.edit().remove("3").apply();
                } else if (sss.equals(sf.getString("4", ""))) {
                    sf.edit().remove("4").apply();
                }
                ll.removeViewAt(tabPosition);
                viewPagerAdapter.removeItemTab(tabPosition);
                tabStrip.notifyDataSetChanged();
            }
        }
        return true;
    }

    DialogInterface.OnClickListener listener2 = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
        }
    };

    DialogInterface.OnClickListener
            listener1 = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            etText = et.getText().toString();
            if (etText.length() == 0) {
                Toast.makeText(getBaseContext(), "Enter Something Try Again Later", Toast.LENGTH_LONG).show();
            } else {
                cInfo = new ConnectivityInfo(context);
                if (cInfo.getMyNetworkInfo()) {
                    pd = new ProgressDialog(MainActivity.this);
                    pd.setMessage("Please Wait I am Working....");
                    pd.setCancelable(false);
                    pd.show();
                    textView = new TextView(context);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            geocoder = new Geocoder(context);
                            try {
                                addList = geocoder.getFromLocationName(etText, 5);
                                if (!(addList.size() == 0)) {
                                    sAddress = addList.get(0);
                                    sLat = sAddress.getLatitude();
                                    sLon = sAddress.getLongitude();
                                    tttt = sAddress.getLocality();
                                    a0 = sAddress.getAddressLine(0);
                                    a1 = sAddress.getAddressLine(1);
                                    mdb.addData(tttt, a0 + "," + a1, +sLat, sLon);
                                    textView.setText(tttt);
                                    if (sf.getString("1", "").contentEquals("")) {
                                        sf.edit().putString("1", tttt).apply();
                                        sf.edit().putString("11", String.valueOf(sLat)).apply();
                                        sf.edit().putString("111", String.valueOf(sLon)).apply();
                                    } else if (sf.getString("2", "").contentEquals("")) {
                                        sf.edit().putString("2", tttt).apply();
                                        sf.edit().putString("22", String.valueOf(sLat)).apply();
                                        sf.edit().putString("222", String.valueOf(sLon)).apply();
                                    } else if (sf.getString("3", "").contentEquals("")) {
                                        sf.edit().putString("3", tttt).apply();
                                        sf.edit().putString("33", String.valueOf(sLat)).apply();
                                        sf.edit().putString("333", String.valueOf(sLon)).apply();
                                    } else if (sf.getString("4", "").contentEquals("")) {
                                        sf.edit().putString("4", tttt).apply();
                                        sf.edit().putString("44", String.valueOf(sLat)).apply();
                                        sf.edit().putString("444", String.valueOf(sLon)).apply();
                                    }
                                }
                                Thread.sleep(5000);

                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (addList.size() == 0) {
                                        Toast.makeText(context, "Couldn't Find Location", Toast.LENGTH_LONG).show();
                                        pd.cancel();
                                    } else {
                                        pd.setMessage("updating");
                                        ll.addView(textView);
                                        viewPagerAdapter.addItemTab(tttt);
                                        pd.cancel();
                                        tabStrip.notifyDataSetChanged();
                                        addList.clear();
                                    }
                                }
                            });
                        }
                    }).start();

                } else {
                    Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }
        }
    };

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        drawer.closeDrawer(GravityCompat.START);
        this.menuItem = item;
        CountDownTimer countDownTimer = new CountDownTimer(300, 100) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                switch (menuItem.getItemId()) {
                    case R.id.item0:
                        sf.edit().putLong("zero", 01).apply();
                        toolbar.setTitle("Current Weather");
                        viewPagerAdapter.notifyDataSetChanged();
                        break;
                    case R.id.item1:
                        sf.edit().putLong("zero", 02).apply();
                        toolbar.setTitle("Hourly Weather");
                        viewPagerAdapter.notifyDataSetChanged();
                        break;
                    case R.id.item2:
                        sf.edit().putLong("zero", 03).apply();
                        toolbar.setTitle("Daily Weather");
                        viewPagerAdapter.notifyDataSetChanged();
                        break;
                    case R.id.moon:
                        sf.edit().putLong("zero", 04).apply();
                        toolbar.setTitle("Moon Phase");
                        viewPagerAdapter.notifyDataSetChanged();
                        break;
                    case R.id.map:
                        startActivity(new Intent(context, MyMap.class));
                        break;
                    case R.id.setting:
                        startActivity(new Intent(context, SecondActivity.class));
                        break;
                    case R.id.about:
                        startActivity(new Intent(context, About.class));
                        break;
                }
            }
        };
        countDownTimer.start();
        return true;
    }

    private class MyDialog extends AlertDialog {

        protected MyDialog(Context context) {
            super(context);
        }
    }


}


