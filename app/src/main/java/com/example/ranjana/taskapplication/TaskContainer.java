package com.example.ranjana.taskapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ranjana on 8/24/2015.
 */
public class TaskContainer {
    private TaskDBOpenHelper myTaskDBHelper;

    public TaskContainer(Context context) {
        myTaskDBHelper = new TaskDBOpenHelper(context);
    }

    public int addTask(Task task) {
        //get Database from SQLiteDatabase class
        SQLiteDatabase db = myTaskDBHelper.getWritableDatabase();
        ContentValues values = putContentValues(task);

        //insert all those datas
        long task_Id = db.insert(Task.TASK_TABLE,null,values);
        db.close();
        return (int) task_Id;
    }

    public void deleteTask(int task_Id) {
        SQLiteDatabase db = myTaskDBHelper.getWritableDatabase();
        db.delete(Task.TASK_TABLE, Task.KEY_ID + "= ?", new String[]{String.valueOf(task_Id)});
        db.close(); // Closing database connection
    }

    public void ediTask(Task task) {
        SQLiteDatabase db = myTaskDBHelper.getWritableDatabase();
        ContentValues values = putContentValues(task);

        db.update(Task.TASK_TABLE, values, Task.KEY_ID + "= ?", new String[] { String.valueOf(task.task_Id) });
        db.close(); // Closing database connection
    }

    @NonNull
    private ContentValues putContentValues(Task task) {
        ContentValues values = new ContentValues();
        values.put(Task.COLUMN_TASK_TITLE, task.taskTitle);
        values.put(Task.COLUMN_TASK_DETAILS, task.taskDetails);
        values.put(Task.COLUMN_TASK_PRIORITY, task.taskPriority);
        values.put(Task.COLUMN_TASK_DATE, task.taskDate);
        return values;
    }

    public ArrayList<HashMap<String, String>> getTaskList() {
        //Open connection to read only
        SQLiteDatabase db = myTaskDBHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Task.KEY_ID + "," +
                Task.COLUMN_TASK_TITLE + "," +
                Task.COLUMN_TASK_DETAILS + "," +
                Task.COLUMN_TASK_PRIORITY + "," +
                Task.COLUMN_TASK_DATE +
                " FROM " + Task.TASK_TABLE;

        ArrayList<HashMap<String, String>> taskList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> task = new HashMap<String, String>();
                task.put("id", cursor.getString(cursor.getColumnIndex(Task.KEY_ID)));
                task.put("name", cursor.getString(cursor.getColumnIndex(Task.COLUMN_TASK_TITLE)));
                taskList.add(task);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return taskList;

    }

    public Task getTaskById(int Id){
        SQLiteDatabase db = myTaskDBHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Task.KEY_ID + "," +
                Task.KEY_ID + "," +
                Task.COLUMN_TASK_TITLE + "," +
                Task.COLUMN_TASK_DETAILS + "," +
                Task.COLUMN_TASK_PRIORITY + "," +
                Task.COLUMN_TASK_DATE +
                " FROM " + Task.TASK_TABLE
                + " WHERE " +
                Task.KEY_ID + "=?";

        int iCount =0;
        Task task = new Task();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                task.task_Id =cursor.getInt(cursor.getColumnIndex(Task.KEY_ID));
                task.taskTitle =cursor.getString(cursor.getColumnIndex(Task.COLUMN_TASK_TITLE));
                task.taskDetails =cursor.getString(cursor.getColumnIndex(Task.COLUMN_TASK_DETAILS));
                task.taskPriority =cursor.getString(cursor.getColumnIndex(Task.COLUMN_TASK_PRIORITY));
                task.taskDate=cursor.getString(cursor.getColumnIndex(Task.COLUMN_TASK_DATE));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return task;
    }
}
