package tk.woppo.sunday.widget;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.util.Random;

import tk.woppo.sunday.R;
import tk.woppo.sunday.model.Weather;
import tk.woppo.sunday.model.city.BaseCityModel;
import tk.woppo.sunday.util.WeatherUtil;

/**
 * Created by Ho on 2014/6/29.
 */

@EViewGroup(R.layout.layout_hourly_item)
public class HourlyItem extends LinearLayout {

    @ViewById(R.id.rl_item_hourly)
    RelativeLayout rlBg;

    @ViewById(R.id.img_item_hourly_weather)
    ImageView imgWeather;

    @ViewById(R.id.tv_item_hourly_weather)
    TextView tvWeather;

    @ViewById(R.id.tv_item_hourly_temp)
    TextView tvTemp;

    @ViewById(R.id.tv_item_hourly_time)
    TextView tvTime;

    public HourlyItem(Context context) {
        super(context);
    }

    public void bind(Weather model) {
        rlBg.setBackgroundResource(getBg(model));
        imgWeather.setImageResource(WeatherUtil.getWeatherIcon(model.info));
        tvWeather.setText(model.info);
        tvTemp.setText(model.temp + "℃");
        tvTime.setText(model.hour + "点");
    }

    private int getBg(Weather model) {

        if (model.temp > 35) {
            return R.drawable.bg_hourly_item3;
        } else {
            if (model.info.equals("大雨") || model.info.equals("暴雨") || model.info.equals("阵雨") || model.info.equals("雷阵雨"))
                return R.drawable.bg_hourly_item3;
            else if (model.info.equals("小雨") || model.info.equals("中雨"))
                return R.drawable.bg_hourly_item1;
            else if (model.info.equals("阴"))
                return R.drawable.bg_hourly_item4;
            else
                return R.drawable.bg_hourly_item2;
        }
    }
}
