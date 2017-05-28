package com.rubybash.tablicerejestracyjne.Database;

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


    public void provinceData() {


        // B - Podlaskie
        addProvinceTable("BAU", "Augustów", "Podlaskie");
        addProvinceTable("BBI", "Bielsk Podlaski", "Podlaskie");
        addProvinceTable("BGR", "Grajewo", "Podlaskie");
        addProvinceTable("BHA", "Hajnówka", "Podlaskie");
        addProvinceTable("BI", "Białystok", "Podlaskie");
        addProvinceTable("BIA", "Białystok powiat",  "Podlaskie");
        addProvinceTable("BKL", "Kolno", "Podlaskie");
        addProvinceTable("BL", "Łomża", "Podlaskie");
        addProvinceTable("BLM", "Łomża powiat", "Podlaskie");
        addProvinceTable("BMN", "Mońki", "Podlaskie");
        addProvinceTable("BS", "Suwałki", "Podlaskie");
        addProvinceTable("BSE", "Sejny", "Podlaskie");
        addProvinceTable("BSI", "Siemiatycze", "Podlaskie");
        addProvinceTable("BSK", "Sokółka", "Podlaskie");
        addProvinceTable("BSU", "Suwałki powiat", "Podlaskie");
        addProvinceTable("BWM", "Wysokie Mazowieckie", "Podlaskie");
        addProvinceTable("BZA", "Zambrów", "Podlaskie");



        // C - Kujawsko - Pomorskie
        addProvinceTable("CAL", "Aleksandrów Kujawski", "Kujawsko-Pomorskie");
        addProvinceTable("CB", "Bydgoszcz", "Kujawsko-Pomorskie");
        addProvinceTable("CBR", "Brodnica", "Kujawsko-Pomorskie");
        addProvinceTable("CBY", "Bydgoszcz powiat", "Kujawsko-Pomorskie");
        addProvinceTable("CCH", "Chełmno", "Kujawsko-Pomorskie");
        addProvinceTable("CG", "Grudziądz", "Kujawsko-Pomorskie");
        addProvinceTable("CGD", "Golub-Dobrzyń", "Kujawsko-Pomorskie");
        addProvinceTable("CGR", "Grudziądz powiat", "Kujawsko-Pomorskie");
        addProvinceTable("CIN", "Inowrocław", "Kujawsko-Pomorskie");
        addProvinceTable("CLI", "Lipno", "Kujawsko-Pomorskie");
        addProvinceTable("CMG", "Mogilno", "Kujawsko-Pomorskie");
        addProvinceTable("CNA", "Nakło n. Notecią", "Kujawsko-Pomorskie");
        addProvinceTable("CRA", "Radziejów", "Kujawsko-Pomorskie");
        addProvinceTable("CSE", "Sępólno Krajeńskie", "Kujawsko-Pomorskie");
        addProvinceTable("CSW", "Świecie", "Kujawsko-Pomorskie");
        addProvinceTable("CT", "Toruń", "Kujawsko-Pomorskie");
        addProvinceTable("CTR", "Toruń powiat", "Kujawsko-Pomorskie");
        addProvinceTable("CTU", "Tuchola", "Kujawsko-Pomorskie");
        addProvinceTable("CW", "Włocławek", "Kujawsko-Pomorskie");
        addProvinceTable("CWA", "Wąbrzeźno", "Kujawsko-Pomorskie");
        addProvinceTable("CWL", "Włocławek powiat", "Kujawsko-Pomorskie");
        addProvinceTable("CZN", "Żnin", "Kujawsko-Pomorskie");



        // D - Dolnośląskie
        addProvinceTable("DB", "Wałbrzych", "Dolnośląskie");
        addProvinceTable("DBA", "Wałbrzych powiat", "Dolnośląskie");
        addProvinceTable("DBL", "Bolesławiec", "Dolnośląskie");
        addProvinceTable("DDZ", "Dzierżoniów", "Dolnośląskie");
        addProvinceTable("DGL", "Głogów", "Dolnośląskie");
        addProvinceTable("DGR", "Góra", "Dolnośląskie");
        addProvinceTable("DJ", "Jelenia Góra", "Dolnośląskie");
        addProvinceTable("DJA", "Jawor", "Dolnośląskie");
        addProvinceTable("DJE", "Jelenia Góra", "Dolnośląskie");
        addProvinceTable("DKA", "Kamienna Góra", "Dolnośląskie");
        addProvinceTable("DKL", "Kłodzko", "Dolnośląskie");
        addProvinceTable("DL", "Legnica", "Dolnośląskie");
        addProvinceTable("DLB", "Lubań", "Dolnośląskie");
        addProvinceTable("DLE", "Legnica", "Dolnośląskie");
        addProvinceTable("DLU", "Lubin", "Dolnośląskie");
        addProvinceTable("DLW", "Lwówek", "Dolnośląskie");
        addProvinceTable("DMI", "Milicz", "Dolnośląskie");
        addProvinceTable("DOA", "Oława", "Dolnośląskie");
        addProvinceTable("DOL", "Oleśnica", "Dolnośląskie");
        addProvinceTable("DPL", "Polkowice", "Dolnośląskie");
        addProvinceTable("DSR", "Środa Śląska", "Dolnośląskie");
        addProvinceTable("DST", "Strzelin", "Dolnośląskie");
        addProvinceTable("DSW", "Świdnica", "Dolnośląskie");
        addProvinceTable("DTR", "Trzebnica", "Dolnośląskie");
        addProvinceTable("DW", "Wrocław", "Dolnośląskie");
        addProvinceTable("DWL", "Wołów", "Dolnośląskie");
        addProvinceTable("DWR", "Wrocław powiat", "Dolnośląskie");
        addProvinceTable("DZA", "Ząbkowice Śląskie", "Dolnośląskie");
        addProvinceTable("DZG", "Zgorzelec", "Dolnośląskie");
        addProvinceTable("DZL", "Złotoryja", "Dolnośląskie");



        // E - Łódzkie
        addProvinceTable("EBE", "Bełchatów", "Łódzkie");
        addProvinceTable("EBR", "Brzeziny", "Łódzkie");
        addProvinceTable("EKU", "Kutno", "Łódzkie");
        addProvinceTable("EL", "Łódź", "Łódzkie");
        addProvinceTable("ELA", "Łask", "Łódzkie");
        addProvinceTable("ELC", "Łowicz", "Łódzkie");
        addProvinceTable("ELE", "Łęczyca", "Łódzkie");
        addProvinceTable("ELW", "Łódź powiat", "Łódzkie");
        addProvinceTable("EOP", "Opoczno", "Łódzkie");
        addProvinceTable("EP", "Piotrków Trybunalski", "Łódzkie");
        addProvinceTable("EPA", "Pabianice", "Łódzkie");
        addProvinceTable("EPD", "Poddębie", "Łódzkie");
        addProvinceTable("EPI", "Piotrów Tryb. powiat", "Łódzkie");
        addProvinceTable("EPJ", "Pajęczno", "Łódzkie");
        addProvinceTable("ERA", "Radomsko", "Łódzkie");
        addProvinceTable("ERW", "Rawa Mazowiecka", "Łódzkie");
        addProvinceTable("ES", "Skierniewice", "Łódzkie");
        addProvinceTable("ESI", "Sieradz", "Łódzkie");
        addProvinceTable("ESK", "Skierniewice powiat", "Łódzkie");
        addProvinceTable("ETM", "Tomaszów Mazowiecki", "Łódzkie");
        addProvinceTable("EWE", "Wieruszów", "Wieluń");
        addProvinceTable("EWI", "Wieluń", "Łódzkie");
        addProvinceTable("EZD", "Zduńska Wola", "Łódzkie");
        addProvinceTable("EZG", "Zgierz", "Łódzkie");



        // F - Lubuskie
        addProvinceTable("FG", "Gorzów Wielkopolski", "Lubuskie");
        addProvinceTable("FGW", "Gorzów Wlkp. Powiat", "Lubuskie");
        addProvinceTable("FKR", "Krosno Odrzańskie", "Lubuskie");
        addProvinceTable("FMI", "Międzyrzecz", "Lubuskie");
        addProvinceTable("FNW", "Nowa Sól", "Lubuskie");
        addProvinceTable("FSD", "Strzelce Krajeńskie", "Lubuskie");
        addProvinceTable("FSL", "Słubice", "Lubuskie");
        addProvinceTable("FSU", "Sulęcin", "Lubuskie");
        addProvinceTable("FSW", "Świebodzin", "Lubuskie");
        addProvinceTable("FWS", "Wschowa", "Lubuskie");
        addProvinceTable("FZ", "Zielona Góra", "Lubuskie");
        addProvinceTable("FZA", "Żary", "Lubuskie");
        addProvinceTable("FZG", "Żagań", "Lubuskie");
        addProvinceTable("FZI", "Zielona Góra powiat", "Lubuskie");



        // G - Pomorskie
        addProvinceTable("GA", "Gdynia", "Pomorskie");
        addProvinceTable("GBY", "Bytów", "Pomorskie");
        addProvinceTable("GCH", "Chojnice", "Pomorskie");
        addProvinceTable("GCZ", "Człuchów", "Pomorskie");
        addProvinceTable("GD", "Gdańsk", "Pomorskie");
        addProvinceTable("GDA", "Pruszcz Gdański", "Pomorskie");
        addProvinceTable("GKA", "Kartuzy", "Pomorskie");
        addProvinceTable("GKS", "Kościerzyna", "Pomorskie");
        addProvinceTable("GKW", "Kwidzyn", "Pomorskie");
        addProvinceTable("GLE", "Lębork", "Pomorskie");
        addProvinceTable("GMB", "Malbork", "Pomorskie");
        addProvinceTable("GND", "Nowy Dwór Gdański", "Pomorskie");
        addProvinceTable("GPU", "Puck", "Pomorskie");
        addProvinceTable("GS", "Słupsk", "Pomorskie");
        addProvinceTable("GSL", "Słupsk powiat", "Pomorskie");
        addProvinceTable("GSP", "Sopot", "Pomorskie");
        addProvinceTable("GST", "Starogard Gdański", "Pomorskie");
        addProvinceTable("GSZ", "Sztum", "Pomorskie");
        addProvinceTable("GTC", "Tczew", "Pomorskie");
        addProvinceTable("GWE", "Wejherowo", "Pomorskie");



        // K - Małopolskie
        addProvinceTable("KBC", "Bochnia", "Małopolskie");
        addProvinceTable("KBR", "Brzesko", "Małopolskie");
        addProvinceTable("KCH", "Chrzanów", "Małopolskie");
        addProvinceTable("KDA", "Dąbrowa Tarnowska", "Małopolskie");
        addProvinceTable("KGR", "Gorlice", "Małopolskie");
        addProvinceTable("KLI", "Limanowa", "Małopolskie");
        addProvinceTable("KMI", "Miechów", "Małopolskie");
        addProvinceTable("KMY", "Myślenice", "Małopolskie");
        addProvinceTable("KN","Nowy Sącz", "Małopolskie");
        addProvinceTable("KNS", "Nowy Sącz powiat", "Małopolskie");
        addProvinceTable("KNT", "Nowy Targ", "Małopolskie");
        addProvinceTable("KOL", "Olkusz", "Małopolskie");
        addProvinceTable("KOS", "Oświęcim", "Małopolskie");
        addProvinceTable("KPR", "Proszowice", "Małopolskie");
        addProvinceTable("KR", "Kraków", "Małopolskie");
        addProvinceTable("KRA", "Kraków powiat", "Małopolskie");
        addProvinceTable("KSU", "Sucha Beskidzka", "Małopolskie");
        addProvinceTable("KT", "Tarnów", "Małopolskie");
        addProvinceTable("KTA", "Tarnów powiat", "Małopolskie");
        addProvinceTable("KTT", "Zakopane", "Małopolskie");
        addProvinceTable("KWA", "Wadowice", "Małopolskie");
        addProvinceTable("KWI", "Wieliczka", "Małopolskie");



        // L - Lubelskie
        addProvinceTable("LB", "Biała Podlaska", "Lubelskie");
        addProvinceTable("LBI", "Biała Podlaska powiat", "Lubelskie");
        addProvinceTable("LBL", "Biłograj", "Lubelskie");
        addProvinceTable("LC", "Chełm", "Lubelskie");
        addProvinceTable("LCH", "Chełm powiat", "Lubelskie");
        addProvinceTable("LHR", "Hrubieszów", "Lubelskie");
        addProvinceTable("LJA", "Janów Lubelski", "Lubelskie");
        addProvinceTable("LKR", "Krańsnik", "Lubelskie");
        addProvinceTable("LKS", "Krasnystaw", "Lubelskie");
        addProvinceTable("LLB", "Lubartów", "Lubelskie");
        addProvinceTable("LLE", "Łęczna", "Lubelskie");
        addProvinceTable("LLU", "Łuków", "Lubelskie");
        addProvinceTable("LOP", "Opole Lubelskie", "Lubelskie");
        addProvinceTable("LPA", "Parczew", "Lubelskie");
        addProvinceTable("LPU", "Puławy", "Lubelskie");
        addProvinceTable("LRA", "Radzyń Podlaski", "Lubelskie");
        addProvinceTable("LRY", "Ryki", "Lubelskie");
        addProvinceTable("LSW", "Świdnik", "Lubelskie");
        addProvinceTable("LTM", "Tomaszów Lubelski", "Lubelskie");
        addProvinceTable("LU", "Lublin", "Lubelskie");
        addProvinceTable("LUB", "Lublin powiat", "Lubelskie");
        addProvinceTable("LWL", "Włodawa", "Lubelskie");
        addProvinceTable("LZ", "Zamość", "Lubelskie");
        addProvinceTable("LZA", "Zamość powiat", "Lubelskie");



        // N - Warmińsko-Mazurskie
        addProvinceTable("NBA", "Bartoszyce", "Warmińsko-Mazurskie");
        addProvinceTable("NBR", "Braniewo", "Warmińsko-Mazurskie");
        addProvinceTable("NDZ", "Działdowo", "Warmińsko-Mazurskie");
        addProvinceTable("NE", "Elbląg", "Warmińsko-Mazurskie");
        addProvinceTable("NEB", "Elbląg powiat", "Warmińsko-Mazurskie");
        addProvinceTable("NEL", "Ełk", "Warmińsko-Mazurskie");
        addProvinceTable("NGI", "Giżycko", "Warmińsko-Mazurskie");
        addProvinceTable("NGO", "Gołdap", "Warmińsko-Mazurskie");
        addProvinceTable("NIL", "Iława", "Warmińsko-Mazurskie");
        addProvinceTable("NKE", "Kętrzyn", "Warmińsko-Mazurskie");
        addProvinceTable("NLI", "Lidzbark Warmiński", "Warmińsko-Mazurskie");
        addProvinceTable("NMR", "Mrągowo", "Warmińsko-Mazurskie");
        addProvinceTable("NNI", "Nidzica", "Warmińsko-Mazurskie");
        addProvinceTable("NNM", "Nowe Miasto Lub.", "Warmińsko-Mazurskie");
        addProvinceTable("NO", "Olsztyn", "Warmińsko-Mazurskie");
        addProvinceTable("NOG", "Olecko", "Warmińsko-Mazurskie");
        addProvinceTable("NOL", "Olsztyn powiat", "Warmińsko-Mazurskie");
        addProvinceTable("NOS", "Ostróda", "Warmińsko-Mazurskie");
        addProvinceTable("NPI", "Pisz", "Warmińsko-Mazurskie");
        addProvinceTable("NSZ", "Szczytno", "Warmińsko-Mazurskie");
        addProvinceTable("NWE", "Węgorzewo", "Warmińsko-Mazurskie");



        // O - Opolskie
        addProvinceTable("OB", "Brzeg", "Opolskie");
        addProvinceTable("OGL", "Głubczyce", "Opolskie");
        addProvinceTable("OK", "Kędzierzyn Koźle", "Opolskie");
        addProvinceTable("OKL", "Kluczbork", "Opolskie");
        addProvinceTable("OKR", "Krapkowice", "Opolskie");
        addProvinceTable("ONA", "Namysłów", "Opolskie");
        addProvinceTable("ONY", "Nysa", "Opolskie");
        addProvinceTable("OOL", "Olesno", "Opolskie");
        addProvinceTable("OP", "Opole", "Opolskie");
        addProvinceTable("OPO", "Opole powiat", "Opolskie");
        addProvinceTable("OPR", "Prudnik", "Opolskie");
        addProvinceTable("OST", "Strzelce Opolskie", "Opolskie");



        // P - Wielkopolskie
        addProvinceTable("PCH", "Chodzież", "Wielkopolskie");
        addProvinceTable("PCT", "Czarnków", "Wielkopolskie");
        addProvinceTable("PGN", "Gniezno", "Wielkopolskie");
        addProvinceTable("PGO", "Grodzisk Wlkp.", "Wielkopolskie");
        addProvinceTable("PGS", "Gostyń", "Wielkopolskie");
        addProvinceTable("PJA", "Jarocin", "Wielkopolskie");
        addProvinceTable("PK", "Kalisz", "Wielkopolskie");
        addProvinceTable("PKA", "Kalisz powiat", "Wielkopolskie");
        addProvinceTable("PKE", "Kępno", "Wielkopolskie");
        addProvinceTable("PKL", "Koło", "Wielkopolskie");
        addProvinceTable("PKN", "Konin powiat", "Wielkopolskie");
        addProvinceTable("PKO", "Konin", "Wielkopolskie");
        addProvinceTable("PKR", "Krotoszyn", "Wielkopolskie");
        addProvinceTable("PKS", "Kościan", "Wielkopolskie");
        addProvinceTable("PL", "Leszno", "Wielkopolskie");
        addProvinceTable("PLE", "Leszno powiat", "Wielkopolskie");
        addProvinceTable("PMI", "Międzychód", "Wielkopolskie");
        addProvinceTable("PN", "Konin", "Wielkopolskie");
        addProvinceTable("PNT", "Nowy Tomyśl", "Wielkopolskie");
        addProvinceTable("PO", "Poznań", "Wielkopolskie");
        addProvinceTable("POB", "Oborniki", "Wielkopolskie");
        addProvinceTable("POS", "Ostrów Wielkopolski", "Wielkopolskie");
        addProvinceTable("POT", "Ostrzeszów", "Wielkopolskie");
        addProvinceTable("POZ", "Poznań powiat", "Wielkopolskie");
        addProvinceTable("PP", "Piła", "Wielkopolskie");
        addProvinceTable("PPL", "Pleszew", "Wielkopolskie");
        addProvinceTable("PRA", "Rawicz", "Wielkopolskie");
        addProvinceTable("PSE", "Śrem", "Wielkopolskie");
        addProvinceTable("PSL", "Słupca", "Wielkopolskie");
        addProvinceTable("PSR", "Środa Wielkopolska.", "Wielkopolskie");
        addProvinceTable("PSZ", "Szamotuły", "Wielkopolskie");
        addProvinceTable("PTU", "Turek", "Wielkopolskie");
        addProvinceTable("PWA", "Wągrowiec", "Wielkopolskie");
        addProvinceTable("PWL", "Wolsztyn", "Wielkopolskie");
        addProvinceTable("PWR", "Września", "Wielkopolskie");
        addProvinceTable("PZ", "Poznań powiat", "Wielkopolskie");
        addProvinceTable("PZL", "Złotów", "Wielkopolskie");



        // R - Podkarpackie
        addProvinceTable("RBI", "Ustrzyki Dolne", "Podkarpackie");
        addProvinceTable("RBR", "Brzozów", "Podkarpackie");
        addProvinceTable("RDE", "Dębica", "Podkarpackie");
        addProvinceTable("RJA", "Jarosław", "Podkarpackie");
        addProvinceTable("RJS", "Jasło", "Podkarpackie");
        addProvinceTable("RK", "Krosno", "Podkarpackie");
        addProvinceTable("RKL", "Kolbuszowa", "Podkarpackie");
        addProvinceTable("RKR", "Krosno powiat", "Podkarpackie");
        addProvinceTable("RLA", "Łańcut", "Podkarpackie");
        addProvinceTable("RLE", "Leżajsk", "Podkarpackie");
        addProvinceTable("RLS", "Lesko", "Podkarpackie");
        addProvinceTable("RLU", "Lubaczów", "Podkarpackie");
        addProvinceTable("RMI", "Mielec", "Podkarpackie");
        addProvinceTable("RNI", "Nisko", "Podkarpackie");
        addProvinceTable("RP", "Przemyśl", "Podkarpackie");
        addProvinceTable("RPR", "Przemyśl powiat", "Podkarpackie");
        addProvinceTable("RPZ", "Przerowsk", "Podkarpackie");
        addProvinceTable("RRS", "Ropczyce", "Podkarpackie");
        addProvinceTable("RSA", "Sanok", "Podkarpackie");
        addProvinceTable("RSR", "Strzyżów", "Podkarpackie");
        addProvinceTable("RST", "Stalowa Wola", "Podkarpackie");
        addProvinceTable("RT", "Tarnobrzeg", "Podkarpackie");
        addProvinceTable("RTA", "Tarnobrzeg powiat", "Podkarpackie");
        addProvinceTable("RZ", "Rzeszów", "Podkarpackie");
        addProvinceTable("RZE", "Rzeszów powiat", "Podkarpackie");



        // S - Śląskie
        addProvinceTable("SB", "Bielsko-Biała", "Śląskie");
        addProvinceTable("SBE", "Będzin", "Śląskie");
        addProvinceTable("SBI", "Bielsko-Biała powiat", "Śląskie");
        addProvinceTable("SBL", "Tychy powiat", "Śląskie");
        addProvinceTable("SC", "Częstochowa", "Śląskie");
        addProvinceTable("SCI", "Cieszyn", "Śląskie");
        addProvinceTable("SCZ", "Częstochowa powiat", "Śląskie");
        addProvinceTable("SD", "Dąbrowa Górnicza", "Śląskie");
        addProvinceTable("SG", "Gliwice", "Śląskie");
        addProvinceTable("SGL", "Gliwice powiat", "Śląskie");
        addProvinceTable("SH", "Chorzów", "Śląskie");
        addProvinceTable("SI", "Siemanowice Śląskie", "Śląskie");
        addProvinceTable("SJ", "Jaworzno", "Śląskie");
        addProvinceTable("SJZ", "Jastrzębie Zdrój", "Śląskie");
        addProvinceTable("SK", "Katowice", "Śląskie");
        addProvinceTable("SKL", "Kłobuck", "Śląskie");
        addProvinceTable("SL", "Ruda Śląska", "Śląskie");
        addProvinceTable("SLU", "Lubliniec", "Śląskie");
        addProvinceTable("SM", "Mysłowice", "Śląskie");
        addProvinceTable("SMI", "Mikołów", "Śląskie");
        addProvinceTable("SMY", "Myszków", "Śląskie");
        addProvinceTable("SO", "Sosnowiec", "Śląskie");
        addProvinceTable("SPI", "Piekary Śląskie", "Śląskie");
        addProvinceTable("SPS", "Pszczyna", "Śląskie");
        addProvinceTable("SR", "Rybnik", "Śląskie");
        addProvinceTable("SRB", "Rybnik powiat", "Śląskie");
        addProvinceTable("SRC", "Racibórz", "Śląskie");
        addProvinceTable("SRS", "Ruda Śląsk", "Śląskie");
        addProvinceTable("ST", "Tychy", "Śląskie");
        addProvinceTable("STA", "Tarnowskie Góry", "Śląskie");
        addProvinceTable("STY", "Tyczy powiat", "Śląskie");
        addProvinceTable("SW", "Świętochłowice", "Śląskie");
        addProvinceTable("SWD", "Wodzisław", "Śląskie");
        addProvinceTable("SY", "Bytom", "Śląskie");
        addProvinceTable("SZ", "Zabrze", "Śląskie");
        addProvinceTable("SZA", "Zawiercie", "Śląskie");
        addProvinceTable("SZO", "Żory", "Śląskie");
        addProvinceTable("SZY", "Żywiec", "Śląskie");



        //T - Świętokrzystkie
        addProvinceTable("TBU", "Busko", "Świętokrzystkie");
        addProvinceTable("TJE", "Jędrzejów", "Świętokrzystkie");
        addProvinceTable("TK", "Kielce", "Świętokrzystkie");
        addProvinceTable("TKA", "Kazimierza Wielka", "Świętokrzystkie");
        addProvinceTable("TKI", "Kielce powiat", "Świętokrzystkie");
        addProvinceTable("TKN", "Końskie", "Świętokrzystkie");
        addProvinceTable("TOP", "Opatów", "Świętokrzystkie");
        addProvinceTable("TOS", "Ostrowiec Świętokrzystkie", "Świętokrzystkie");
        addProvinceTable("TPI", "Pińczów", "Świętokrzystkie");
        addProvinceTable("TSA", "Sandomierz", "Świętokrzystkie");
        addProvinceTable("TSK", "Skarżysko-Kamienna", "Świętokrzystkie");
        addProvinceTable("TST", "Starachowice", "Świętokrzystkie");
        addProvinceTable("TSZ", "Staszów", "Świętokrzystkie");
        addProvinceTable("TWL", "Włoszczowa", "Świętokrzystkie");



        //W - Mazowieckie
        addProvinceTable("WA", "Warszawa-Białołęka", "Mazowieckie");
        addProvinceTable("WB", "Warszawa-Bemowo", "Mazowieckie");
        addProvinceTable("WBR", "Białobrzegi", "Mazowieckie");
        addProvinceTable("WCI", "Ciechanów", "Mazowieckie");
        addProvinceTable("WD", "Warszawa-Bielany", "Mazowieckie");
        addProvinceTable("WE", "Warszawa-Mokotów", "Mazowieckie");
        addProvinceTable("WF", "Warszawa-Praga Pd.", "Mazowieckie");
        addProvinceTable("WG", "Garwolin", "Mazowieckie");
        addProvinceTable("WGM", "Grodzisk", "Mazowieckie");
        addProvinceTable("WGR", "Grójec", "Mazowieckie");
        addProvinceTable("WGS", "Gostynin", "Mazowieckie");
        addProvinceTable("WH", "Warszawa-Praga Pn.", "Mazowieckie");
        addProvinceTable("WI", "Warszawa Śródmieście", "Mazowieckie");
        addProvinceTable("WJ", "Warszawa-Targówek", "Mazowieckie");
        addProvinceTable("WK", "Warszawa-Ursus", "Mazowieckie");
        addProvinceTable("WKZ", "Kozienice", "Mazowieckie");
        addProvinceTable("WL", "Legionowo", "Mazowieckie");
        addProvinceTable("WLI", "Lipsko", "Mazowieckie");
        addProvinceTable("WLS", "Łosice", "Mazowieckie");
        addProvinceTable("WM", "Mińsk Mazowiecki", "Mazowieckie");
        addProvinceTable("WMA", "Maków Mazowiecki", "Mazowieckie");
        addProvinceTable("WML", "Mława", "Mazowieckie");
        addProvinceTable("WN", "Warszawa-Ursynów", "Mazowieckie");
        addProvinceTable("WND", "Nowy Dwór Mazowiecki", "Mazowieckie");
        addProvinceTable("WO", "Ostrołęka", "Mazowieckie");
        addProvinceTable("WOS", "Ostrów Mazowiecka", "Mazowieckie");
        addProvinceTable("WOT", "Otwock", "Mazowieckie");
        addProvinceTable("WP", "Płock", "Mazowieckie");
        addProvinceTable("WPI", "Piaseczno", "Mazowieckie");
        addProvinceTable("WPL", "Płock", "Mazowieckie");
        addProvinceTable("WPN", "Płońsk", "Mazowieckie");
        addProvinceTable("WPR", "Pruszków", "Mazowieckie");
        addProvinceTable("WPU", "Pułtusk", "Mazowieckie");
        addProvinceTable("WPY", "Przysucha", "Mazowieckie");
        addProvinceTable("WPZ", "Przasnysz", "Mazowieckie");
        addProvinceTable("WR", "Radom", "Mazowieckie");
        addProvinceTable("WRA", "Radom powiat", "Mazowieckie");
        addProvinceTable("WS", "Siedlce", "Mazowieckie");
        addProvinceTable("WSC", "Sochaczew", "Mazowieckie");
        addProvinceTable("WSE", "Sierpc", "Mazowieckie");
        addProvinceTable("WSI", "Siedlce powiat", "Mazowieckie");
        addProvinceTable("WSK", "Sokołów Podlaski", "Mazowieckie");
        addProvinceTable("WSZ", "Szydłowiec", "Mazowieckie");
        addProvinceTable("WT", "Warszawa-Wawer", "Mazowieckie");
        addProvinceTable("WU","Warszawa-Ochota", "Mazowieckie");
        addProvinceTable("WW.A", "Warszawa-Rembrertów", "Mazowieckie");
        addProvinceTable("WW.C", "Warszawa-Rembrertów", "Mazowieckie");
        addProvinceTable("WW.E", "Warszawa-Wilanów", "Mazowieckie");
        addProvinceTable("WW.F", "Warszawa-Wilanów", "Mazowieckie");
        addProvinceTable("WW.G", "Warszawa-Wilanów", "Mazowieckie");
        addProvinceTable("WW.H", "Warszawa-Wilanów", "Mazowieckie");
        addProvinceTable("WW.J", "Warszawa-Wilanów", "Mazowieckie");
        addProvinceTable("WW.K", "Warszawa-Włochy", "Mazowieckie");
        addProvinceTable("WW.L", "Warszawa-Włochy", "Mazowieckie");
        addProvinceTable("WW.M", "Warszawa-Włochy", "Mazowieckie");
        addProvinceTable("WW.N", "Warszawa-Włochy", "Mazowieckie");
        addProvinceTable("WW.V", "Warszawa-Włochy", "Mazowieckie");
        addProvinceTable("WW.X", "Warszawa-Rembrertów", "Mazowieckie");
        addProvinceTable("WW.Y", "UM St. Warszawy", "Mazowieckie");
        addProvinceTable("WW.W", "Warszawa-Wilanów", "Mazowieckie");
        addProvinceTable("WW.YS", "Warszawa-Wesoła", "Mazowieckie");
        addProvinceTable("WW.YV", "Warszawa-Wesoła", "Mazowieckie");
        addProvinceTable("WW.YX", "Warszawa-Wesoła", "Mazowieckie");
        addProvinceTable("WW.YZ", "Warszawa-Wesoła", "Mazowieckie");
        addProvinceTable("WWE", "Węgrów", "Mazowieckie");
        addProvinceTable("WWL", "Wołomin", "Mazowieckie");
        addProvinceTable("WWY", "Wyszków", "Mazowieckie");
        addProvinceTable("WX", "Warszawa-Żoliborz", "Mazowieckie");
        addProvinceTable("WY", "Warszawa-Wola", "Mazowieckie");
        addProvinceTable("WZ", "Warszawa-Zachód", "Mazowieckie");
        addProvinceTable("WZU", "Żuromin", "Mazowieckie");
        addProvinceTable("WZW", "Zwoleń", "Mazowieckie");
        addProvinceTable("WZY", "Żyrandów", "Mazowieckie");



        // Z - Zachodniopomorskie
        addProvinceTable("ZBI", "Białogard", "Zachodniopomorskie");
        addProvinceTable("ZCH", "Choszczno", "Zachodniopomorskie");
        addProvinceTable("ZDR", "Drawsko", "Zachodniopomorskie");
        addProvinceTable("ZGL", "Goleniów", "Zachodniopomorskie");
        addProvinceTable("ZGR", "Gryfino", "Zachodniopomorskie");
        addProvinceTable("ZGY", "Gryfice", "Zachodniopomorskie");
        addProvinceTable("ZK", "Koszalin", "Zachodniopomorskie");
        addProvinceTable("ZKA", "Kamień Pomorski", "Zachodniopomorskie");
        addProvinceTable("ZKL", "Kołobrzeg", "Zachodniopomorskie");
        addProvinceTable("ZKO", "Koszalin powiat", "Zachodniopomorskie");
        addProvinceTable("ZLO", "Łobez", "Zachodniopomorskie");
        addProvinceTable("ZMY", "Myślibórz", "Zachodniopomorskie");
        addProvinceTable("ZPL", "Police", "Zachodniopomorskie");
        addProvinceTable("ZPY", "Pyrzyce", "Zachodniopomorskie");
        addProvinceTable("ZS", "Szczecin", "Zachodniopomorskie");
        addProvinceTable("ZSD", "Świdwin", "Zachodniopomorskie");
        addProvinceTable("ZSL", "Sławno", "Zachodniopomorskie");
        addProvinceTable("ZST", "Starogard Szczeciński", "Zachodniopomorskie");
        addProvinceTable("ZSW", "Świnołujście", "Zachodniopomorskie");
        addProvinceTable("ZSZ", "Szczecinek", "Zachodniopomorskie");
        addProvinceTable("ZWA", "Wałcz", "Zachodniopomorskie");

    }

    public void diplomaticData(){

        addDiplomaticTable("001xxx", "USA");
        addDiplomaticTable("002xxx", "Wielka Brytania");
        addDiplomaticTable("003xxx", "Francja");
        addDiplomaticTable("004xxx", "Kanada");
        addDiplomaticTable("005xxx", "Niemcy");
        addDiplomaticTable("006xxx", "Holandia");
        addDiplomaticTable("007xxx", "Włochy");
        addDiplomaticTable("008xxx", "Austria");
        addDiplomaticTable("009xxx", "Japonia");
        addDiplomaticTable("010xxx", "Turcja");
        addDiplomaticTable("011xxx", "Belgia");
        addDiplomaticTable("012xxx", "Dania");
        addDiplomaticTable("013xxx", "Norwegia");
        addDiplomaticTable("014xxx", "Grecja");
        addDiplomaticTable("015xxx", "Australia");
        addDiplomaticTable("016xxx", "Algieria");
        addDiplomaticTable("017xxx", "Afganistan");
        addDiplomaticTable("018xxx", "Argentyna");
        addDiplomaticTable("019xxx", "Brazylia");
        addDiplomaticTable("020xxx", "Bangladesz");
        addDiplomaticTable("021xxx", "Egipt");
        addDiplomaticTable("022xxx", "Ekwador");
        addDiplomaticTable("023xxx", "Finlandia");
        addDiplomaticTable("024xxx", "Hiszpania");
        addDiplomaticTable("025xxx", "Irak");
        addDiplomaticTable("026xxx", "Iran");
        addDiplomaticTable("027xxx", "Indie");
        addDiplomaticTable("028xxx", "Indonezja");
        addDiplomaticTable("029xxx", "Kolumbia");
        addDiplomaticTable("030xxx", "Malezja");
        addDiplomaticTable("031xxx", "Libia");
        addDiplomaticTable("032xxx", "Maroko");
        addDiplomaticTable("033xxx", "Meksyk");
        addDiplomaticTable("034xxx", "Nigeria");
        addDiplomaticTable("035xxx", "Pakistan");
        addDiplomaticTable("036***", "Portugalia");
        addDiplomaticTable("037***", "Palestyna");
        addDiplomaticTable("038***", "Syria");
        addDiplomaticTable("039***", "Szwecja");
        addDiplomaticTable("040***", "Szwajcaria");
        addDiplomaticTable("041***", "Tunezja");
        addDiplomaticTable("042***", "Tajlandia");
        addDiplomaticTable("043***", "Wenezuela");
        addDiplomaticTable("044***", "Urugwaj");
        addDiplomaticTable("045***", "Peru");
        addDiplomaticTable("046***", "Jemen");
        addDiplomaticTable("047***", "Kostaryka");
        addDiplomaticTable("048***", "Kongo");
        addDiplomaticTable("049***", "Izrael");
        addDiplomaticTable("050***", "Nikaragua");
        addDiplomaticTable("051***", "Chile");
        addDiplomaticTable("052***", "Watykan");
        addDiplomaticTable("053***", "Korea Południowa");
        addDiplomaticTable("054***", "Przedstawicielstwo KWE");
        addDiplomaticTable("055***", "Irlandia");
        addDiplomaticTable("056***", "Bank Światowy");
        addDiplomaticTable("057***", "Międzynarowdowy Funfusz Walutowy");
        addDiplomaticTable("058***", "Filipiny");
        addDiplomaticTable("059***", "Międzynarodowa Korproracja Finansowa");
        addDiplomaticTable("060***", "RPA");
        addDiplomaticTable("061***", "OBWE");
        addDiplomaticTable("062***", "Cypr");
        addDiplomaticTable("063***", "Kuwejt");
        addDiplomaticTable("064***", "ONZ");
        addDiplomaticTable("065***", "Rosja");
        addDiplomaticTable("066***", "Słowacja");
        addDiplomaticTable("067***", "Czechy");
        addDiplomaticTable("068***", "Bułgaria");
        addDiplomaticTable("069***", "Węgry");
        addDiplomaticTable("070***", "Rumunia");
        addDiplomaticTable("071***", "Wietnam");
        addDiplomaticTable("072***", "Serbia");
        addDiplomaticTable("073***", "Korea Północna");
        addDiplomaticTable("074***", "Kuba");
        addDiplomaticTable("075***", "Albania");
        addDiplomaticTable("076***", "Chiny");
        addDiplomaticTable("077***", "Mongolia");
        addDiplomaticTable("078***", "Międzynarodowa Organizacja Pracy");
        addDiplomaticTable("079***", "Organizacja Kooperacyjna ds. Kolei");
        addDiplomaticTable("080***", "Klub Dyplomatyczny");
        addDiplomaticTable("081***", "Laos");
        addDiplomaticTable("082***", "Angola");
        addDiplomaticTable("083***", "Ukraina");
        addDiplomaticTable("084***", "EBOR");
        addDiplomaticTable("085***", "Litwa");
        addDiplomaticTable("086***", "Białoruś");
        addDiplomaticTable("087***", "Łotwa");
        addDiplomaticTable("088***", "Chorwacja");
        addDiplomaticTable("089***", "Liban");
        addDiplomaticTable("090***", "Słowenia");
        addDiplomaticTable("091***", "Gwatemala");
        addDiplomaticTable("092***", "Estonia");
        addDiplomaticTable("093***", "Macedonia");
        addDiplomaticTable("094***", "Mołdawia");
        addDiplomaticTable("095***", "Izrael");
        addDiplomaticTable("096***", "Armenia");
        addDiplomaticTable("097***", "Sir Lanka");
        addDiplomaticTable("098***", "Kazachstan");
        addDiplomaticTable("099***", "Arabia");
        addDiplomaticTable("100***", "Gruzja");
        addDiplomaticTable("101***", "Uzbekistan");
        addDiplomaticTable("102***", "UN-HABITAT");
        addDiplomaticTable("103***", "Nowa Zelandia");
        addDiplomaticTable("104***", "Azerbejdżan");
        addDiplomaticTable("105***", "Suwerenny Wojskowy Zakon Maltański");
        addDiplomaticTable("106***", "Kambodża");
        addDiplomaticTable("107***", "Frontex");
        addDiplomaticTable("108***", "Luksemburg");
        addDiplomaticTable("109***", "Bośnia i Hercegowina");
        addDiplomaticTable("110***", "Panama");
        addDiplomaticTable("111***", "Katar");
        addDiplomaticTable("112***", "Malta");
        addDiplomaticTable("113***", "Zjednoczone Emiraty Arabskie");
        addDiplomaticTable("114***", "Czarnogóra");
        addDiplomaticTable("115***", "Senegal");
    }

    public void uniformedData(){

        //Tablice Wojskowe
        addUniformedTable("UA", "Wojsko", "Samochody osobowe");
        addUniformedTable("UB", "Wojsko" , "Pojazdy Bojowe");
        addUniformedTable("UC", "Wojsko", "Samochody Dostawcze");
        addUniformedTable("UD", "Wojsko", "Autobusy");
        addUniformedTable("UE", "Wojsko", "Samochody Ciężarowe");
        addUniformedTable("UG", "Wojsko", "Pojazdy Specjalne");
        addUniformedTable("UI", "Wojsko", "Przyczepy Transportowe");
        addUniformedTable("UJ", "Wojsko", "Przyczepy Specjalne");
        addUniformedTable("UK", "Wojsko", "Motocykle");


        //Tablice Instytucji Bezpieczeństwa
        addUniformedTable("HA", "Centralne Biuro Antykorupcyjne", "");
        addUniformedTable("HB", "Biuro Ochrony Rządu", "");
        addUniformedTable("HC", "Służba Celna", "");
        addUniformedTable("HK", "Agencja Bezpieczeństwa Wewnętrznego", "");
        addUniformedTable("HM", "Służba Kontrwywiadu Wojskowego", "");
        addUniformedTable("HN", "GROM", "");
        addUniformedTable("HM", "Służba Wywiadu Wojskowego", "");
        addUniformedTable("HP", "Policja", "");
        addUniformedTable("HS", "Kontrola Skarbowa", "");
        addUniformedTable("HW", "Straż Graniczna", "");


        //Tablice Izby Celnej
        addUniformedTable("HCA", "Izba Celna", "Olszyn");
        addUniformedTable("HCB", "Izba Celna", "Białystok");
        addUniformedTable("HCC", "Izba Celna", "Biała Podlaska");
        addUniformedTable("HCD", "Izba Celna", "Przemyśl");
        addUniformedTable("HCE", "Izba Celna", "Kraków");
        addUniformedTable("HCF", "Izba Celna", "Katowice");
        addUniformedTable("HCG", "Izba Celna", "Wrocław");
        addUniformedTable("HCH", "Izba Celna", "Rzepin");
        addUniformedTable("HCJ", "Izba Celna", "Szczecin");
        addUniformedTable("HCK", "Izba Celna", "Gdynia");
        addUniformedTable("HCL", "Izba Celna", "Warszawa");
        addUniformedTable("HCM", "Izba Celna", "Toruń");
        addUniformedTable("HCN", "Izba Celna", "Łódź");
        addUniformedTable("HCO", "Izba Celna", "Poznań");
        addUniformedTable("HCP", "Izba Celna", "Opole");
        addUniformedTable("HCR", "Izba Celna", "Kielce");



        //Tablice Policji
        addUniformedTable("HPA", "Komenda Główna Policji", "");
        addUniformedTable("HPB", "Policja", "Dolnośląskie");
        addUniformedTable("HPC", "Policja", "Kujawsko-Pomorskie");
        addUniformedTable("HPD", "Policja", "Lubelskie");
        addUniformedTable("HPE", "Policja", "Lubuskie");
        addUniformedTable("HPF", "Policja", "Lodzkie");
        addUniformedTable("HPG", "Policja", "Małopolska");
        addUniformedTable("HPH", "Policja", "Mazowieckie");
        addUniformedTable("HPJ", "Policja", "Opolskie");
        addUniformedTable("HPK", "Policja", "Podkarpackie");
        addUniformedTable("HPL", "Policja", "Szkoła Policji");
        addUniformedTable("HPM", "Policja", "Podlaskie");
        addUniformedTable("HPN", "Policja", "Pomorskie");
        addUniformedTable("HPP", "Policja", "Śląskie");
        addUniformedTable("HPS", "Policja", "Świętokrzystkie");
        addUniformedTable("HPT", "Policja", "Warmińsko-Mazurskie");
        addUniformedTable("HPU", "Policja", "Wielkopolskie");
        addUniformedTable("HPW", "Policja", "Zachodniopomorskie");
        addUniformedTable("HPZ", "Komenda Stołeczna Policji", "");
    }

}
