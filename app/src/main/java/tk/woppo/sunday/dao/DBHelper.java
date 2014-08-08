package tk.woppo.sunday.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import tk.woppo.sunday.model.WeatherTodayModel;

/**
 * Created by Ho on 2014/6/26.
 */
public class DBHelper extends SQLiteOpenHelper {

    protected static final String DB_NAME = "sunday.db";
    protected static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        WeatherDataHelper.WeatherDBInfo.TABLE.create(db);
        WeatherCurDataHelper.WeatherCurDBInfo.TABLE.create(db);
        WeatherTodayDataHelper.WeatherTodayDBInfo.TABLE.create(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
