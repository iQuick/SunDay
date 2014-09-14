package tk.woppo.sunday.service;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.widget.RemoteViews;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.androidannotations.annotations.EService;
import org.androidannotations.annotations.UiThread;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import tk.woppo.sunday.App;
import tk.woppo.sunday.Const;
import tk.woppo.sunday.R;
import tk.woppo.sunday.dao.WeatherCurDataHelper;
import tk.woppo.sunday.dao.WeatherTodayDataHelper;
import tk.woppo.sunday.data.GsonRequest;
import tk.woppo.sunday.data.RequestManager;
import tk.woppo.sunday.model.CurWeatherModel;
import tk.woppo.sunday.model.WeatherTodayModel;
import tk.woppo.sunday.receiver.WeatherWidget;
import tk.woppo.sunday.ui.MainActivity_;
import tk.woppo.sunday.util.DateUtil;
import tk.woppo.sunday.util.LogUtil;
import tk.woppo.sunday.util.WeatherUtil;

/**
 * Created by Ho on 2014/7/7.
 */

@EService
public class WeatherUpdateService extends Service {

    /** Action = tk.woppo.sunday.action.update_weather */
    public final static String ACTION_UPDATE_WEATHER = "tk.woppo.sunday.action.UPDATE_WEATHER";
    public final static String ACTION_SWITH_CITY = "tk.woppo.sunday.action.SWITH_CITY";
    public final static String ACTION_NEXT_CITY = "tk.woppo.sunday.action.NEXT_CITY";
    public final static String ACTION_TIME_SET = "android.intent.action.TIME_SET";

    protected final static String TAG = "WeatherUpdateService";

    protected final static int UPDATE_DELAY = 1;
    protected final static int UPDATE_PERIOD = 3600 * 1000;
//    protected final static int UPDATE_PERIOD = 10 * 1000;

    private WeatherTodayModel mWeatherTodayModel;
    private CurWeatherModel mCurWeatherModel;

    private WeatherCurDataHelper mWeatherDB;
    private WeatherTodayDataHelper mTodayDB;

    private RemoteViews remoteView;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mWeatherDB = new WeatherCurDataHelper(this);
        mTodayDB = new WeatherTodayDataHelper(this);

