package com.example.marksheet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper {
    public static final String DATABASE_NAME="StudentDetails";
    public static final int DATABASE_VERSION=1;
    public static final String TABLE_NAME="StudentDetails";
    public static final String ROLL_NUMBER="RollNumber";
    public static final String NAME="Name";
    public static final String SEMESTER="Semester";

    public static final String TABLE_NAME1="CeComponent";
    public static final String CLASSTEST="classtest";
    public static final String SESSIONAL="Sessional";
    public static final String ASSIGNMENT="Assignment";

    private static final String DB_CREATE="create table "+TABLE_NAME+"("+ROLL_NUMBER+" TEXT not null, "+
            NAME+" TEXT not null, "+SEMESTER+" INTEGER not null);";
    private static final String DB_CREATE1="create table "+TABLE_NAME1+"("+ROLL_NUMBER+" TEXT not null, "+
            CLASSTEST+" INTEGER not null, "+SESSIONAL+" INTEGER not null, "+ASSIGNMENT+" INTEGER not null);";

    private final Context context;
    private SQLiteDatabase sqLiteDatabase;
    private MyDBAdapter myDBAdapter;
    private DBHelper dbHelper;
    private Cursor c;
    public DBHelper(Context context) {
        this.context = context;
        myDBAdapter=new MyDBAdapter(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    public DBHelper open()
    {
        sqLiteDatabase=myDBAdapter.getWritableDatabase();
        return this;
    }
    public void close()
    {
        sqLiteDatabase.close();
    }

    public long insertdata(String rollno,String name,String semester)
    {
        ContentValues contentValues=new ContentValues();
        contentValues.put(ROLL_NUMBER,rollno);contentValues.put(NAME,name);contentValues.put(SEMESTER,semester);
        return sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
    }

    public Cursor getstudentdata(String sql) {
        return sqLiteDatabase.rawQuery(sql,null);
    }

    public long insertmarksdata(String rollno,int classtest,int sessional,int assignment)
    {
        ContentValues contentValues=new ContentValues();
        contentValues.put(ROLL_NUMBER,rollno);contentValues.put(CLASSTEST,classtest);contentValues.put(SESSIONAL,sessional);
        contentValues.put(ASSIGNMENT,assignment);
        return sqLiteDatabase.insert(TABLE_NAME1,null,contentValues);
    }
    public Cursor getstudentmarks(String sql) {
        return sqLiteDatabase.rawQuery(sql,null);
    }

    public static class MyDBAdapter extends SQLiteOpenHelper {
        public MyDBAdapter(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE);
            db.execSQL(DB_CREATE1);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exists "+TABLE_NAME);
            db.execSQL("drop table if exists "+TABLE_NAME1);
            onCreate(db);

        }
    }
}
