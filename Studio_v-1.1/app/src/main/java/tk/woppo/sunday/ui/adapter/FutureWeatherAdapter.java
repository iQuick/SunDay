package tk.woppo.sunday.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

import tk.woppo.sunday.model.WeatherModel;
import tk.woppo.sunday.widget.FutureWeatherItem;
import tk.woppo.sunday.widget.FutureWeatherItem_;

/**
 * Created by Ho on 2014/7/3.
 */
@EBean
public class FutureWeatherAdapter extends BaseAdapter {

    @RootContext
    protected Context mContext;

    List<WeatherModel> mList = new ArrayList<WeatherModel>();

    public void appendToList(List<WeatherModel> list) {
        if (list == null) {
            return;
        }
        mList.clear();
        mList.addAll(list);
        try {
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<WeatherModel> getData() {
        return mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WeatherModel model = mList.get(position);
        FutureWeatherItem item;

        if (convertView == null) {
            item = FutureWeatherItem_.build(mContext);
        } else {
            item = (FutureWeatherItem) convertView;
        }
        item.bind(model, position);
        return item;
    }

    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }
}
