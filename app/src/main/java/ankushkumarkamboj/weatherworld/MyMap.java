package ankushkumarkamboj.weatherworld;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;

import static ankushkumarkamboj.weatherworld.R.id.mapContent;

public class MyMap extends AppCompatActivity implements OnMapReadyCallback, MenuItem.OnMenuItemClickListener {
    MapFragment mapFragment;
    GoogleMap gMap;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Menu menu;
    SharedPreferences sf;
    ActionBarDrawerToggle toggle;
    LocationManager lm;
    Toolbar toolbar;
    Geocoder geocoder;
    LatLng latLng;
    android.support.v7.app.ActionBar actionBar;
    FrameLayout frameLayout;
    int k;
    Context context;
    MenuItem menuItem;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        context = this.getBaseContext();
        sf = PreferenceManager.getDefaultSharedPreferences(this);
        if (sf.getString("theme", "Light").contentEquals("Light")) {
            setTheme(R.style.l);
        } else {
            setTheme(R.style.d);
        }
        setContentView(R.layout.my_map_drawer);
        toolbar = (Toolbar) findViewById(R.id.maptoolbar);
        setSupportActionBar(toolbar);
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        frameLayout = (FrameLayout) findViewById(R.id.fLay);
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerLayout = (DrawerLayout) findViewById(R.id.dLay);
        toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        drawerLayout.addDrawerListener(toggle);
        navigationView = (NavigationView) findViewById(R.id.navLay);
        navigationView.setItemIconTintList(null);
        menu = navigationView.getMenu();
        menu.findItem(R.id.hMap).setOnMenuItemClickListener(this);
        menu.findItem(R.id.about).setOnMenuItemClickListener(this);
        menu.findItem(R.id.settingMap).setOnMenuItemClickListener(this);
        menu.findItem(R.id.mMap).setOnMenuItemClickListener(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.gMap = googleMap;
        geocoder = new Geocoder(this);
        IconGenerator iconGenerator = new IconGenerator(getBaseContext());
        iconGenerator.setBackground(getBaseContext().getDrawable(R.drawable.my));
        View view = this.getCardView();
        TextView textView = (TextView) view.findViewById(R.id.mapTitle);
        TextView textView1 = (TextView) view.findViewById(mapContent);
        textView.setText(sf.getString("0", ""));
        textView1.setText(sf.getString("mytemp0" + 0, "") + "/" + sf.getString("mytemp1" + 0, ""));
        double d0 = Double.valueOf(String.valueOf(sf.getString("a", "")));
        double d1 = Double.valueOf(String.valueOf(sf.getString("m", "")));
        latLng = new LatLng(d0, d1);
        iconGenerator.setContentView(view);
        Bitmap bmp = iconGenerator.makeIcon();
        Marker marker = gMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromBitmap(bmp)).alpha((float) .80));
        marker.setFlat(true);
        gMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        if (!(sf.getString("1", "").contentEquals(""))) {
            double d2 = Double.valueOf(String.valueOf(sf.getString("11", "")));
            double d3 = Double.valueOf(String.valueOf(sf.getString("111", "")));
            View view1 = this.getCardView();
            TextView textView2 = (TextView) view1.findViewById(R.id.mapTitle);
            TextView textView3 = (TextView) view1.findViewById(mapContent);
            textView2.setText(sf.getString("1", ""));
            textView3.setText(sf.getString("mytemp0" + 1, "") + "/" + sf.getString("mytemp1" + 1, ""));
            iconGenerator.setContentView(view1);
            Bitmap bmp1 = iconGenerator.makeIcon();
            gMap.addMarker(new MarkerOptions().position(new LatLng(d2, d3)).icon(BitmapDescriptorFactory.fromBitmap(bmp1)).alpha((float) 0.80));

        }
        if (!(sf.getString("2", "").contentEquals(""))) {

            double d4 = Double.valueOf(String.valueOf(sf.getString("22", "")));
            double d5 = Double.valueOf(String.valueOf(sf.getString("222", "")));
            View view2 = this.getCardView();
            TextView textView4 = (TextView) view2.findViewById(R.id.mapTitle);
            TextView textView5 = (TextView) view2.findViewById(mapContent);
            textView4.setText(sf.getString("2", ""));
            textView5.setText(sf.getString("mytemp0" + 2, "") + "/" + sf.getString("mytemp1" + 2, ""));
            iconGenerator.setContentView(view2);
            Bitmap bmp2 = iconGenerator.makeIcon();
            gMap.addMarker(new MarkerOptions().position(new LatLng(d4, d5)).icon(BitmapDescriptorFactory.fromBitmap(bmp2)).alpha((float) 0.80));

        }
        if (!(sf.getString("3", "").contentEquals("a") || sf.getString("3", "").contentEquals(""))) {

            double d6 = Double.valueOf(String.valueOf(sf.getString("33", "")));
            double d7 = Double.valueOf(String.valueOf(sf.getString("333", "")));
            View view3 = this.getCardView();
            TextView textView6 = (TextView) view3.findViewById(R.id.mapTitle);
            TextView textView7 = (TextView) view3.findViewById(mapContent);
            textView6.setText(sf.getString("3", ""));
            textView7.setText(sf.getString("mytemp0" + 3, "") + "/" + sf.getString("mytemp1" + 3, ""));
            iconGenerator.setContentView(view3);
            Bitmap bmp3 = iconGenerator.makeIcon();
            gMap.addMarker(new MarkerOptions().position(new LatLng(d6, d7)).icon(BitmapDescriptorFactory.fromBitmap(bmp3)).alpha((float) 0.80));

        }
        if (!(sf.getString("4", "").contentEquals("a") || sf.getString("4", "").contentEquals(""))) {

            double d8 = Double.valueOf(String.valueOf(sf.getString("44", "")));
            double d9 = Double.valueOf(String.valueOf(sf.getString("444", "")));
            View view4 = this.getCardView();
            TextView textView8 = (TextView) view4.findViewById(R.id.mapTitle);
            TextView textView9 = (TextView) view4.findViewById(mapContent);
            textView8.setText(sf.getString("4", ""));
            textView9.setText(sf.getString("mytemp0" + 4, "") + "/" + sf.getString("mytemp1" + 4, ""));
            iconGenerator.setContentView(view4);
            Bitmap bmp4 = iconGenerator.makeIcon();
            gMap.addMarker(new MarkerOptions().position(new LatLng(d8, d9)).icon(BitmapDescriptorFactory.fromBitmap(bmp4)).alpha((float) 0.80));

        }
        if (!(sf.getString("5", "").contentEquals("a") || sf.getString("5", "").contentEquals(""))) {

            double d10 = Double.valueOf(String.valueOf(sf.getString("55", "")));
            double d11 = Double.valueOf(String.valueOf(sf.getString("555", "")));
            View view5 = this.getCardView();
            TextView textView10 = (TextView) view5.findViewById(R.id.mapTitle);
            TextView textView11 = (TextView) view5.findViewById(mapContent);
            textView10.setText(sf.getString("5", ""));
            textView11.setText(sf.getString("mytemp0" + 5, "") + "/" + sf.getString("mytemp1" + 5, ""));
            iconGenerator.setContentView(view5);
            Bitmap bmp5 = iconGenerator.makeIcon();
            gMap.addMarker(new MarkerOptions().position(new LatLng(d10, d11)).icon(BitmapDescriptorFactory.fromBitmap(bmp5)).alpha((float) 0.80));

        }
        gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                startActivity(new Intent(getBaseContext(), MainActivity.class));
                return true;
            }
        });
    }

    public View getCardView() {
        return getLayoutInflater().from(getBaseContext()).inflate(R.layout.info_window, null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.map_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.satView:
                this.gMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.simpleMap:
                this.gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.traffic:
                gMap.setTrafficEnabled(true);
                break;
            case R.id.hybrid:
                gMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case R.id.terrain:
                gMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            default:
                super.onOptionsItemSelected(item);
        }
        return true;

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        this.menuItem = item;
        CountDownTimer countDownTimer = new CountDownTimer(300, 100) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                switch (menuItem.getItemId()) {
                    case R.id.hMap:
                        startActivity(new Intent(context, MainActivity.class));
                        break;
                    case R.id.about:
                        startActivity(new Intent(context, About.class));
                        break;
                    case R.id.settingMap:
                        startActivity(new Intent(context, SecondActivity.class));
                        break;
                    case R.id.mMap:
                        startActivity(new Intent(context, MyMap.class));
                        break;
                }
            }
        };
        countDownTimer.start();

        return true;
    }

}
