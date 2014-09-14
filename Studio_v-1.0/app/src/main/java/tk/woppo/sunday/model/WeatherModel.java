package tk.woppo.sunday.model;

import android.database.Cursor;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tk.woppo.sunday.dao.WeatherDataHelper;

/**
 * Created by Ho on 2014/6/26.
 */
public class WeatherModel extends BaseModel {

    private static final HashMap<String, WeatherModel> CACHE = new HashMap<String, WeatherModel>();

    /** 城市ID */
    @SerializedName("cityid")
    public String id;
    /** 城市名称 */
    public String city;
    /** 发布日期 */
    @SerializedName("date_y")
    public String date;
    /** 星期 */
    public String week;

    /** 今天-6天温度 */
    public String temp1;
    public String temp2;
    public String temp3;
    public String temp4;
    public String temp5;
    public String temp6;

    /** 今天-6天天气 */
    public String weather1;
    public String weather2;
    public String weather3;
    public String weather4;
    public String weather5;
    public String weather6;

    /** 舒适度 */
    public String index;
    /** 穿衣建议 */
    public String index_d;
    /** 紫外线信息 */
    public String index_uv;
    /** 洗车指数 */
    public String index_xc;
    /** 旅游指数 */
    public String index_tr;
    /** 舒适指数 */
    public String index_co;
    /** 晨练指数 */
    public String index_cl;
    /** 晾晒指数 */
    public String index_ls;
    /** 感冒指数 */
    public String index_ag;

    private static void addToCache(WeatherModel model) {
        CACHE.put(model.id, model);
    }

    private static WeatherModel getFromCache(String id) {
        return CACHE.get(id);
    }

    public static WeatherModel fromJson(String json) {
        return new Gson().fromJson(json, WeatherModel.class);
    }

    public static WeatherModel fromCursor(Cursor cursor) {
        String id = cursor.getString(cursor.getColumnIndex(WeatherDataHelper.WeatherDBInfo.ID));
        WeatherModel model = getFromCache(id);
        if (model != null) {
            return model;
        }
        model = new Gson().fromJson(cursor.getString(cursor.getColumnIndex(WeatherDataHelper.WeatherDBInfo.JSON)), WeatherModel.class);
        addToCache(model);
        return model;
    }

    public static class WeatherRequestData {
        public WeatherModel weatherinfo;
    }

    /**
     * 以List形式输出六天天气
     * @return List<SimpleWeatherModel>
     */
    public List<SimpleWeatherModel> toSimpleWeatherList() {
        List<SimpleWeatherModel> list = new ArrayList<SimpleWeatherModel>();

        list.add(new SimpleWeatherModel(week, weather1, temp1));
        list.add(new SimpleWeatherModel(getWeek(getWeekInt(week) + 1), weather2, temp2));
        list.add(new SimpleWeatherModel(getWeek(getWeekInt(week) + 2), weather3, temp3));
        list.add(new SimpleWeatherModel(getWeek(getWeekInt(week) + 3), weather4, temp4));
        list.add(new SimpleWeatherModel(getWeek(getWeekInt(week) + 4), weather5, temp5));
        list.add(new SimpleWeatherModel(getWeek(getWeekInt(week) + 5), weather6, temp6));

        return list;
    }

    private int getWeekInt(String week) {
        if (week.equals("星期一")) {
            return 1;
        } else if (week.equals("星期二")) {
            return 2;
        } else if (week.equals("星期三")) {
            return 3;
        } else if (week.equals("星期四")) {
            return 4;
        } else if (week.equals("星期五")) {
            return 5;
        } else if (week.equals("星期六")) {
            return 6;
        } else if (week.equals("星期日")) {
            return 7;
        }
        return 1;
    }

    private String getWeek(int week) {
        switch (week % 7) {
            case 0:
                return "星期日";
            case 1:
                return "星期一";
            case 2:
                return "星期二";
            case 3:
                return "星期三";
            case 4:
                return "星期四";
            case 5:
                return "星期五";
            case 6:
                return "星期六";
        }
        return "星期一";
    }

    @Override
    public String toString() {
        return "WeatherModel{" +
                "id='" + id + '\'' +
                ", city='" + city + '\'' +
                ", date='" + date + '\'' +
                ", week='" + week + '\'' +
                ", temp1='" + temp1 + '\'' +
                ", temp2='" + temp2 + '\'' +
                ", temp3='" + temp3 + '\'' +
                ", temp4='" + temp4 + '\'' +
                ", temp5='" + temp5 + '\'' +
                ", temp6='" + temp6 + '\'' +
                ", weather1='" + weather1 + '\'' +
                ", weather2='" + weather2 + '\'' +
                ", weather3='" + weather3 + '\'' +
                ", weather4='" + weather4 + '\'' +
                ", weather5='" + weather5 + '\'' +
                ", weather6='" + weather6 + '\'' +
                ", index='" + index + '\'' +
                ", index_d='" + index_d + '\'' +
                ", index_uv='" + index_uv + '\'' +
                ", index_xc='" + index_xc + '\'' +
                ", index_tr='" + index_tr + '\'' +
                ", index_co='" + index_co + '\'' +
                ", index_cl='" + index_cl + '\'' +
                ", index_ls='" + index_ls + '\'' +
                ", index_ag='" + index_ag + '\'' +
                '}';
    }
}
