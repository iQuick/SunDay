package tk.woppo.sunday.util;

import tk.woppo.sunday.R;

/**
 * Created by Ho on 2014/7/9.
 */
public class WeatherUtil {

    /**
     * 获取天气ICON
     * @param weather
     * @return
     */
    public static int getIcon(String weather) {

        if (weather.indexOf("转") != -1) {
            if (weather.indexOf("到") != -1) {
                return getWeatherIcon(weather.split("转")[0].split("到")[1]);
            } else {
                return getWeatherIcon(weather.split("转")[0]);
            }
        } else {
            return getWeatherIcon(weather);
        }
    }

    public static int getWeatherIcon(String weather) {

        boolean isSun = DateUtil.isSun();
        if (weather.equals("晴")) {
            return isSun?R.drawable.w1_s:R.drawable.w1_m;
        } else if (weather.equals("阴")) {
            return R.drawable.w3;
        } else if (weather.equals("小雨")) {
            return R.drawable.w4;
        } else if (weather.equals("中雨")) {
            return R.drawable.w5_6;
        } else if (weather.equals("大雨")){
            return R.drawable.w7;
        } else if (weather.equals("阵雨") || weather.equals("暴雨") || weather.equals("大暴雨")) {
            return R.drawable.w8;
        } else if (weather.equals("雷阵雨")) {
            return R.drawable.w9;
        } else if (weather.equals("雨夹雪")) {
            return R.drawable.w10;
        }else if (weather.equals("小雪")) {
            return R.drawable.w11_12;
        } else if (weather.equals("中雪")) {
            return R.drawable.w13_14;
        } else if (weather.equals("大雪")) {
            return R.drawable.w15;
        } else if (weather.equals("暴雪") || weather.equals("大暴雪")) {
            return R.drawable.w16;
        } else if (weather.equals("冰雹")) {
            return R.drawable.w18;
        } else if (weather.equals("大雾")) {
            return R.drawable.w30;
        } else if (weather.equals("风")) {
            return R.drawable.w17;
        } else {
            return isSun?R.drawable.w2_s:R.drawable.w2_m;
        }
    }

    /**
     * 获取Drawer中的天气图片
     * @param weather
     * @return
     */
    public static int getImg(String weather) {

        if (weather.indexOf("转") != -1) {
            if (weather.indexOf("到") != -1) {
                return getWeatherImg(weather.split("转")[0].split("到")[1]);
            } else {
                return getWeatherImg(weather.split("转")[0]);
            }
        } else {
            return getWeatherImg(weather);
        }
    }

    public static int getWeatherImg(String weather) {

        boolean isSun = DateUtil.isSun();
        if (weather.indexOf("晴") != -1) {
            return isSun ? R.drawable.banner_fine_day : R.drawable.banner_fine_night;
        } else if (weather.indexOf("多云") != -1) {
            return isSun ? R.drawable.banner_cloudy_day : R.drawable.banner_cloudy_night;
        } else if (weather.indexOf("雾") != -1) {
            return isSun ? R.drawable.banner_fog_day : R.drawable.banner_fog_night;
        } else if (weather.indexOf("阴") != -1) {
            return R.drawable.banner_overcast;
        } else if (weather.indexOf("雷") != -1) {
            return R.drawable.banner_thunder_storm;
        } else if (weather.indexOf("雨") != -1) {
            return R.drawable.banner_rain;
        } else if (weather.indexOf("雪") != -1) {
            return R.drawable.banner_day_snow;
        } else if (weather.indexOf("沙尘暴") != -1) {
            return R.drawable.banner_sand_storm;
        } else {
            return R.drawable.banner_fine_day;
        }
    }
}
