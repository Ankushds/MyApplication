package ankushkamboj.sevicebiinder;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    MyService serviceBinder;
    Intent i;
    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder binder) {
            serviceBinder = ((MyService.MyBinder)binder).getService();
            try {
                URL urls[] = new URL[]{new URL("url-1"),new URL("url-2"),new URL("url-3"),new URL("url-4")};
               serviceBinder.urls = urls;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            startService(i);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            serviceBinder = null;

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

    }
    public void startService(View v){
        i = new Intent(getBaseContext(),MyService.class);
        bindService(i,serviceConnection, Context.BIND_AUTO_CREATE);

    }


}
