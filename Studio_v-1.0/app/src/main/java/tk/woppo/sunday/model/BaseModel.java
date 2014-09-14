package tk.woppo.sunday.model;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by Ho on 2014/6/26.
 */
public abstract class BaseModel implements Serializable {

    public String toJson() {
        return new Gson().toJson(this);
    }
}
