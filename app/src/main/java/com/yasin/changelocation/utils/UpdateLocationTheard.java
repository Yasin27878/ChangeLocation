package com.yasin.changelocation.utils;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import com.yasin.changelocation.bean.Address;

import java.util.ArrayList;
import java.util.List;

/**
 * Creator  : Yasin on 2016/11/5.
 * Email    : Yasin27878@163.com
 * Desc     : 更新位置的线程
 * Impl Step:
 */
public class UpdateLocationTheard extends Thread {
    private static final String TAG = UpdateLocationTheard.class.getSimpleName();
    //
    public static boolean isRuning = false;
    //
    public float mCurrBearing = 0f;
    public List<Address> mAddressList = new ArrayList<>();
    public Context mContent;
    public Address mAddress;


    @Override
    public void run() {
        Log.d(TAG, "run: " + UpdateLocationTheard.class.getSimpleName() + "is running");

        isRuning = true;

        LocationManager lm = (LocationManager) mContent.getSystemService(Context.LOCATION_SERVICE);
        lm.addTestProvider(LocationManager.GPS_PROVIDER, false, false, false, false, false, true, true, 1, 1);
        lm.setTestProviderEnabled(LocationManager.GPS_PROVIDER, true);

        while (isRuning) {
            Location loc = new Location(LocationManager.GPS_PROVIDER);
            loc.setTime(System.currentTimeMillis());
            loc.setLongitude(mAddress.getLongitude());
            loc.setLatitude(mAddress.getLatitude());
            mCurrBearing = Math.nextAfter()
            loc.setBearing();

        }

    }
}
