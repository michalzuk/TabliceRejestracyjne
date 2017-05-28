import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;ackage com.rubybash.tablicerejestracyjne.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.rubybash.tablicerejestracyjne.Models.Diplomatic;


/**
 * Created by michalzuk on 07.05.17.
 */

public class DatabaseAdapter extends BaseAdapter {

    //BASIC DB INFO
    public static final String DATABASE_NAME = "TabliceRejestracyjne";
    public static final String TAG = "DatabaseAdapter";
    public static final int DATABASE_VERSION = 1;


    //Database Tables
    public static final String TABLE_PROVINCE = "table_province";
    public static final String TABLE_DIPLOMATIC = "table_diplomatic";
    public static final String TABLE_UNIFORMED = "table_uniformed";


    //Common Database Rows
    public static final String KEY_ROWID = "_id";
    public static final String KEY_SHORTCUT = "shortcut";


    //Province Database Rows (Other)
    public static final String KEY_CITY = "city";
    public static final String KEY_PROVINCE = "province";


    //Dimplomatic Database Rows (Other)
    public static final String KEY_COUNTRY = "country";


    //Uniformed Services Database Rows (Other)
    public static final String KEY_SERVICE = "service";
    public static final String KEY_ADDITIONAL = "additional";

    private final Context mContext;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase mDb;


    private static final String TABLE_PROVINCE_CREATE() {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS ").append(TABLE_PROVINCE).append(" ( ")
                .append(KEY_ROWID).append(" INTEGER PRIMARY KEY autoincrement,")
                .append(KEY_SHORTCUT).append(",")
                .append(KEY_CITY).append(" ,")
                .append(KEY_PROVINCE).append(" );");

        return sb.toString();
    }

    private static final String TABLE_DIPLOMATIC_CREATE() {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS ").append(TABLE_DIPLOMATIC).append(" ( ")
                .append(KEY_ROWID).append(" INTEGER PRIMARY KEY autoincrement,")
                .append(KEY_SHORTCUT).append(",")
                .append(KEY_COUNTRY).append(" );");

        return sb.toString();
    }

    private static final String TABLE_UNIFORMED_CREATE() {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS ").append(TABLE_UNIFORMED).append(" ( ")
                .append(KEY_ROWID).append(" INTEGER PRIMARY KEY autoincrement,")
                .append(KEY_SHORTCUT).append(",")
                .append(KEY_SERVICE).append(",")
                .append(KEY_ADDITIONAL).append(" );");

        return sb.toString();
    }


    private static final String[] PROVINCE_ROWS() {
        String[] rows = new String[]{KEY_ROWID,
                KEY_SHORTCUT, KEY_CITY, KEY_PROVINCE};

        return rows;
    }

    private static final String[] DIPLOMATIC_ROWS() {
        String[] rows = new String[]{KEY_ROWID,
                KEY_SHORTCUT, KEY_COUNTRY};

        return rows;
    }

    private static final String[] UNIFORMED_ROWS() {
        String[] rows = new String[]{KEY_ROWID,
                KEY_SHORTCUT, KEY_SERVICE, KEY_ADDITIONAL};

        return rows;
    }


    public DatabaseAdapter(Context context) {
        this.mContext = context;
    }

    public DatabaseAdapter open() throws SQLException {
        dbHelper = new DatabaseHelper(mContext);
        mDb = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        if (dbHelper != null) {
            dbHelper.close();
        }
    }


    public long addProvinceTable(String shortcut, String city, String province) {
        ContentValues values = new ContentValues();
        values.put(KEY_SHORTCUT, shortcut);
        values.put(KEY_CITY, city);
        values.put(KEY_PROVINCE, province);

        return mDb.insert(TABLE_PROVINCE, null, values);
    }

    public long addDiplomaticTable(String shortcut, String country) {
        ContentValues values = new ContentValues();
        values.put(KEY_SHORTCUT, shortcut);
        values.put(KEY_COUNTRY, country);

        return mDb.insert(TABLE_DIPLOMATIC, null, values);
    }

    public long addUniformedTable(String shortcut, String service, String additional) {
        ContentValues values = new ContentValues();
        values.put(KEY_SHORTCUT, shortcut);
        values.put(KEY_SERVICE, service);
        values.put(KEY_ADDITIONAL, additional);

        return mDb.insert(TABLE_UNIFORMED, null, values);
    }


    public boolean dropProvinceTable() {

        int removed = 0;
        removed = mDb.delete(TABLE_PROVINCE, null, null);
        Log.w(TAG, Integer.toString(removed));
        return removed > 0;
    }

    public boolean dropDiplomaticTable() {

        int removed = 0;
        removed = mDb.delete(TABLE_DIPLOMATIC, null, null);
        Log.w(TAG, Integer.toString(removed));
        return removed > 0;
    }

    public boolean dropUniformedTable() {

        int removed = 0;
        removed = mDb.delete(TABLE_UNIFORMED, null, null);
        Log.w(TAG, Integer.toString(removed));
        return removed > 0;
    }

    public Cursor fetchProvinceTablice() {

        Cursor cursor = mDb.query(TABLE_PROVINCE, PROVINCE_ROWS(), null, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        return cursor;
    }

    public Cursor fetchDimplomaticTablice() {

        Cursor cursor = mDb.query(TABLE_DIPLOMATIC, DIPLOMATIC_ROWS(), null, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        return cursor;
    }

    public Cursor fetchUniformedTablice() {

        Cursor cursor = mDb.query(TABLE_UNIFORMED, UNIFORMED_ROWS(), null, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        return cursor;
    }


    public Cursor fetchProvinceByShortcut(String inputText) throws SQLException {
        Log.w(TAG, inputText);
        Cursor cursor = null;
        if (inputText == null || inputText.length() == 0) {
            cursor = mDb.query(TABLE_PROVINCE, PROVINCE_ROWS(), null, null, null, null, null, null);
        } else {
            cursor = mDb.query(true, TABLE_PROVINCE, PROVINCE_ROWS(), KEY_SHORTCUT + " like '%" + inputText + "%'",
                    null, null, null, null, null);
        }
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchDiplomaticByShortcut(String inputText) throws SQLException {
        Log.w(TAG, inputText);
        Cursor cursor = null;
        if (inputText == null || inputText.length() == 0) {
            cursor = mDb.query(TABLE_DIPLOMATIC, DIPLOMATIC_ROWS(), null, null, null, null, null, null);
        } else {
            cursor = mDb.query(true, TABLE_DIPLOMATIC, DIPLOMATIC_ROWS(), KEY_SHORTCUT + " like '%" + inputText + "%'",
                    null, null, null, null, null);
        }
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchUniformedByShortcut(String inputText) throws SQLException {
        Log.w(TAG, inputText);
        Cursor cursor = null;
        if (inputText == null || inputText.length() == 0) {
            cursor = mDb.query(TABLE_UNIFORMED, UNIFORMED_ROWS(), null, null, null, null, null, null);
        } else {
            cursor = mDb.query(true, TABLE_UNIFORMED, UNIFORMED_ROWS(), KEY_SHORTCUT + " like '%" + inputText + "%'",
                    null, null, null, null, null);
        }
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }


    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }


    public class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.w(TAG, DATABASE_NAME);
            db.execSQL(TABLE_PROVINCE_CREATE());
            db.execSQL(TABLE_DIPLOMATIC_CREATE());
            db.execSQL(TABLE_UNIFORMED_CREATE());
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Latest database version:" + oldVersion + "Has been updated to version:" + newVersion);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROVINCE);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIPLOMATIC);
            onCreate(db);
        }
    }
}