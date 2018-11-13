package com.farizdotid.belajarautocomplete;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by farizdotid on 23-Jan-17.
 */

public class DBHelperNamaProvinsi extends SQLiteOpenHelper {

    private static final String TAG = DBHelperNamaProvinsi.class.getSimpleName();

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "db_provinsi";

    private static final String TABLE_NAME = "tabel_provinsi";

    private static final String KEY_IDPROV = "id";
    private static final String KEY_NAMAPROV = "namaprovinsi";

    private Context mContext;

    public DBHelperNamaProvinsi(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
                KEY_IDPROV + " INTEGER PRIMARY KEY," +
                KEY_NAMAPROV + " TEXT" + ");";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(db);
    }

    public void loadContent() {
        onUpgrade(this.getReadableDatabase(), DATABASE_VERSION, DATABASE_VERSION);

        addData(1, "MR100");
        addData(2, "EL100");
        addData(3, "EL101");
        addData(4, "EL102");
        addData(5, "ML500");
        addData(6, "FR1000");
        addData(7, "FR55");
        addData(8, "RM1000");
    }

    void addData(int idprov, String namaprov) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_IDPROV, idprov);
        values.put(KEY_NAMAPROV, namaprov);

        db.insert(TABLE_NAME, null, values);
        Log.i(TAG, "addData " + " ID : " + values.get(KEY_IDPROV) + " NAMAPROV : " + values.get(KEY_NAMAPROV));
        db.close();
    }

    public String[] SelectAllDataNamaProv() {
        try {
            String arrData[] = null;
            SQLiteDatabase db;
            db = this.getReadableDatabase();

            String strSQL = "SELECT namaprovinsi FROM " + TABLE_NAME;
            Cursor cursor = db.rawQuery(strSQL, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    arrData = new String[cursor.getCount()];
                    int i = 0;
                    do {
                        arrData[i] = cursor.getString(0);
                        i++;
                    } while (cursor.moveToNext());
                }
            }
            cursor.close();
            return arrData;
        } catch (Exception e) {
            return null;
        }
    }

    public String[] getByName(String name) {
        try {
            String arrData[] = null;
            SQLiteDatabase db;
            db = this.getReadableDatabase();

//            String strSQL = "SELECT namaprovinsi FROM " + TABLE_NAME + " WHERE namaprovinsi like %%";
//            Cursor cursor = db.rawQuery(strSQL, null);

            String[] args = new String[1];
            args[0] = "%" + name + "%";

            String sql = "SELECT namaprovinsi FROM " + TABLE_NAME + " WHERE namaprovinsi LIKE ?";

            Cursor cursor = db.rawQuery(sql, args);

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    arrData = new String[cursor.getCount()];
                    int i = 0;
                    do {
                        arrData[i] = cursor.getString(0);
                        i++;
                    } while (cursor.moveToNext());
                }
            }
            cursor.close();
            return arrData;
        } catch (Exception e) {
            Log.d("MyAPP", e.getMessage());
            return null;
        }
    }

}