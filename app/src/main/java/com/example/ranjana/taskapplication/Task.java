package com.example.ranjana.taskapplication;

/**
 * Created by Ranjana on 8/24/2015.
 */
public class Task {
   //Task Table Name
    public static final String TASK_TABLE = "Tasks";

    // Table contains these columns
    public static final String KEY_ID = "id";
    public static final String COLUMN_TASK_TITLE = "title";
    public static final String COLUMN_TASK_DETAILS = "details";
    public static final String COLUMN_TASK_PRIORITY = "priority";
    public static final String COLUMN_TASK_DATE = "date";

    //main fields
    public int task_Id;
    public String taskTitle;
    public String taskDetails;
    public String taskPriority;
    public String taskDate;
}
