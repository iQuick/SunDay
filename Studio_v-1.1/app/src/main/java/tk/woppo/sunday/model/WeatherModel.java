package tk.woppo.sunday.model;

import java.util.List;

/**
 * Created by Ho on 2014/9/4.
 */
public class WeatherModel extends BaseModel {

    /** 时间 */
    public String date;

    /** 天气信息 */
    public WeatherInfo info;

    public static class WeatherInfo {

        /** 晚上 */
        public List<String> night;

        /** 白天 */
        public List<String> day;
    }
}
