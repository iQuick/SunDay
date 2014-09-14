package tk.woppo.sunday.widget;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import tk.woppo.sunday.R;
import tk.woppo.sunday.model.SimpleWeatherModel;
import tk.woppo.sunday.util.WeatherUtil;

/**
 * Created by Ho on 2014/7/3.
 */

@EViewGroup(R.layout.layout_simple_item)
public class SimpleWeatherItem extends LinearLayout {

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

    public SimpleWeatherItem(Context context) {
        super(context);
    }

    public void bind(SimpleWeatherModel model, int position) {
        if (position == 0) {
            ll.setBackgroundResource(R.drawable.simple_item_first);
        } else {
            ll.setBackgroundResource(R.drawable.simple_item);
        }
        tvWeather.setText(model.getWeather());
        tvTemp.setText(model.getTemp());
        tvWeek.setText(model.getWeek());
        ivWeather.setImageResource(WeatherUtil.getIcon(model.getWeather()));
    }
}
