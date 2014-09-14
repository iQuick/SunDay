package tk.woppo.sunday.widget;

import android.content.Context;
import android.nfc.Tag;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import tk.woppo.sunday.R;
import tk.woppo.sunday.model.city.BaseCityModel;
import tk.woppo.sunday.ui.MyCityActivity;
import tk.woppo.sunday.util.LogUtil;

/**
 * Created by Ho on 2014/7/3.
 */
@EViewGroup(R.layout.layout_mycity_item)
public class MyCityItem extends LinearLayout {

    @ViewById(R.id.front)
    TextView tvCity;

    private int position;
    private BaseCityModel model;

    public MyCityItem(Context context) {
        super(context);
    }

    public void bind(BaseCityModel model, int position) {
        this.position = position;
        this.model = model;
        tvCity.setText(model.getCityName());
    }

    @Click(R.id.delectCity)
    void deleteCityClick() {
        try {
            ((MyCityActivity) getContext()).delectCity(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
