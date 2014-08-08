package tk.woppo.sunday.model;

import android.database.Cursor;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

import tk.woppo.sunday.dao.WeatherDataHelper;

/**
 * Created by Ho on 2014/7/8.
 */
public class CurWeatherModel extends BaseModel {

    private static final HashMap<String, CurWeatherModel> CACHE = new HashMap<String, CurWeatherModel>();

    @SerializedName("cityid")
    public String id;

    @SerializedName("city")
    public String cityName;

    public String weather;

    @SerializedName("ptime")
    public String time;

    private static void addToCache(CurWeatherModel model) {
        CACHE.put(model.id, model);
    }

    private static CurWeatherModel getFromCache(String id) {
        return CACHE.get(id);
    }

    public static WeatherModel fromJson(String json) {
        return new Gson().fromJson(json, WeatherModel.class);
    }

    public static CurWeatherModel fromCursor(Cursor cursor) {
        String id = cursor.getString(cursor.getColumnIndex(WeatherDataHelper.WeatherDBInfo.ID));
        CurWeatherModel model = getFromCache(id);
        if (model != null) {
            return model;
        }
        model = new Gson().fromJson(cursor.getString(cursor.getColumnIndex(WeatherDataHelper.WeatherDBInfo.JSON)), CurWeatherModel.class);
        addToCache(model);
        return model;
    }

    public static class CurWeatherRequestData {
        public CurWeatherModel weatherinfo;
    }
}