        remoteView = new RemoteViews(getPackageName(), R.layout.widget_4x2);

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                getWeatherFormNet(getNextWeatherCode(App.getCurCityIndex()));
            }
        }, UPDATE_DELAY, UPDATE_PERIOD);

    }

    private String getNextWeatherCode(int index) {
        return App.getMyArea().get(index % App.getMyArea().size()).getWeatherCode();
    }

    private void getWeather(String id) {

        CurWeatherModel weatherModel = mWeatherDB.query(id);
        WeatherTodayModel weatherTodayModel = mTodayDB.query(id);
        if (weatherModel == null || weatherTodayModel == null) {
            getWeatherFormNet(id);
        } else {
            mCurWeatherModel = weatherModel;
            mWeatherTodayModel = weatherTodayModel;
            sendUpdateWeather();
        }
    }

    private void getWeatherFormNet(String id) {
        RequestManager.addRequest(new GsonRequest(Const.WEATHER_CUR + id + ".html", CurWeatherModel.CurWeatherRequestData.class, responseCurListener(), errorListener()), this);
        RequestManager.addRequest(new GsonRequest(Const.WEATHER_NOW + id + ".html", WeatherTodayModel.WeatherTodayRequestData.class, responseListener(), errorListener()), this);
    }

    /**
     * 获取数据成功回调
     * @return
     */
    private Response.Listener<WeatherTodayModel.WeatherTodayRequestData> responseListener() {

        return new Response.Listener<WeatherTodayModel.WeatherTodayRequestData>() {
            @Override
            public void onResponse(WeatherTodayModel.WeatherTodayRequestData weatherTodayRequestData) {
                mWeatherTodayModel = weatherTodayRequestData.weatherinfo;
                sendUpdateWeather();
            }
        };
    }
    private Response.Listener<CurWeatherModel.CurWeatherRequestData> responseCurListener() {

        return new Response.Listener<CurWeatherModel.CurWeatherRequestData>() {
            @Override
            public void onResponse(CurWeatherModel.CurWeatherRequestData curWeatherRequestData) {

                mCurWeatherModel = curWeatherRequestData.weatherinfo;
                sendUpdateWeather();
            }
        };

    }

    /**
     * 获取数据错误回调
     * @return
     */
    protected Response.ErrorListener errorListener() {

        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        };
    }

    // 发送广播，更新天气
    private void sendUpdateWeather() {
        sendBroadcast(new Intent(ACTION_UPDATE_WEATHER));
    }

    @UiThread
    void updateTime() {

        Date date = new Date();
        // 定义SimpleDateFormat对象
        remoteView.setTextViewText(R.id.tv_provider_time, DateUtil.getCurTime());
        remoteView.setTextViewText(R.id.tv_provider_week, DateUtil.getCurWeek());
        remoteView.setOnClickPendingIntent(R.id.rl_provider_widget, PendingIntent.getActivity(this, 0, new Intent(this, MainActivity_.class), 0));
        remoteView.setOnClickPendingIntent(R.id.ll_provider_right, PendingIntent.getBroadcast(this, 1, new Intent(ACTION_NEXT_CITY), 0));
        ComponentName componentName = new ComponentName(getApplication(), WeatherWidget.class);
        AppWidgetManager.getInstance(getApplication()).updateAppWidget(componentName, remoteView);
    }

    @UiThread
    void updateWeather() {

        if (mWeatherTodayModel != null) {
            if (mWeatherTodayModel.cityName != null)
                remoteView.setTextViewText(R.id.tv_provider_city, mWeatherTodayModel.cityName);
            if (mWeatherTodayModel.temp != null)
                remoteView.setTextViewText(R.id.tv_provider_temp, mWeatherTodayModel.temp + "℃");
        }
        if (mCurWeatherModel != null) {
            if (mCurWeatherModel.weather != null) {
                remoteView.setTextViewText(R.id.tv_provider_weather, mCurWeatherModel.weather);
                remoteView.setImageViewResource(R.id.iv_provider_weather, WeatherUtil.getIcon(mCurWeatherModel.weather));
            }
        }

        ComponentName componentName = new ComponentName(getApplication(), WeatherWidget.class);
        AppWidgetManager.getInstance(getApplication()).updateAppWidget(componentName, remoteView);
    }

    // 广播接收者去接收系统每分钟的提示广播，来更新时间
    private BroadcastReceiver mTimePickerBroadcast = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ACTION_UPDATE_WEATHER)) {
                updateWeather();
            } else if (intent.getAction().equals(ACTION_SWITH_CITY)) {
                LogUtil.i(TAG, "swith city " + App.getMyArea().get(App.getCurCityIndex()).getCityName());
                getWeather(getNextWeatherCode(App.getCurCityIndex()));
            } else if (intent.getAction().equals(ACTION_NEXT_CITY)) {
                LogUtil.i(TAG, "next city");
                App.setCurCityIndex((App.getCurCityIndex() + 1) % App.getMyArea().size());
                getWeather(getNextWeatherCode(App.getCurCityIndex()));
            } else {
                updateTime();
            }
        }
    };

    // 注册事件，注（只能在代码中注册）
    private void registerReceiver() {
        IntentFilter updateIntent = new IntentFilter();
        updateIntent.addAction(ACTION_UPDATE_WEATHER);
        updateIntent.addAction(ACTION_SWITH_CITY);
        updateIntent.addAction(ACTION_NEXT_CITY);
        updateIntent.addAction(ACTION_TIME_SET);
        updateIntent.addAction(Intent.ACTION_TIME_TICK);
        updateIntent.addAction(Intent.ACTION_DATE_CHANGED);
        updateIntent.addAction(Intent.ACTION_TIMEZONE_CHANGED);
        registerReceiver(mTimePickerBroadcast, updateIntent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        registerReceiver();
        updateTime();
        return super.onStartCommand(intent, START_STICKY, startId);
    }

    @Override
    public void onDestroy() {
        // 注销广播
        if (mTimePickerBroadcast != null) {
            unregisterReceiver(mTimePickerBroadcast);
        }
        // 被系统干掉后，服务重启
        WeatherUpdateService_.intent(getApplicationContext()).start();
        super.onDestroy();
    }
}
