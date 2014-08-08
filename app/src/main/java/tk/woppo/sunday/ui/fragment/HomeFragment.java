package tk.woppo.sunday.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.AnimationRes;

import java.util.ArrayList;
import java.util.List;

import tk.woppo.sunday.App;
import tk.woppo.sunday.App_;
import tk.woppo.sunday.Const;
import tk.woppo.sunday.R;
import tk.woppo.sunday.dao.WeatherDataHelper;
import tk.woppo.sunday.dao.WeatherTodayDataHelper;
import tk.woppo.sunday.data.GsonRequest;
import tk.woppo.sunday.model.CurWeatherModel;
import tk.woppo.sunday.model.SimpleWeatherModel;
import tk.woppo.sunday.model.WeatherModel;
import tk.woppo.sunday.model.WeatherTodayModel;
import tk.woppo.sunday.model.city.AreaModel;
import tk.woppo.sunday.service.WeatherUpdateService;
import tk.woppo.sunday.ui.CityActivity_;
import tk.woppo.sunday.ui.MainActivity;
import tk.woppo.sunday.ui.adapter.SimpleWeatherAdapter;
import tk.woppo.sunday.ui.adapter.WeatherTodayAdapter;
import tk.woppo.sunday.util.LogUtil;
import tk.woppo.sunday.util.NetUtil;
import tk.woppo.sunday.util.SharedPrefUtil;
import tk.woppo.sunday.util.ToastUtil;
import tk.woppo.sunday.widget.TodayWeatherItem;
import tk.woppo.sunday.widget.TodayWeatherItem_;
import tk.woppo.sunday.widget.jazzyviewpager.JazzyViewPager;

/**
 * Created by Ho on 2014/7/1.
 */
