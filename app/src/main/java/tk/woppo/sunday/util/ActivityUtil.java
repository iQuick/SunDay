package tk.woppo.sunday.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ho on 2014/7/10.
 */
public class ActivityUtil {

    private static List<Activity> mActivityList = new ArrayList<Activity>();

    public static void add(Activity activity) {
        mActivityList.add(activity);
    }

    public static void remove(Activity activity) {
        mActivityList.remove(activity);
    }

    /**
     * finish所有的存活的Activity
     * 并杀死应用进程
     */
    public static void finishKill() {
        finishAll();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * finish所有的存活的Activity
     */
    public static void finishAll() {
        for (Activity activity : mActivityList) {
            activity.finish();
        }
    }
}
