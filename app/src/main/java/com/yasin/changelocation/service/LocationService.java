package com.yasin.changelocation.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.yasin.changelocation.bean.Address;
import com.yasin.changelocation.core.Constants;
import com.yasin.changelocation.utils.UpdateLocationTheard;

import java.util.List;

/**
 * Creator  : Yasin on 2016/11/5.
 * Email    : Yasin27878@163.com
 * Desc     : 更改GPS位置的服务
 * Impl Step:
 */

public class LocationService extends Service {
    private static final String TAG = LocationService.class.getSimpleName();
    //位置更新的频率
    private static final long LOCATION_UPDATE_RATE = 200;
    //LocationService 实例化对象
    public static LocationService mInstance = null;
    public UpdateLocationTheard mTheard = null;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent.getStringExtra("action").equals(Constants.START_SERVICE)) {

            if (mTheard != null) {
                mTheard.isRuning = false;
            }
            mTheard = UpdateLocationTheard.newInstance(this);
            List<Address> mAddressList = intent.getParcelableArrayListExtra(Constants.TAG_ADDRESS_LIST);
            for (Address address : mAddressList) {
                Log.d(TAG, "onStartCommand: " + address.toString());

            }

        }

        return START_STICKY;
    }
}