@EFragment(R.layout.fragment_home)
public class HomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    protected final static String TAG = "HomeFragment";

    /** 是否第一次进入本Fragment */
    protected boolean isFrastEnter = true;

    private WeatherDataHelper mWeatherDB;
    private WeatherTodayDataHelper mTodayDB;

    @ViewById(R.id.tv_city)
    TextView tvCity;

    @ViewById(R.id.tv_temp)
    TextView tvTemp;

    @ViewById(R.id.tv_weather)
    TextView tvWeather;

    @AnimationRes
    Animation fadeIn;

    @ViewById(R.id.swipe_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private MenuItem mRefreshItem;

    /** 天气信息 */
    private List<WeatherModel> mWeatherModels;

    /** 今日天气Pager */
    @ViewById(R.id.jazzy_pager)
    JazzyViewPager mViewPager;

    /** 今日天气信息 */
    private TodayWeatherItem mWeatherToday;
    private List<WeatherTodayModel> mWeatherTodayModels;
    private List<TodayWeatherItem> weatherViews;

    /** 六天天气数据 */
    List<List<SimpleWeatherModel>>  mSimpleWeatherModels;

    /** 六天天气适配器 */
    @Bean
    SimpleWeatherAdapter simpleWeatherAdapter;

    /** 六天天气ListView */
    @ViewById(R.id.lv_weather)
    ListView mWeatherListView;

    /** 当前Page页面 */
    private int mCurPageIndex;
    /** 城市数 */
    private int mCityNum;

    @AfterViews
    void initFragment() {

        isFrast();
        setHasOptionsMenu(true);

        if (isFrastEnter) {
            initData();
            initView();
            isFrastEnter = false;
        }
    }

    private void isFrast() {
        if (SharedPrefUtil.isFrast()) {
            // 进入选择城市
            CityActivity_.intent(this).startForResult(TodayWeatherItem.REQUEST_CODE);
            SharedPrefUtil.setFrast();
        }
    }

    private void initData() {
        mCityNum = App.getMyArea().size();
        mWeatherDB = new WeatherDataHelper(App.getContext());
        mTodayDB = new WeatherTodayDataHelper(App.getContext());
        mWeatherModels = new ArrayList<WeatherModel>();
        mSimpleWeatherModels = new ArrayList<List<SimpleWeatherModel>>();
        mWeatherTodayModels = new ArrayList<WeatherTodayModel>();
//        getDataFromDB();
        getAllWeatherData();
    }

    private void initView() {
        mWeatherListView.setAdapter(simpleWeatherAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorScheme(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    private void initViewPager() {
        weatherViews = new ArrayList<TodayWeatherItem>();
        for (int i = 0; i < mWeatherTodayModels.size(); i++) {
            mWeatherToday = TodayWeatherItem_.build(getActivity(), this);

            mWeatherToday.bind(mWeatherTodayModels.get(i));
            weatherViews.add(mWeatherToday);
        }
        mViewPager.setTransitionEffect(JazzyViewPager.TransitionEffect.CubeOut);
        mViewPager.setOnPageChangeListener(new MyPageViewListener());
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setAdapter(new WeatherTodayAdapter(weatherViews, mViewPager));
        mViewPager.setPageMargin(3);
    }

    @UiThread
    void updateWeatherList() {
        if (mWeatherTodayModels.size() == App.getMyArea().size() && mWeatherModels.size() == App.getMyArea().size()) {
            for (int i = 0; i < mWeatherModels.size(); i++) {
                mWeatherTodayModels.get(i).weather = mWeatherModels.get(i).weather1;
            }
            simpleWeatherAdapter.appendToList(mSimpleWeatherModels.get(0));
            initViewPager();
        }

        dismissLoading();
    }

    private void initDataOver() {
        updateWeatherList();
        inserData();
        setRefreshing(false);
    }

    /**
     * 从数据库中获取数据
     */
    @Background
    void getDataFromDB() {
        for (AreaModel model : App.getMyArea()) {
            WeatherModel weatherModel = mWeatherDB.query(model.getWeatherCode());
            WeatherTodayModel weatherTodayModel = mTodayDB.query(model.getWeatherCode());
            if (weatherModel != null) {
                mWeatherModels.add(weatherModel);
                mSimpleWeatherModels.add(weatherModel.toSimpleWeatherList());
            }
            if (weatherTodayModel != null) mWeatherTodayModels.add(weatherTodayModel);
        }
        // 更新列表
        updateWeatherList();
    }

    /**
     * 插入数据到数据库
     */
    @Background
    void inserData() {
        //保存数据到数据库中
        mWeatherDB.deleteAll();
        mTodayDB.deleteAll();
        mWeatherDB.bulkInsert(mWeatherModels);
        mTodayDB.bulkInsert(mWeatherTodayModels);
    }

    /**
     * 获取所有所有城市天气
     */
    private void getAllWeatherData() {

        showLoading();

        // 清除所有数据
        mWeatherModels.clear();
        mSimpleWeatherModels.clear();
        mWeatherTodayModels.clear();

        if (NetUtil.isNetworkConnected()) {
            getWeatherData(App.getMyArea().get(0).getWeatherCode());}
        else {
            getDataFromDB();
            ToastUtil.showShort(R.string.error_net);
        }
    }

    /**
     * 获取城市信息
     * @param id 天气WeatherCode
     */
    private void getWeatherData(String id) {
        executeRequest(new GsonRequest(Const.WEATHER + id + ".html", WeatherModel.WeatherRequestData.class, responseListener(), errorListener()));
        executeRequest(new GsonRequest(Const.WEATHER_NOW + id + ".html", WeatherTodayModel.WeatherTodayRequestData.class, responseTodayListener(), errorListener()));
    }

    private void onRefreshData() {
//        getWeatherData(App.getMyArea().get(mCurPageIndex).getWeatherCode());
        getAllWeatherData();
    }

    private Response.Listener<WeatherTodayModel.WeatherTodayRequestData> responseTodayListener() {
        return new Response.Listener<WeatherTodayModel.WeatherTodayRequestData>() {
            @Override
            public void onResponse(WeatherTodayModel.WeatherTodayRequestData weatherTodayRequestData) {
                mWeatherTodayModels.add(weatherTodayRequestData.weatherinfo);
                if (mWeatherTodayModels.size() < App.getMyArea().size()) {
                    executeRequest(new GsonRequest(Const.WEATHER_NOW + App.getMyArea().get(mWeatherTodayModels.size()).getWeatherCode() + ".html", WeatherTodayModel.WeatherTodayRequestData.class, responseTodayListener(), errorListener()));
                } else {
                    initDataOver();
                }
                // 获取天气
//                executeRequest(new GsonRequest(Const.WEATHER_CUR + weatherTodayRequestData.weatherinfo.id + ".html", CurWeatherModel.CurWeatherRequestData.class, responseCurListener(), errorListener()));
            }
        };
    }

    /**
     * 获取数据成功回调
     * @return
     */
    private Response.Listener<WeatherModel.WeatherRequestData> responseListener() {

        return new Response.Listener<WeatherModel.WeatherRequestData>() {
            @Override
            public void onResponse(WeatherModel.WeatherRequestData weatherRequestData) {
                mWeatherModels.add(weatherRequestData.weatherinfo);
                mSimpleWeatherModels.add(weatherRequestData.weatherinfo.toSimpleWeatherList());
                if (mWeatherModels.size() < App.getMyArea().size()) {
                    executeRequest(new GsonRequest(Const.WEATHER + App.getMyArea().get(mWeatherModels.size()).getWeatherCode() + ".html", WeatherModel.WeatherRequestData.class, responseListener(), errorListener()));
                } else {
                    initDataOver();
                }
            }
        };
    }

    /**
     * 获取数据错误回调
     * @return
     */
    protected Response.ErrorListener errorListener() {

        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
//                getDataFromDB();
                LogUtil.e(TAG, R.string.error_get_weather);
                ToastUtil.showShort(R.string.error_get_weather);
                setRefreshing(false);
            }
        };
    }

    @OnActivityResult(TodayWeatherItem.REQUEST_CODE)
    void onResult(int resultCode) {

        if (resultCode == 1 || mCityNum != App.getMyArea().size()) {
            mCityNum = App.getMyArea().size();
            getAllWeatherData();
        }
    }

    public WeatherModel getCurWeatherModel() {
        if (mWeatherModels != null && mWeatherModels.size() != 0)
            return mWeatherModels.get(mCurPageIndex);
        else
            return null;
    }

    @Override
    public void onRefresh() {

        if (mViewPager.getCurrentItem() < 1 || simpleWeatherAdapter.getData().size() < 1) {
            getAllWeatherData();
        } else {
            onRefreshData();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewPager.setCurrentItem(App.getCurCityIndex());
    }

    public void setOnRefresh() {
        setRefreshing(true);
        onRefresh();
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        mRefreshItem = menu.findItem(R.id.action_refresh);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void setRefreshing(boolean refreshing) {
        mSwipeRefreshLayout.setRefreshing(refreshing);
        if (mRefreshItem == null) return;

        if (refreshing) {
            mRefreshItem.setEnabled(false);
            mRefreshItem.setActionView(R.layout.actionbar_refresh_progress);
        }
        else {
            mRefreshItem.setEnabled(true);
            mRefreshItem.setActionView(null);
        }
    }

    /**
     * ViewPage Listener
     */
    private class MyPageViewListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            try {
                App.setCurCityIndex(mCurPageIndex = position);
                LogUtil.i(TAG, App.getMyArea().get(App.getCurCityIndex()).getCityName());
                getActivity().sendBroadcast(new Intent(WeatherUpdateService.ACTION_SWITH_CITY));
                mWeatherListView.startAnimation(fadeIn);
                mWeatherListView.smoothScrollToPosition(0);
                simpleWeatherAdapter.appendToList(mSimpleWeatherModels.get(position));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}