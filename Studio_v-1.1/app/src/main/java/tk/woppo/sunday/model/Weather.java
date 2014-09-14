package tk.woppo.sunday.model;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

/**
 * Created by Ho on 2014/9/4.
 */
public class Weather extends BaseModel {

    /** 图片ID */
    public int img;

    /** 时间 */
    public int hour;

    /** 温度 */
    @SerializedName("temperature")
    public int temp;

    /** 华氏度 */
    public int humidity;

    /** 描述信息 */
    public String info;
}
