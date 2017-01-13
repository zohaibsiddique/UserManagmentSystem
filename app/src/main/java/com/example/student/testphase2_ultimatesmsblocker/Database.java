package com.example.student.testphase2_ultimatesmsblocker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "test_phase.db";

    private static final String TABLE_ADMIN = "admin";
    public static final String ID_ADMIN = "id";
    public static final String NAME_ADMIN = "name";
    public static final String PASSWORD_ADMIN = "password";
    public static final String EMAILID_ADMIN = "emailid";
    public static final String CONTACT_ADMIN = "contact";

    public static final String CREATE_TABLE_ADMIN = "CREATE TABLE " + TABLE_ADMIN
            + "(" + ID_ADMIN + " INTEGER PRIMARY KEY AUTOINCREMENT DEFAULT 0,"
            + NAME_ADMIN + " TEXT,"
            + PASSWORD_ADMIN + " TEXT,"
            + EMAILID_ADMIN + " TEXT,"
            + CONTACT_ADMIN + " TEXT)";

    private static final String TABLE_USER_DETAILS = "UserDetails";
    public static final String ID_USER = "user_id";
    public static final String NAME_USER = "name";
    public static final String PASSWORD_USER = "password";
    public static final String EMAIL_USER = "emailid";
    public static final String CONTACT_USER = "contact";
    public static final String TYPE_USER = "usertype";
    public static final String REGISTRATION_DATE_USER = "registerationdate";
    public static final String MODIFY_DATE_USER = "modifydate";
    public static final String KEY_SEARCH_USER = "key_search";

    public static final String CREATE_TABLE_USER_DETAILS = "CREATE VIRTUAL TABLE " + TABLE_USER_DETAILS + " USING fts3("
            + ID_USER + " INTEGER PRIMARY KEY,"
            + NAME_USER + " TEXT,"
            + PASSWORD_USER + " TEXT,"
            + EMAIL_USER + " TEXT,"
            + CONTACT_USER + " TEXT,"
            + TYPE_USER + " INTEGER,"
            + REGISTRATION_DATE_USER + " INTEGER,"
            + MODIFY_DATE_USER + " TEXT,"
            + KEY_SEARCH_USER + " TEXT)";

    private static final String TABLE_USERTYPE = "UserType";
    public static final String ID_USERTYPE = "usertypeid ";
    public static final String TITLE_USERTYPE = "typetitle";

    public static final String CREATE_TABLE_USERTYPE = "CREATE TABLE " + TABLE_USERTYPE
            + "(" + ID_USERTYPE + " INTEGER PRIMARY KEY,"
            + TITLE_USERTYPE + " TEXT)";

    // Constructor for creating database
    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE_ADMIN);
            db.execSQL(CREATE_TABLE_USER_DETAILS);
            db.execSQL(CREATE_TABLE_USERTYPE);
            db.execSQL("INSERT INTO " + TABLE_ADMIN + " VALUES(1,'karam', 'karam', 'karam@gmail.com', 'vu')");
            db.execSQL("INSERT INTO " + TABLE_USERTYPE + " VALUES(1,'Normal User')");
            db.execSQL("INSERT INTO " + TABLE_USERTYPE + " VALUES(2,'Advanced User')");
            Log.d("Creating Database:", " Successful");
        } catch (Exception e) {
            Log.d("Creating Database:", " failed");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADMIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERTYPE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADMIN);
        onCreate(db);
    }

    // add new user
    public boolean addNewUser(String id, String name, String password, String email, String contact, String type, String regDate, String modfDate){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues value_TABLE_USER = new ContentValues();
            value_TABLE_USER.put(ID_USER, id);
            value_TABLE_USER.put(NAME_USER, name);
            value_TABLE_USER.put(PASSWORD_USER, password);
            value_TABLE_USER.put(EMAIL_USER, email);
            value_TABLE_USER.put(CONTACT_USER, contact);
            value_TABLE_USER.put(TYPE_USER, type);
            value_TABLE_USER.put(REGISTRATION_DATE_USER, regDate);
            value_TABLE_USER.put(MODIFY_DATE_USER, modfDate);

            String searchValue = id + " " + name + " " + email;
            value_TABLE_USER.put(KEY_SEARCH_USER, searchValue);

            db.insert(TABLE_USER_DETAILS, null, value_TABLE_USER);
        } catch (Exception e) {
            Log.d("addNewUser", " error is " + e.getMessage());
        }
        return true;
    }

    public boolean authenticateLogin(String name, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        String authenticateString = "SELECT * FROM " + TABLE_ADMIN + " WHERE " +
                NAME_ADMIN + "='" + name +"' AND " + PASSWORD_ADMIN + "='" +password+"'";
        Cursor cursor = db.rawQuery(authenticateString, null);

        boolean authenticate = false;
        if(cursor.moveToFirst()){
            authenticate = true;
        }
        cursor.close();
        db.close();
        return authenticate;
    }

    boolean deleteUser(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_USER_DETAILS + " WHERE " + ID_USER + "='" + id + "'");
        return true;
    }
    public Cursor selectAllUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_USER_DETAILS + " ORDER BY " + ID_USER + " DESC";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public Cursor selectBySearch(CharSequence inputText) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String query = "SELECT * FROM " + TABLE_USER_DETAILS + " WHERE " +  KEY_SEARCH_USER + " MATCH '" + inputText + "';";
            Cursor cursor = db.rawQuery(query, null);
            return cursor;
        } catch (Exception e) {
            Log.d("errrr", e.getMessage());
        }
        return null;
    }

    // Update user
    public boolean updateUser(String id, String newId, String username, String password, String email, String contact, String type, String regDate, String modfDate){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_USER, newId);
        contentValues.put(NAME_USER, username);
        contentValues.put(PASSWORD_USER, password);
        contentValues.put(EMAIL_USER, email);
        contentValues.put(CONTACT_USER, contact);
        contentValues.put(TYPE_USER, type);
        contentValues.put(REGISTRATION_DATE_USER, regDate);
        contentValues.put(MODIFY_DATE_USER, modfDate);

        String searchValue = newId + " " + username + " " + email;
        contentValues.put(KEY_SEARCH_USER, searchValue);

        db.update(TABLE_USER_DETAILS, contentValues, ID_USER + "= ?", new String[]{id});
        return true;
    }
}