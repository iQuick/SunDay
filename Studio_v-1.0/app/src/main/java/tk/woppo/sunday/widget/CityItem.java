package tk.woppo.sunday.widget;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import tk.woppo.sunday.model.city.BaseCityModel;
import tk.woppo.sunday.model.city.CityModel;
import tk.woppo.sunday.R;

/**
 * Created by Ho on 2014/6/29.
 */

@EViewGroup(R.layout.layout_city_item)
public class CityItem extends LinearLayout {

    @ViewById(R.id.tv_city_name_layout)
    TextView tvCityName;

    CityItem(Context context) {
        super(context);
    }

    public void bind(BaseCityModel model) {
        tvCityName.setText(model.getCityName());
    }
}
