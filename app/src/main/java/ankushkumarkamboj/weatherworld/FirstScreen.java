package ankushkumarkamboj.weatherworld;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;

import java.io.IOException;
import java.util.List;

public class FirstScreen extends AppCompatActivity implements LocationListener {
    Geocoder geocoder;
    List<Address> list;
    SharedPreferences sf;
    LocationManager lm;
    Context context;
    ConnectivityInfo connectivityInfo;
    MyDatabase mdb;
    String str0, a0, a1;
    double d0, d1;
    private static final String[] permission = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    private static final int INITIAL_REQUEST = 7882;
    MyAlertDialog dialog, dialog1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this.getBaseContext();
        sf = PreferenceManager.getDefaultSharedPreferences(this);
        PreferenceManager.setDefaultValues(context, R.xml.my_prefrences, true);
        setContentView(R.layout.first_screen);
        setAnimations();
        MobileAds.initialize(context, "ca-app-pub-9200733326204012~2632042488");
        lm = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        connectivityInfo = new ConnectivityInfo(context);
        startAction();
    }

    private void setAnimations() {
        RotateAnimation rotate = new RotateAnimation(0,540, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(3000);
        rotate.setInterpolator(new LinearInterpolator());
        findViewById(R.id.containerFScreen).startAnimation(rotate);
    }

    public void startAction() {
        new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                if (connectivityInfo.getMyNetworkInfo()) {
                    if (lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                        if (ContextCompat.checkSelfPermission(FirstScreen.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                            getLocationForData();
                        } else {
                            requestPermissions(permission, INITIAL_REQUEST);
                        }
                    } else {
                        setProvider();
                    }
                } else {
                    Toast.makeText(context, "No internet access", Toast.LENGTH_SHORT).show();
                }
            }
        }.start();
    }

    private void getLocationForData() {
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 100, FirstScreen.this);
    }

    private void setProvider() {
        dialog = new MyAlertDialog(FirstScreen.this);
        dialog.setTitle("!Notification");
        dialog.setMessage("Gps seems to be Disabled enable to fetch the Location!");
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Enable", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "NotNow", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                finishAffinity();
            }
        });
        dialog.create();
        dialog.show();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 100, FirstScreen.this);
        } else {
            requestPermissions(permission, INITIAL_REQUEST);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        lm.removeUpdates(FirstScreen.this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 100, FirstScreen.this);
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLocationChanged(Location locationn) {
        if (locationn != null) {
            d0 = locationn.getLatitude();
            d1 = locationn.getLongitude();
            geocoder = new Geocoder(context);
            try {
                Address ad0 = geocoder.getFromLocation(d0, d1, 2).get(0);
                str0 = ad0.getLocality();
                a0 = ad0.getAddressLine(0);
                a1 = ad0.getAddressLine(1);
                mdb = new MyDatabase(context);
                mdb.addData(str0, a0 + "," + a1, d0, d1);
                sf.edit().putString("0", str0).apply();
                sf.edit().putString("a", String.valueOf(d0)).apply();
                sf.edit().putString("m", String.valueOf(d1)).apply();
                Intent intent = new Intent(FirstScreen.this, MainActivity.class);
                startActivity(intent);
            } catch (IOException e) {
                Toast.makeText(context, "There is Network Error", Toast.LENGTH_LONG).show();

            }
        } else {
            Toast.makeText(context, "There is Network Error", Toast.LENGTH_SHORT).show();
            dialog1 = new MyAlertDialog(FirstScreen.this);
            dialog1.setTitle("!Error");
            dialog1.setMessage("There is Network Error,Are you wants to close application...");
            dialog1.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog1.cancel();
                    finishAffinity();
                }
            });
            dialog1.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog1.cancel();
                }
            });
            dialog1.create();
            dialog1.show();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private class MyAlertDialog extends AlertDialog {
        MyAlertDialog(@NonNull Context context) {
            super(context);
        }
    }
}
