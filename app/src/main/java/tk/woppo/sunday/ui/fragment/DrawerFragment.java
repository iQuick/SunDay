package tk.woppo.sunday.ui.fragment;

import android.app.Fragment;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

import tk.woppo.sunday.App;
import tk.woppo.sunday.Const;
import tk.woppo.sunday.R;
import tk.woppo.sunday.model.WeatherModel;
import tk.woppo.sunday.ui.AboutActivity;
import tk.woppo.sunday.ui.AboutActivity_;
import tk.woppo.sunday.ui.CityActivity_;
import tk.woppo.sunday.ui.MainActivity;
import tk.woppo.sunday.ui.MyCityActivity_;
import tk.woppo.sunday.ui.SettingActivity;
import tk.woppo.sunday.ui.SettingActivity_;
import tk.woppo.sunday.util.ActivityUtil;
import tk.woppo.sunday.util.LogUtil;
import tk.woppo.sunday.util.SharedPrefUtil;
import tk.woppo.sunday.util.ToastUtil;
import tk.woppo.sunday.util.WeatherUtil;
import tk.woppo.sunday.widget.TodayWeatherItem;

/**
 * Created by Ho on 2014/7/1.
 */

@EFragment(R.layout.fragment_drawer)
public class DrawerFragment extends BaseFragment {

    ////////////////////////////////////////////
    //温馨提示
    @ViewById(R.id.drawer_weather_img)
    ImageView ivWeatherImg;

    @ViewById(R.id.drawer_weather)
    ImageView ivWeather;

    @ViewById(R.id.drawer_tv_weather)
    TextView tvWeather;

    @ViewById(R.id.drawer_temp)
    TextView tvTemp;

    @ViewById(R.id.drawer_tigan)
    TextView tvTigan;

    @ViewById(R.id.drawer_chuanyi)
    TextView tvChuanyi;

    @ViewById(R.id.drawer_ganmao)
    TextView tvGanmao;

    @ViewById(R.id.drawer_ziwaixian)
    TextView tvZhiwaixian;

    @ViewById(R.id.drawer_yundong)
    TextView tvYundong;

    @ViewById(R.id.drawer_xiche)
    TextView tvXiche;

    @AfterViews
    void initFragment() {
    }

    /**
     * 更新温馨提示
     */
    public void updateReminder(WeatherModel model) {
        if (model != null) {
            App.mCurWeatherModel = model;
            ivWeatherImg.setImageResource(WeatherUtil.getImg(model.weather1));
            ivWeather.setImageResource(WeatherUtil.getIcon(model.weather1));
            tvWeather.setText(model.weather1);
            tvTemp.setText(model.temp1);
            tvTigan.setText(model.index_co);
            tvChuanyi.setText(model.index_d);
            tvZhiwaixian.setText(model.index_uv);
            tvYundong.setText(model.index_cl);
            tvGanmao.setText(model.index_ag);
            tvXiche.setText(model.index_xc);
        }
    }

    @Click(R.id.drawer_add_city)
    void clickAddCity() {
        CityActivity_.intent(((MainActivity) getActivity()).homeFragment).startForResult(TodayWeatherItem.REQUEST_CODE);
        ((MainActivity) getActivity()).closeDrawer();
    }

    @Click(R.id.drawer_del_city)
    void clickDelCity() {
        MyCityActivity_.intent(((MainActivity) getActivity()).homeFragment).startForResult(TodayWeatherItem.REQUEST_CODE);
        ((MainActivity) getActivity()).closeDrawer();
    }

    @Click(R.id.drawwer_setting)
    void clickSetting() {
        openActivity(SettingActivity_.class);
        ((MainActivity) getActivity()).closeDrawer();
    }

    @Click(R.id.drawer_exit)
    void clickExit() {
        ((MainActivity) getActivity()).closeDrawer();

        if (SharedPrefUtil.getBoolean(Const.CONFIG_EXIT_KILL, false))
            ActivityUtil.finishKill();
        else
            ActivityUtil.finishAll();
    }
}