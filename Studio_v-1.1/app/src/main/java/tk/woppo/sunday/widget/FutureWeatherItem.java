package tk.woppo.sunday.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.util.Date;

import tk.woppo.sunday.R;
import tk.woppo.sunday.model.WeatherModel;
import tk.woppo.sunday.util.DateUtil;
import tk.woppo.sunday.util.WeatherUtil;

/**
 * Created by Ho on 2014/7/3.
 */

@EViewGroup(R.layout.layout_future_item)
public class FutureWeatherItem extends LinearLayout {

    @ViewById(R.id.ll_simple_item)
    LinearLayout ll;

    @ViewById(R.id.tv_simple_item_weather)
    TextView tvWeather;

    @ViewById(R.id.tv_simple_item_temp)
    TextView tvTemp;

    @ViewById(R.id.tv_simple_item_week)
    TextView tvWeek;

    @ViewById(R.id.iv_simple_item_Weather)
    ImageView ivWeather;

    public FutureWeatherItem(Context context) {
        super(context);
    }

    public void bind(WeatherModel model, int position) {
        if (position == 0) {
            ll.setBackgroundResource(R.drawable.simple_item_first);
        } else {
            ll.setBackgroundResource(R.drawable.simple_item);
        }

        // 如果当天的为晚上，则显示晚上天气，其余为白天天气
        if (position == 0 && !DateUtil.isSun()) {
            ivWeather.setImageResource(WeatherUtil.getIcon(model.info.night.get(1)));
            tvWeather.setText(model.info.night.get(1));
        } else {
            ivWeather.setImageResource(WeatherUtil.getIcon(model.info.day.get(1)));
            tvWeather.setText(model.info.day.get(1));
        }

        tvTemp.setText(model.info.day.get(2) + " - " + model.info.night.get(2) + "℃");
        tvWeek.setText(getDateSet(model.date));
    }

    /**
    * 这里通过蔡勒公式算出某一天是星期几
    */
    public String getDateSet(String date) {

        String dates[] = date.split("-");
        int y= Integer.parseInt(dates[0])-1;
        int m=Integer.parseInt(dates[1]);
        int d=Integer.parseInt(dates[2]);
        int c=20;
        int w=(y+(y/4)+(c/4)-2*c+(26*(m+1)/10)+d-1)%7;
        String myWeek = null;
        switch(w) {
            case 0:
                myWeek="日";
                break;
            case 1:
                myWeek="一";
                break;
            case 2:
                myWeek="二";
                break;
            case 3:
                myWeek="三";
                break;
            case 4:
                myWeek="四";
                break;
            case 5:
                myWeek="五";
                break;
            case 6:
                myWeek="六";
                break;
            default:
                break;
        }

        return Integer.parseInt(dates[1]) + "月" + Integer.parseInt(dates[2]) + "日" + " 周" + myWeek;
    }
}
