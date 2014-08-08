package tk.woppo.sunday.receiver;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

import tk.woppo.sunday.App;
import tk.woppo.sunday.service.WeatherUpdateService_;
import tk.woppo.sunday.util.LogUtil;

/**
 * Created by Ho on 2014/7/9.
 */
public class BootBroadcastReceiver extends BroadcastReceiver {

    protected final static String TAG = "BootBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        LogUtil.i(TAG, intent.getAction());

        if (intent.getAction().equals(Intent.ACTION_USER_PRESENT)) {
            // 用户唤醒设备时启动
            WeatherUpdateService_.intent(context).start();
        } else if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            // 开机时
            WeatherUpdateService_.intent(context).start();
        }

    }

    private boolean isServiceDie() {
        ActivityManager myManager = (ActivityManager) App.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) myManager.getRunningServices(30);
        for (int i = 0; i < runningService.size(); i++) {
            if (runningService.get(i).service.getClassName().toString().equals("com.android.controlAddFunctions.PhoneService")) {
                return true;
            }
        }
        return false;
    }
}
