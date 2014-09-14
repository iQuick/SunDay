package tk.woppo.sunday.widget;

import android.app.Fragment;
import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import tk.woppo.sunday.R;
import tk.woppo.sunday.model.WeatherTodayModel;
import tk.woppo.sunday.ui.CityActivity_;
import tk.woppo.sunday.ui.fragment.HomeFragment_;
import tk.woppo.sunday.util.WeatherUtil;

/**
 * Created by Ho on 2014/7/2.
 */
@EViewGroup(R.layout.layout_weather_today)
public class TodayWeatherItem extends LinearLayout {

    public final static int REQUEST_CODE = 1000;

    protected Fragment mFragment;

    @ViewById(R.id.iv_weather_icon)
    ImageView ivWeatherIcon;

    @ViewById(R.id.tv_weather)
    TextView tvWeather;

    @ViewById(R.id.tv_city)
    TextView tvCity;

    @ViewById(R.id.tv_temp)
    TextView tvTemp;

    @ViewById(R.id.tv_wind)
    TextView tvWind;

    @ViewById(R.id.tv_create_time)
    TextView tvCreateTiem;


    public TodayWeatherItem(Context context, Fragment fragment) {
        super(context);
        this.mFragment = fragment;
    }

    public void bind(WeatherTodayModel model) {
        tvCity.setText(model.cityName);
        tvCreateTiem.setText(model.time + "发布");
        tvWind.setText(model.wind);
        tvTemp.setText(model.temp + "℃");
        tvWeather.setText(model.weather);
        ivWeatherIcon.setImageResource(WeatherUtil.getIcon(model.weather));
    }

    /**
     * 设置空气质量
     */
    private String setWeatherAQI(int aqi) {
        String s = "";
        if (aqi >= 0 && aqi <= 50) {

        } else if (aqi > 50 && aqi <= 100) {

        } else if (aqi > 100 && aqi <= 150) {

        } else if (aqi > 150 && aqi <= 200) {

        } else if (aqi > 200 && aqi <= 250) {

        } else {
        }
        return s;
    }

    @Click(R.id.tv_city)
    void cityClick() {
        CityActivity_.intent(mFragment).startForResult(REQUEST_CODE);
    }
}
