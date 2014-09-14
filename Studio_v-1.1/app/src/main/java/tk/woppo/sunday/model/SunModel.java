package tk.woppo.sunday.model;

import android.database.Cursor;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

import tk.woppo.sunday.dao.SunDataHelper;
import tk.woppo.sunday.util.LogUtil;

/**
 * Created by Ho on 2014/9/4.
 */
public class SunModel extends BaseModel {

    private static final HashMap<String, SunModel> CACHE = new HashMap<String, SunModel>();

    /** 城市ID */
    public String id;

    /** 生活信息 */
    public LifeModel life;

    /** 实时信息 */
    public RealtimeModel realtime;

    /** 天气信息 */
    public List<WeatherModel> weather;

    /** PM信息 */
    public PmModel pm;

    /** 每小时天气 */
    @SerializedName("hourly_forecast")
    public List<Weather> hourlyForecast;

    /** 地区 */
    public String area[][];


    private static void addToCache(SunModel model) {
        CACHE.put(model.id, model);
    }

    private static SunModel getFromCache(String id) {
        return CACHE.get(id);
    }

    public static SunModel fromJson(String json) {
        SunModel model = new Gson().fromJson(json, SunModel.class);
        model.id = model.area[2][1];
        model.realtime.cityName = model.area[2][0];
        return model;
    }

    public static SunModel fromCursor(Cursor cursor) {
        String id = cursor.getString(cursor.getColumnIndex(SunDataHelper.SunDBInfo.ID));
        SunModel model = getFromCache(id);
        if (model != null) {
            return model;
        }
        model = new Gson().fromJson(cursor.getString(cursor.getColumnIndex(SunDataHelper.SunDBInfo.JSON)), SunModel.class);
        addToCache(model);
        return model;
    }

    public static class SunRequestData {
        public SunModel data;
    }
}
