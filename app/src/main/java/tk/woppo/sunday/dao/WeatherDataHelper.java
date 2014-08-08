package tk.woppo.sunday.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

import tk.woppo.sunday.model.CurWeatherModel;
import tk.woppo.sunday.model.WeatherModel;
import tk.woppo.sunday.util.db.Column;
import tk.woppo.sunday.util.db.SQLiteTable;

/**
 * Created by Ho on 2014/6/26.
 */
public class WeatherDataHelper extends BaseDataHelper {

    public WeatherDataHelper(Context context) {
        super(context);
    }

    @Override
    protected Uri getContentUri() {
        return DataProvider.WEATHER_CONTENT_URI;
    }

    private ContentValues getContentValues(WeatherModel model) {
        ContentValues values = new ContentValues();
        values.put(WeatherDBInfo.ID, model.id);
        values.put(WeatherDBInfo.JSON, model.toJson());
        return values;
    }

    public WeatherModel query(String id) {
        WeatherModel model = null;
        Cursor cursor = query(null, WeatherDBInfo.ID + " = ?", new String[] {id}, null);
        if (cursor.moveToFirst()) {
            model = WeatherModel.fromCursor(cursor);
        }
        cursor.close();
        return model;
    }

    public void insert(WeatherModel model) {
        insert(getContentValues(model));
    }

    public void bulkInsert(List<WeatherModel> models) {
        ArrayList<ContentValues> contentValueses = new ArrayList<ContentValues>();
        for (WeatherModel model : models) {
            ContentValues values = getContentValues(model);
            contentValueses.add(values);
        }
        ContentValues[] valueArray = new ContentValues[contentValueses.size()];
        bulkInsert(contentValueses.toArray(valueArray));
    }

    public int deleteAll() {
        synchronized (DataProvider.DBLock) {
            DBHelper mDbHelper = DataProvider.getDBHelper();
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            int row = db.delete(WeatherDBInfo.TABLE_NAME, null, null);
            return row;
        }
    }

    public static final class WeatherDBInfo implements BaseColumns {

        /** 表名 */
        public static final String TABLE_NAME = "Weathers";

        /** ID */
        public static final String ID = "id";

        /** JSON */
        public static final String JSON = "json";

        public static final SQLiteTable TABLE = new SQLiteTable(TABLE_NAME)
                .addColumn(ID, Column.DataType.INTEGER)
                .addColumn(JSON, Column.DataType.TEXT);
    }
}
