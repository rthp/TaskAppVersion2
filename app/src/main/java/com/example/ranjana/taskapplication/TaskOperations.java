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
public class TaskOperations {
    private TaskDBOpenHelper myTaskDBHelper;

    public TaskOperations(Context context) {
        myTaskDBHelper = new TaskDBOpenHelper(context);
    }

    public int addTask(Task task) {
        //get Database from SQLiteDatabase class
        SQLiteDatabase db = myTaskDBHelper.getWritableDatabase();
        ContentValues values = putContentValues(task);

        //insert all those datas
        int task_Id = (int) db.insert(Task.TASK_TABLE, null, values);
        db.close();
        return task_Id;
    }

    public void deleteTask(int task_Id) {
        SQLiteDatabase db = myTaskDBHelper.getWritableDatabase();
        db.delete(Task.TASK_TABLE, Task.TASK_KEY_ID + "= ?", new String[]{String.valueOf(task_Id)});
        db.close(); // Closing database connection
    }

    public void editTask(Task task) {
        SQLiteDatabase db = myTaskDBHelper.getWritableDatabase();
        ContentValues values = putContentValues(task);

        db.update(Task.TASK_TABLE, values, Task.TASK_KEY_ID + "= ?", new String[]{String.valueOf(task.task_Id)});
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

    public ArrayList<HashMap<String, String>> getAllTasks() {
        //Open connection to read only
        DBAndSelectQuery DBAndSelectQuery = new DBAndSelectQuery().invoke();
        SQLiteDatabase db = DBAndSelectQuery.getDb();
        String selectQueryforAllTasks = DBAndSelectQuery.getSelectQueryforAllTasks();
        ArrayList<HashMap<String, String>> taskList = DBAndSelectQuery.getTaskList();

        Cursor cursor = db.rawQuery(selectQueryforAllTasks, null);
        // looping through all rows and adding to list

        cursorMove(taskList, cursor);

        cursor.close();
        db.close();
        return taskList;
    }

    public ArrayList<HashMap<String, String>> getAllTasksSortByDate() {
        //Open connection to read only
        DBAndSelectQuery DBAndSelectQuery = new DBAndSelectQuery().invoke();
        SQLiteDatabase db = DBAndSelectQuery.getDb();
        String selectQueryforAllTasks = DBAndSelectQuery.getSelectQueryforAllTasks();
        ArrayList<HashMap<String, String>> taskList = DBAndSelectQuery.getTaskList();

        Cursor cursor = db.rawQuery(selectQueryforAllTasks + " ORDER BY " + Task.COLUMN_TASK_DATE + " ASC", null);

        cursorMove(taskList, cursor);

        cursor.close();
        db.close();
        return taskList;
    }

    public ArrayList<HashMap<String, String>> getAllHighPriorityTasks() {
        //Open connection to read only
        DBAndSelectQuery DBAndSelectQuery = new DBAndSelectQuery().invoke();
        SQLiteDatabase db = DBAndSelectQuery.getDb();
        ArrayList<HashMap<String, String>> taskList = DBAndSelectQuery.getTaskList();
        String Query = "Select * from " + Task.TASK_TABLE + " where " + Task.COLUMN_TASK_PRIORITY + " =?";
        Cursor cursor = db.rawQuery(Query, new String[]{"High"});
        cursorMove(taskList, cursor);

        cursor.close();
        db.close();
        return taskList;
    }


    private void cursorMove(ArrayList<HashMap<String, String>> taskList, Cursor cursor) {
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> task = new HashMap<>();
                task.put("id", cursor.getString(cursor.getColumnIndex(Task.TASK_KEY_ID)));
                task.put("name", cursor.getString(cursor.getColumnIndex(Task.COLUMN_TASK_TITLE)));
                taskList.add(task);

            } while (cursor.moveToNext());
        }
    }

    public Task getSingleTask(int Id) {
        SQLiteDatabase db = myTaskDBHelper.getReadableDatabase();
        String selectQueryforTaskById = "SELECT  " +
                Task.TASK_KEY_ID + "," +
                Task.COLUMN_TASK_TITLE + "," +
                Task.COLUMN_TASK_DETAILS + "," +
                Task.COLUMN_TASK_PRIORITY + "," +
                Task.COLUMN_TASK_DATE +
                " FROM " + Task.TASK_TABLE
                + " WHERE " +
                Task.TASK_KEY_ID + "=?";

        Task task = new Task();

        Cursor cursor = db.rawQuery(selectQueryforTaskById, new String[]{String.valueOf(Id)});

        if (cursor.moveToFirst()) {
            do {
                task.task_Id = cursor.getInt(cursor.getColumnIndex(Task.TASK_KEY_ID));
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

    protected class DBAndSelectQuery {
        private SQLiteDatabase db;
        private String selectQueryforAllTasks;
        private ArrayList<HashMap<String, String>> taskList;

        public SQLiteDatabase getDb() {
            return db;
        }

        public String getSelectQueryforAllTasks() {
            return selectQueryforAllTasks;
        }

        public ArrayList<HashMap<String, String>> getTaskList() {
            return taskList;
        }

        public DBAndSelectQuery invoke() {
            db = myTaskDBHelper.getReadableDatabase();
            selectQueryforAllTasks = "SELECT  " +
                    Task.TASK_KEY_ID + "," +
                    Task.COLUMN_TASK_TITLE + "," +
                    Task.COLUMN_TASK_DETAILS + "," +
                    Task.COLUMN_TASK_PRIORITY + "," +
                    Task.COLUMN_TASK_DATE +
                    " FROM " + Task.TASK_TABLE;

            taskList = new ArrayList<>();
            return this;
        }
    }
}
