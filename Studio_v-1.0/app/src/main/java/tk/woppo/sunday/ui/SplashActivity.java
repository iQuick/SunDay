package tk.woppo.sunday.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;

import tk.woppo.sunday.App;
import tk.woppo.sunday.Const;
import tk.woppo.sunday.R;
import tk.woppo.sunday.model.city.AreaModel;
import tk.woppo.sunday.util.SharedPrefUtil;

/**
 * Created by Ho on 2014/7/10.
 */
@Fullscreen
@EActivity(R.layout.activity_splash)
public class SplashActivity extends Activity {

    protected final static int START_TIME = 2 * 1000;

    @AfterViews
    void initActivity() {

        isFrast();
    }

    private void isFrast() {

        if (SharedPrefUtil.isFrast()) {
            //添加一个默认城市-北京
            App.addMyArea(new AreaModel(Const.DEF_CITY_ID, Const.DEF_CITY_NAME, Const.DEF_WEATHER_CODE));
            //进入引导界面
            start(GuideActivity_.class);
        } else {
            //进入应用主界面
            start(MainActivity_.class);
        }
    }

    /**
     * 启动应用
     */
    private void start(final Class clazz) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, clazz);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        }, START_TIME);
    }
}
