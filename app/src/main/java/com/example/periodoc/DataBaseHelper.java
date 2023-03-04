package com.example.periodoc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String USER_TABLE = "USER_TABLE";
    public static final String COLUMN_USER_DATE = "USER_DATE";
    private static final String COLUMN_ID = "ID";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "user.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + USER_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USER_DATE + " TEXT)";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(CustomerModel customerModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USER_DATE, customerModel.getMyDate());

        long insert = db.insert(USER_TABLE,null, cv);
        if(insert == -1){
            return false;
        }
        else
            return true;
    }
    //here starts

    public boolean deleteOne(CustomerModel customerModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + USER_TABLE + " WHERE " + COLUMN_ID + " = " + customerModel.getId();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            return true;
        }
        else {
            return false;
        }
    }

    public List<CustomerModel> getEveryDate(){
        List<CustomerModel> returnList = new ArrayList<>();

        String queryString = "SELECT*FROM " + USER_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            do{
                int user_ID = cursor.getInt(0);
                String myDate = cursor.getString(1);
                CustomerModel newUser = new CustomerModel(user_ID, myDate);
                returnList.add(newUser);

            }while (cursor.moveToNext());
        }
        else {
            //failure
        }

        cursor.close();
        db.close();

        return returnList;
    }

    public String getLastDate(){
        //List<CustomerModel> returnList = new ArrayList<>();
        String returnDate=" ";
        //returnDate = "00/00/0000";

        String selectQuery = "SELECT "+ COLUMN_USER_DATE +" FROM " + USER_TABLE + " ORDER BY "+ COLUMN_ID+" DESC LIMIT 1";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst())
            returnDate = cursor.getString(cursor.getColumnIndex(COLUMN_USER_DATE));

        cursor.close();
        db.close();

        return returnDate;
    }
}
