package tk.woppo.sunday.ui.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import tk.woppo.sunday.widget.TodayWeatherItem;
import tk.woppo.sunday.widget.jazzyviewpager.JazzyViewPager;
import tk.woppo.sunday.widget.jazzyviewpager.OutlineContainer;

/**
 * Created by Ho on 2014/7/2.
 */
public class WeatherTodayAdapter extends PagerAdapter {

    private final List<TodayWeatherItem> mList;
    private JazzyViewPager mViewPager;

    public WeatherTodayAdapter(List<TodayWeatherItem> list, JazzyViewPager viewPager) {
        mList = list;
        mViewPager = viewPager;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mList.get(position), ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mViewPager.setObjectForPosition(mList.get(position), position);
        return mList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewPager.findViewFromObject(position));
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        if (view instanceof OutlineContainer) {
            return ((OutlineContainer) view).getChildAt(0) == object;
        } else {
            return view == object;
        }
    }

}
