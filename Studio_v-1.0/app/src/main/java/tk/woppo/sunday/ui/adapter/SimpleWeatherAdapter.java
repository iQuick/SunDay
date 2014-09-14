package tk.woppo.sunday.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

import tk.woppo.sunday.model.SimpleWeatherModel;
import tk.woppo.sunday.widget.SimpleWeatherItem;
import tk.woppo.sunday.widget.SimpleWeatherItem_;

/**
 * Created by Ho on 2014/7/3.
 */
@EBean
public class SimpleWeatherAdapter extends BaseAdapter {

    @RootContext
    protected Context mContext;

    List<SimpleWeatherModel> mList = new ArrayList<SimpleWeatherModel>();

    public void appendToList(List<SimpleWeatherModel> list) {
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

    public List<SimpleWeatherModel> getData() {
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
        SimpleWeatherModel model = mList.get(position);
        SimpleWeatherItem item;

        if (convertView == null) {
            item = SimpleWeatherItem_.build(mContext);
        } else {
            item = (SimpleWeatherItem) convertView;
        }
        item.bind(model, position);
        return item;
    }

    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }
}
