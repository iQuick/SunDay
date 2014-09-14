package tk.woppo.sunday.ui;

import android.content.Intent;
import android.view.View;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import tk.woppo.sunday.App;
import tk.woppo.sunday.Const;
import tk.woppo.sunday.R;
import tk.woppo.sunday.util.DataCleanUtil;
import tk.woppo.sunday.util.SharedPrefUtil;
import tk.woppo.sunday.util.ToastUtil;
import tk.woppo.sunday.widget.MySlipSwitch;
import tk.woppo.sunday.widget.swipeback.SwipeBackActivity;

/**
 * Created by Ho on 2014/7/10.
 */
@EActivity(R.layout.activity_setting)
public class SettingActivity extends SwipeBackActivity implements MySlipSwitch.OnSwitchListener {

    private String mStrShareWeather = "SunDay天气";

    @ViewById(R.id.setting_exit)
    MySlipSwitch mExitSlipSwitch;

    @ViewById(R.id.setting_notify)
    MySlipSwitch mNotifySlipSwitch;

    @AfterViews
    void initActivity(){

        initShareStr();
        initSlipSwitch();
    }

    private void initShareStr() {
        //初始化分享内容
        if (App.mCurWeatherModel != null) {
            mStrShareWeather =
                    "SunDay天气提醒您," +
                            "今日" + App.mCurWeatherModel.city + "天气" + App.mCurWeatherModel.weather1 + "," +
                            "温度" + App.mCurWeatherModel.temp1 + "," +
                            "体感" + App.mCurWeatherModel.index_co + "," +
                            "建议穿着" + App.mCurWeatherModel.index_d + "," +
                            App.mCurWeatherModel.index_cl + "晨练";
        }
    }

    private void initSlipSwitch() {
        mExitSlipSwitch.setImageResource(R.drawable.switch_on, R.drawable.switch_off, R.drawable.switch_btn);
        mNotifySlipSwitch.setImageResource(R.drawable.switch_on, R.drawable.switch_off, R.drawable.switch_btn);
        mExitSlipSwitch.setSwitchState(SharedPrefUtil.getBoolean(Const.CONFIG_EXIT_KILL, false));
        mNotifySlipSwitch.setSwitchState(SharedPrefUtil.getBoolean(Const.CONFIG_NOTIFY, false));
        mExitSlipSwitch.setOnSwitchListener(this);
        mNotifySlipSwitch.setOnSwitchListener(this);
    }

    /**
     * 分享天气
     */
    @Click(R.id.setting_share_weather)
    void shareWeather() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_SUBJECT, "SunDay天气分享");
        // 自动添加的发送的具体信息
        intent.putExtra(Intent.EXTRA_TEXT, mStrShareWeather);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, getTitle()));
    }

    /**
     * 短信发送天气
     */
    @Click(R.id.setting_send_weather)
    void sendWeather() {

        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.putExtra("sms_body", mStrShareWeather);
            intent.setType("vnd.android-dir/mms-sms");
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.showShort(R.string.error_send);
        }
    }

    /**
     * 检查更新
     */
    @Click(R.id.setting_check_update)
    void clickUpdate() {
        if (false) {
            // 进入更新
        } else {
            // 已是最新版本
            ToastUtil.showShort(R.string.no_update);
        }
    }

    /**
     * 清除缓存
     */
    @Click(R.id.setting_clean)
    void clickClean() {
        showLoading();
        cleanCache();
    }

    @Background
    void cleanCache() {
        DataCleanUtil.cleanInternalCache(this);
        DataCleanUtil.cleanFiles(this);
        cleanCacheOver();
    }

    @UiThread
    void cleanCacheOver() {
        dismissLoading();
        ToastUtil.showShort(R.string.success_clean);
    }

    /**
     * About
     */
    @Click(R.id.setting_about)
    void clickAbout() {
        openActivity(AboutActivity_.class);
    }

    @Override
    public void onSwitched(View v, boolean isSwitchOn) {
        switch (v.getId()) {
            case R.id.setting_exit:
                SharedPrefUtil.putBoolean(Const.CONFIG_EXIT_KILL, isSwitchOn);
                break;
            case R.id.setting_notify:
                SharedPrefUtil.putBoolean(Const.CONFIG_NOTIFY, isSwitchOn);
                break;
        }
    }
}
