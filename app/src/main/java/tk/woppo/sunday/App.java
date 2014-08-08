package tk.woppo.sunday;

import android.app.Application;
import android.content.Context;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EApplication;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import tk.woppo.sunday.domain.CitySaxParseHandler;
import tk.woppo.sunday.model.WeatherModel;
import tk.woppo.sunday.model.city.AreaModel;
import tk.woppo.sunday.model.city.ProvicneModel;
import tk.woppo.sunday.util.FileUtil;
import tk.woppo.sunday.util.LogUtil;

/**
 * Created by Ho on 2014/6/25.
 */

@EApplication
public class App extends Application {

    protected final static String TAG = "App";

    private static Context mContext;

    /** 城市列表 */
    private static List<ProvicneModel> mProvicneModels;
    private static List<AreaModel> mAreaModels;
    public static WeatherModel mCurWeatherModel;
    private static int mCurWeatherIndex;

    @AfterInject
    void init() {

        this.mContext = getApplicationContext();
        this.mAreaModels = new ArrayList<AreaModel>();
        this.mCurWeatherIndex = 0;
        this.initMyArea();
        this.initProvicneModels();
    }

    public static Context getContext() {
        return mContext;
    }

    /**
     * 初始化城市列表
     */
    private void initProvicneModels() {
        try {
            InputStream in = getAssets().open(Const.FILE_CITY_NAME);
            mProvicneModels = CitySaxParseHandler.getProvicneModel(in);
            LogUtil.i(TAG, mProvicneModels.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static List<ProvicneModel> getProvicneModels() {
        return mProvicneModels;
    }

    /**
     * 初始化我的城市
     */
    private void initMyArea() {
        try {
            List<AreaModel> models = (List<AreaModel>) FileUtil.readObjsFromFile(Const.FILE_MY_AREA);
            if (models != null) {
                mAreaModels.addAll(models);
                LogUtil.i(TAG, mAreaModels.get(0).getCityName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setCurCityIndex(int index) {
        mCurWeatherIndex = index;
    }

    public static int getCurCityIndex() {
        return mCurWeatherIndex;
    }

    /**
     * 添加我的城市
     * @param model
     * @return
     */
    public static String addMyArea(AreaModel model) {
        if (model == null) {
            LogUtil.i(TAG, "null");
            return null;
        }

        if (mAreaModels.size() >= 5) {
            return getContext().getString(R.string.city_exceed_num);
        } else {
            for (AreaModel areaModel : mAreaModels) {
                if (areaModel.getCityId().equals(model.getCityId())) {
                    return getContext().getString(R.string.city_already_exists);
                }
            }
            //添加到第一位
            mAreaModels.add(0, model);
            // 重新保存文件
            FileUtil.writeObjsToFile(mAreaModels, Const.FILE_MY_AREA, Context.MODE_PRIVATE);
            // 返回添加城市结果信息
            return getContext().getString(R.string.city_add_success);
        }
    }

    /**
     * 删除城市信息
     * @param position
     * @return
     */
    public static AreaModel removeMyArea(int position) {
        // 删除数据
        AreaModel model = mAreaModels.remove(position);
        // 重新保存文件
        FileUtil.writeObjsToFile(mAreaModels, Const.FILE_MY_AREA, Context.MODE_PRIVATE);
        return model;
    }
    public static boolean removeMyArea(AreaModel areaModel) {
        // 删除数据
        boolean is = mAreaModels.remove(areaModel);
        // 重新保存文件
        FileUtil.writeObjsToFile(mAreaModels, Const.FILE_MY_AREA, Context.MODE_PRIVATE);
        return is;
    }

    public static List<AreaModel> getMyArea() {
        return mAreaModels;
    }
}
