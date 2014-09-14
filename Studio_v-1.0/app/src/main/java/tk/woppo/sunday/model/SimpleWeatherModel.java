package tk.woppo.sunday.model;

import java.util.HashMap;

/**
 * Created by Ho on 2014/7/3.
 */
public class SimpleWeatherModel extends BaseModel {

    /**
     * 温度
     */
    private String temp;

    /**
     * 天气
     */
    private String weather;

    /**
     * 星期
     */
    private String week;

    public SimpleWeatherModel(String week, String weather, String temp) {
        this.week = week;
        this.weather = weather;
        this.temp = temp;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    @Override
    public String toString() {
        return "SimpleWeatherModel{" +
                "temp='" + temp + '\'' +
                ", weather='" + weather + '\'' +
                ", week='" + week + '\'' +
                '}';
    }
}
