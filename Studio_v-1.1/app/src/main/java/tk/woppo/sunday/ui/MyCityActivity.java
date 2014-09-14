package tk.woppo.sunday.ui;

import android.app.Activity;
import android.graphics.Point;
import android.nfc.Tag;
import android.view.Display;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import tk.woppo.sunday.App;
import tk.woppo.sunday.R;
import tk.woppo.sunday.model.city.BaseCityModel;
import tk.woppo.sunday.ui.adapter.MyCityAdapter;
import tk.woppo.sunday.util.LogUtil;
import tk.woppo.sunday.util.ToastUtil;
import tk.woppo.sunday.widget.swipeback.SwipeBackActivity;
import tk.woppo.sunday.widget.swipelistview.SwipeListView;

/**
 * Created by Ho on 2014/7/3.
 */
@EActivity(R.layout.activiy_mycity)
public class MyCityActivity extends SwipeBackActivity {

    @ViewById(R.id.lv_my_city)
    SwipeListView mListView;

    @Bean
    MyCityAdapter mCityAdapter;

    @AfterViews
    void initActivity() {

        initView();
    }

    private void initView() {

        // 获取屏幕宽度
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenWidth = size.x;

        // 设置模式支持滑动
        mListView.setSwipeMode(SwipeListView.SWIPE_MODE_LEFT);
        mListView.setSwipeActionLeft(0);
        mListView.setSwipeActionRight(0);
        mListView.setOffsetLeft(screenWidth / 4 * 3);
        mListView.setOffsetRight(0);
        mListView.setAnimationTime(0);
        // 支持长按自动呼出
        mListView.setSwipeOpenOnLongPress(true);

        mListView.setAdapter(mCityAdapter);
        mCityAdapter.appendToList(App.getMyArea());
    }

    public void delectCity(int position) {
        if (App.getMyArea().size() <= 1) {
            ToastUtil.showShort(R.string.no_delect);
        } else {
            setResult(1);
            App.removeMyArea(position);
            mCityAdapter.appendToList(App.getMyArea());
            mListView.closeOpenedItems();
        }
    }
}
