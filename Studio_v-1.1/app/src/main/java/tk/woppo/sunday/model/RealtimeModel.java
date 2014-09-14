package tk.woppo.sunday.model;

/**
 * Created by Ho on 2014/9/4.
 */
public class RealtimeModel extends BaseModel {

    /** 城市名称 */
    public String cityName;

    /** 风力信息 */
    public Wind wind;

    /** 时间 */
    public String time;

    /** 大气压 */
    public int pressure;

    /** 天气 */
    public Weather weather;

    /** 更新时间 */
    public int dataUptime;

    /** 时间 */
    public String date;

    public static class Wind {

        /** 风速 */
        public String windspeed;

        /** 风向 */
        public String direct;

        /** 风力 */
        public String power;
    }
}
