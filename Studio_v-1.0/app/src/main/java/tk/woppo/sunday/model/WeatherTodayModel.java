package tk.woppo.sunday.model;

import android.database.Cursor;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

import tk.woppo.sunday.dao.WeatherDataHelper;
import tk.woppo.sunday.dao.WeatherTodayDataHelper;

/**
 * Created by Ho on 2014/7/4.
 */
public class WeatherTodayModel extends BaseModel {

    private static final HashMap<String, WeatherTodayModel> CACHE = new HashMap<String, WeatherTodayModel>();

    /** 城市ID */
    @SerializedName("cityid")
    public String id;

    /** 城市名称 */
    @SerializedName("city")
    public String cityName;

    /** 温度 */
    public String temp;

    /** 天气 */
    public String weather;

    /** 风向 */
    @SerializedName("WD")
    public String wind;

    /** 风力 */
    @SerializedName("WS")
    public String ws;

    /** 湿度 */
    @SerializedName("SD")
    public String sd;

    /** 发布时间 */
    public String time;

    private static void addToCache(WeatherTodayModel model) {
        CACHE.put(model.id, model);
    }

    private static WeatherTodayModel getFromCache(String id) {
        return CACHE.get(id);
    }

    public static WeatherTodayModel fromJson(String json) {
        return new Gson().fromJson(json, WeatherTodayModel.class);
    }

    public static WeatherTodayModel fromCursor(Cursor cursor) {
        String id = cursor.getString(cursor.getColumnIndex(WeatherDataHelper.WeatherDBInfo.ID));
        WeatherTodayModel model = getFromCache(id);
        if (model != null) {
            return model;
        }
        model = new Gson().fromJson(cursor.getString(cursor.getColumnIndex(WeatherTodayDataHelper.WeatherTodayDBInfo.JSON)), WeatherTodayModel.class);
        addToCache(model);
        return model;
    }

    public static class WeatherTodayRequestData {
        public WeatherTodayModel weatherinfo;
    }
}
