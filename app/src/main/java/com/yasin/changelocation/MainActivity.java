package com.yasin.changelocation;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String LOCOTION_ADDRESS = "北京市海淀区苏州街 ";
    public static final double LOCOTION_LONGITUDE = 116.306169;
    public static final double LOCATION_LATITUDE = 39.976694;
    private Location mCurLocation = null;
    private LocationManager mLocManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mCurLocation = getLocation(this);
        setLocation();
        try {
            mLocManager.removeTestProvider(LocationManager.GPS_PROVIDER);
            mLocManager.removeTestProvider(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mLocManager.addTestProvider(LocationManager.GPS_PROVIDER, true, false, false, false, false, false, false, 1, 1);
        mLocManager.setTestProviderEnabled(LocationManager.GPS_PROVIDER, true);
        mLocManager.setTestProviderLocation(LocationManager.GPS_PROVIDER, mCurLocation);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    /**
     * 为Location设置值
     */
    private void setLocation() {
        if (mCurLocation != null) {
            mCurLocation.setLongitude(LOCOTION_LONGITUDE);
            mCurLocation.setLatitude(LOCATION_LATITUDE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * @param context
     * @return
     */
    public Location getLocation(Context context) {
        mLocManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Location location = mLocManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Log.d(TAG, "getLocation: " + LocationManager.GPS_PROVIDER);
        if (location == null) {
            location = mLocManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Log.d(TAG, "getLocation: " + LocationManager.NETWORK_PROVIDER);
        } else if (location == null) {
            location = mLocManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            Log.d(TAG, "getLocation: " + LocationManager.PASSIVE_PROVIDER);
        }
        return location;
    }


}
