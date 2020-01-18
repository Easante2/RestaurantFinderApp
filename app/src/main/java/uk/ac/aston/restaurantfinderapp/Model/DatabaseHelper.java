package uk.ac.aston.restaurantfinderapp.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Emmanuel on 09/04/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DBNAME = "restaurantFinder";
        private static final String favTable = "Favourites";
        private static final String favID = "FavID";
        public static final String placeID = "PlaceID";
        public static final String eateryName = "EateryName";
       // public static final String eateryVicinity = "EateryVicinity";
        public static final String eateryAddress = "EateryAddress";
        public static final String longitude = "longitude";
        public static final String latitude = "latitude";
        public static final String visits = "visits";

    public DatabaseHelper(Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + favTable + " (" + favID
                + " INTEGER PRIMARY KEY AUTOINCREMENT , " + placeID + " TEXT , " + eateryName
                + " TEXT , " + eateryAddress + " TEXT , " + longitude + " REAL , " + latitude + " REAL , " + visits + " INTEGER)";
        Log.i("DBHELPER", sql);
        db.execSQL(sql);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + favTable);
        onCreate(db);
    }

    public long addToFavourites(String pID, String name, String ad, double lat, double lon, int visits) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(placeID, pID);
        cv.put(eateryName, name);
        cv.put(eateryAddress, ad);
        cv.put(latitude, lat);
        cv.put(longitude, lon);
        cv.put(this.visits, visits);

        long i = db.insert(favTable, eateryName, cv);
        Log.i("Add to Fav", pID + ", "+name+", "+ ad + " , "+ lat + " , " + lon + " , " + visits + " added to favourites ");
        db.close();
        return i;
    }

    public Cursor getAllFavourites() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT " + favID + " as _id, " + placeID + " , " + eateryName
                + " , " + eateryAddress + " , " + latitude + " , " + longitude + " , " + visits + " from " + favTable, new String[] {});
        return cur;
    }

    public void updateVisits(String pID){
        SQLiteDatabase db = this.getReadableDatabase();

        db.execSQL("UPDATE " + favTable + " SET " + visits + " = " + visits + " + 1 " + " WHERE " + placeID + " =? ",
                new String[]{String.valueOf(pID)});
        Log.i("UPDATE  ", "visits =" + visits);



    }



  /*  public int getVisit(String value){
        SQLiteDatabase db = this.getReadableDatabase();



        Cursor cursor = db.query(favTable, new String[] { visits }, placeID + "=?",
                new String[] { String.valueOf(value)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();


        int result = cursor.getInt(0);
        // return contact
        return result;

    }*/

    public String getPlaceID(String pID){
        SQLiteDatabase db = this.getReadableDatabase();
        String id ="";
        String selectQuery = "SELECT " +placeID+ " FROM " + favTable + " WHERE "+ placeID + " =?";
        Cursor c = db.rawQuery(selectQuery, new String[] { pID });
        if (c.moveToFirst()) {
             id = c.getString(c.getColumnIndex(placeID));
        }
        c.close();
        return id;
    }


    public Cursor getLatLong(String pID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT " + favID + " as _id, " + latitude
                + " , " + longitude + " from " + favTable + " WHERE " + placeID + " = '" + pID + "'" , null);
        return cur;
    }

    public void deleteFromFavourites(String pID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(favTable, placeID + "=?",
                new String[] {String.valueOf(pID)});
        db.close();
    }

    public void dropTable(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + favTable);
    }



}
