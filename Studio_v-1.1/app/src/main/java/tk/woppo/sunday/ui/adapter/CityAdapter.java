package tk.woppo.sunday.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

import tk.woppo.sunday.model.city.BaseCityModel;
import tk.woppo.sunday.widget.CityItem;
import tk.woppo.sunday.widget.CityItem_;

/**
 * Created by Ho on 2014/6/29.
 */

@EBean
public class CityAdapter extends BaseAdapter {

    @RootContext
    Context mContext;

    List<BaseCityModel> mList = new ArrayList<BaseCityModel>();

    public void appendToList(List<BaseCityModel> list) {
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
        BaseCityModel model = mList.get(position);
        CityItem item;

        if (convertView == null) {
            item = CityItem_.build(mContext);
        } else {
            item = (CityItem) convertView;
        }
        item.bind(model);
        return item;
    }

    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }

}
