package com.example.ranjana.taskapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ranjana on 8/24/2015.
 */
public class TaskDBOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="TaskDB";
    private static final int DATABASE_VERSION = 1;

    public TaskDBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String CREATE_TABLE_TASK = "CREATE TABLE " + Task.TASK_TABLE + "("
                + Task.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Task.COLUMN_TASK_TITLE + " TEXT, "
                + Task.COLUMN_TASK_DETAILS + " TEXT, "
                + Task.COLUMN_TASK_PRIORITY + " TEXT,"
                + Task.COLUMN_TASK_DATE + " TEXT )";
        database.execSQL(CREATE_TABLE_TASK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + Task.TASK_TABLE);
        onCreate(db);
    }

    public void addTask(String taskName,String taskDetails)
    {
        ContentValues values=new ContentValues(2);
        values.put("TaskName", taskName);
        values.put("TaskDetails", taskDetails);
        getWritableDatabase().insert("tasks", "name", values);

    }
}
