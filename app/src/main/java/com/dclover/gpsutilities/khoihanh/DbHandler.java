package com.dclover.gpsutilities.khoihanh;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.design.BuildConfig;
import android.util.Log;

import com.dclover.gpsutilities.khoihanh.items.DataFiles;

import java.util.ArrayList;

/**
 * Created by Kinghero on 7/5/2016.
 */
public class DbHandler extends SQLiteOpenHelper {
    public static final String COLUMN_AVGSPEED = "avgSpeed";
    public static final String COLUMN_DATETIME = "dateTime";
    public static final String COLUMN_DISTANCE = "distanceTravelled";
    public static final String COLUMN_ENDPLACE = "endPlace";
    public static final String COLUMN_FILENAME = "fileName";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_MAXSPEED = "maxSpeed";
    public static final String COLUMN_STARTPLACE = "startPlace";
    public static final String COLUMN_TIMEDURATION = "timeDuration";
    public static final String DATABASE_NAME = "TripData.db";
    public static final int DATABASE_VERSION = 2;
    public static final String TABLE_NAME = "Tb_TripDetails";
    private static final String TAG = "GPS Trip Tracker";

    public class DbData {
        ArrayList<String> endPlaceList;
        ArrayList<String> fileNameList;
        ArrayList<String> startPlaceList;

        DbData(ArrayList<String> fileNameList, ArrayList<String> startPlaceList, ArrayList<String> endPlaceList) {
            this.fileNameList = new ArrayList();
            this.startPlaceList = new ArrayList();
            this.endPlaceList = new ArrayList();
            this.fileNameList = fileNameList;
            this.startPlaceList = startPlaceList;
            this.endPlaceList = endPlaceList;
        }

        public ArrayList<String> GetfileNameList() {
            return this.fileNameList;
        }

        public ArrayList<String> GetstartPlaceList() {
            return this.startPlaceList;
        }

        public ArrayList<String> GetendPlaceList() {
            return this.endPlaceList;
        }
    }

    public DbHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Tb_TripDetails(id INTEGER PRIMARY KEY AUTOINCREMENT, fileName TEXT, startPlace TEXT, endPlace TEXT, maxSpeed FLOAT, avgSpeed FLOAT, timeDuration FLOAT, distanceTravelled FLOAT, dateTime TEXT );");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
        db.execSQL("INSERT INTO Tb_TripDetails (id , fileName , startPlace , endPlace , maxSpeed , avgSpeed , timeDuration , distanceTravelled)  SELECT * FROM TripDetails");
        db.execSQL("DROP TABLE IF EXISTS TripDetails");
    }

    public void addNewFile(DataFiles DataFile) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO Tb_TripDetails(fileName , startPlace , endPlace , maxSpeed , avgSpeed , timeDuration , distanceTravelled, dateTime) VALUES('" + DataFile.getfileName() + "'  ,  '" + DataFile.getstartPlace() + "' ,  '" + DataFile.getendPlace() + "',  '" + DataFile.getmaxSpeed() + "',  '" + DataFile.getavgSpeed() + "',  '" + DataFile.gettimeDuration() + "',  '" + DataFile.getdistanceTravelled() + "',  '" + DataFile.getdateTime() + "' );");
        db.close();
    }

    public void deleteFile(String filename) {
        getWritableDatabase().execSQL("DELETE FROM Tb_TripDetails WHERE fileName = '" + filename + "'");
    }

    public DbData getAllFileNames() {
        ArrayList<String> fileNameList = new ArrayList();
        ArrayList<String> startPlaceList = new ArrayList();
        ArrayList<String> endPlaceList = new ArrayList();
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Tb_TripDetails", null);
        if (c.getCount() != 0) {
            c.moveToFirst();
            do {
                if (!c.getString(c.getColumnIndex(COLUMN_FILENAME)).isEmpty()) {
                    fileNameList.add(c.getString(c.getColumnIndex(COLUMN_FILENAME)));
                }
                if (!c.getString(c.getColumnIndex(COLUMN_STARTPLACE)).isEmpty()) {
                    startPlaceList.add(c.getString(c.getColumnIndex(COLUMN_STARTPLACE)));
                }
                if (!c.getString(c.getColumnIndex(COLUMN_ENDPLACE)).isEmpty()) {
                    endPlaceList.add(c.getString(c.getColumnIndex(COLUMN_ENDPLACE)));
                }
            } while (c.moveToNext());
        } else {
            Log.d(TAG, "DB is NULL");
        }
        DbData dbValues = new DbData(fileNameList, startPlaceList, endPlaceList);
        db.close();
        return dbValues;
    }

    public String GetValueAtPosition(int id, String category) {
        Cursor c = getWritableDatabase().rawQuery("SELECT * FROM Tb_TripDetails", null);
        if (c.getCount() != 0) {
            c.moveToPosition(id);
            if (category == COLUMN_STARTPLACE && !c.getString(c.getColumnIndex(COLUMN_STARTPLACE)).isEmpty()) {
                return c.getString(c.getColumnIndex(COLUMN_STARTPLACE));
            }
            if (category == COLUMN_ENDPLACE && !c.getString(c.getColumnIndex(COLUMN_ENDPLACE)).isEmpty()) {
                return c.getString(c.getColumnIndex(COLUMN_ENDPLACE));
            }
            if (category == COLUMN_MAXSPEED) {
                return BuildConfig.FLAVOR + c.getFloat(c.getColumnIndex(COLUMN_MAXSPEED));
            }
            if (category == COLUMN_AVGSPEED) {
                return BuildConfig.FLAVOR + c.getDouble(c.getColumnIndex(COLUMN_AVGSPEED));
            }
            if (category == COLUMN_TIMEDURATION) {
                return BuildConfig.FLAVOR + c.getFloat(c.getColumnIndex(COLUMN_TIMEDURATION));
            }
            if (category == COLUMN_DISTANCE) {
                return BuildConfig.FLAVOR + c.getFloat(c.getColumnIndex(COLUMN_DISTANCE));
            }
            if (category == COLUMN_DATETIME) {
                return BuildConfig.FLAVOR + c.getString(c.getColumnIndex(COLUMN_DATETIME));
            }
        }
        return BuildConfig.FLAVOR;
    }

    public float getHighestSpeed() {
        Cursor c = getWritableDatabase().rawQuery("SELECT * FROM Tb_TripDetails", null);
        float highestSpeed = 0.0f;
        if (c.getCount() != 0) {
            c.moveToFirst();
            do {
                if (highestSpeed < c.getFloat(c.getColumnIndex(COLUMN_MAXSPEED))) {
                    highestSpeed = c.getFloat(c.getColumnIndex(COLUMN_MAXSPEED));
                }
            } while (c.moveToNext());
        }
        return highestSpeed;
    }
}
