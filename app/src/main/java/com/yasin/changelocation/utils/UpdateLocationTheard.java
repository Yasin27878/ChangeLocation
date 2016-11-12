package com.yasin.changelocation.utils;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import com.yasin.changelocation.bean.Address;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    //方向
    public float mCurrBearing = 0f;
    //速度 in meters/second over ground.
    public float mSpeed = 0.5f;
    public List<Address> mAddressList = new ArrayList<>();
    public Context mContent;
    public Address mAddress;
    //休眠时间
    public long mSleepTimes = 500;
    //线程
    public static UpdateLocationTheard currentThread;

    public UpdateLocationTheard(Context mContent) {
        this.mContent = mContent;
    }

    public static UpdateLocationTheard newInstance(Context mContent) {
        if (currentThread == null) {
            synchronized (UpdateLocationTheard.class) {
                if (currentThread == null) {
                    currentThread = new UpdateLocationTheard(mContent);
                }
            }
        }
        return currentThread;
    }

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
            mCurrBearing = (float) Math.random() * 360;
            loc.setBearing(mCurrBearing);
            loc.setSpeed(mSpeed);
            lm.setTestProviderLocation(LocationManager.GPS_PROVIDER, loc);
            try {
                Thread.sleep(mSleepTimes);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        lm.setTestProviderEnabled(LocationManager.GPS_PROVIDER, false);
        lm.removeTestProvider(LocationManager.GPS_PROVIDER);
        Log.i("UpdateLocationTheard", "Ending UpdateGPSThread");

    }

    /**
     * 获取 范围内的随机数
     *
     * @param start 起始
     * @param end   结束
     * @return
     */
    private Float getRandom(int start, int end) {
        Float f = 0f;
        Random random = new Random();
        f = random.nextFloat() * end + start;
        return f;

    }
}
