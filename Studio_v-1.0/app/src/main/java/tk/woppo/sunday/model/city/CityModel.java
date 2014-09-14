package tk.woppo.sunday.model.city;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ho on 2014/6/26.
 */
public class CityModel extends BaseCityModel {

    /**
     * 下级区/县
     */
    private List<AreaModel> areaModels;

    public CityModel() {
        areaModels = new ArrayList<AreaModel>();
    }

    public List<AreaModel> getAreaModels() {
        return areaModels;
    }

    public void setAreaModels(List<AreaModel> areaModels) {
        this.areaModels = areaModels;
    }

    @Override
    public String toString() {
        return "CityModel{" +
                "areaModels=" + areaModels +
                '}';
    }
}
