package tk.woppo.sunday.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

import tk.woppo.sunday.model.WeatherModel;
import tk.woppo.sunday.model.WeatherTodayModel;
import tk.woppo.sunday.util.db.Column;
import tk.woppo.sunday.util.db.SQLiteTable;

/**
 * Created by Ho on 2014/7/4.
 */
public class WeatherTodayDataHelper extends BaseDataHelper {

    public WeatherTodayDataHelper(Context context) {
        super(context);
    }

    @Override
    protected Uri getContentUri() {
        return DataProvider.WEATHER_CONTENT_TODAY_URI;
    }

    private ContentValues getContentValues(WeatherTodayModel model) {
        ContentValues values = new ContentValues();
        values.put(WeatherTodayDBInfo.ID, model.id);
        values.put(WeatherTodayDBInfo.JSON, model.toJson());
        return values;
    }

    public WeatherTodayModel query(String id) {
        WeatherTodayModel model = null;
        Cursor cursor = query(null, WeatherTodayDBInfo.ID + " = ?", new String[] {id}, null);
        if (cursor.moveToFirst()) {
            model = WeatherTodayModel.fromCursor(cursor);
        }
        cursor.close();
        return model;
    }

    public void insert(WeatherTodayModel model) {
        insert(getContentValues(model));
    }

    public void bulkInsert(List<WeatherTodayModel> models) {
        ArrayList<ContentValues> contentValueses = new ArrayList<ContentValues>();
        for (WeatherTodayModel model : models) {
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
            int row = db.delete(WeatherTodayDBInfo.TABLE_NAME, null, null);
            return row;
        }
    }

    public static final class WeatherTodayDBInfo implements BaseColumns {

        /** 表名 */
        public static final String TABLE_NAME = "WeatherToday";

        /** ID */
        public static final String ID = "id";

        /** JSON */
        public static final String JSON = "json";

        public static final SQLiteTable TABLE = new SQLiteTable(TABLE_NAME)
                .addColumn(ID, Column.DataType.INTEGER)
                .addColumn(JSON, Column.DataType.TEXT);
    }
}
