package com.example.ranjana.taskapplication;

/**
 * Created by Ranjana on 8/24/2015.
 */

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TaskDetails extends ActionBarActivity implements android.view.View.OnClickListener {

    Button btnSave, btnDelete, btnClose;
    EditText editTaskTitle, editTaskDetails, editTaskPriority, editTaskDate;
    private int _Task_Id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewtasks);

        btnSave = (Button) findViewById(R.id.btnSaveTask);
        btnDelete = (Button) findViewById(R.id.btnDeleteTask);
        btnClose = (Button) findViewById(R.id.btnCloseTask);

        editTaskTitle = (EditText) findViewById(R.id.editTaskName);
        editTaskDetails = (EditText) findViewById(R.id.editTaskDetails);
        editTaskPriority = (EditText) findViewById(R.id.editTaskPriority);
        editTaskDate = (EditText) findViewById(R.id.editTaskDate);

        btnSave.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnClose.setOnClickListener(this);


        _Task_Id = 0;
        Intent intent = getIntent();
        _Task_Id = intent.getIntExtra("task_Id", 0);
        TaskContainer taskCollection = new TaskContainer(this);
        Task task = new Task();
        task = taskCollection.getTaskById(_Task_Id);

        editTaskTitle.setText(task.taskTitle);
        editTaskDetails.setText(task.taskDetails);
        editTaskPriority.setText(task.taskPriority);
        editTaskDate.setText(task.taskDate);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view) {
        if (view == findViewById(R.id.btnSaveTask)) {
            TaskContainer taskCollection = new TaskContainer(this);
            Task task = new Task();
            task.taskTitle = editTaskTitle.getText().toString();
            task.taskDetails = editTaskDetails.getText().toString();
            task.taskPriority = editTaskPriority.getText().toString();
            task.taskDate = editTaskDate.getText().toString();
            task.task_Id = _Task_Id;

            if (_Task_Id == 0) {
                _Task_Id = taskCollection.addTask(task);
                Toast.makeText(this, "New Task Added", Toast.LENGTH_SHORT).show();
            } else {

                taskCollection.ediTask(task);
                Toast.makeText(this, "Task was updated", Toast.LENGTH_SHORT).show();
            }
        } else if (view == findViewById(R.id.btnDeleteTask)) {
            TaskContainer taskCollection = new TaskContainer(this);
            taskCollection.deleteTask(_Task_Id);
            Toast.makeText(this, "Task Deleted", Toast.LENGTH_SHORT);
            finish();
        } else if (view == findViewById(R.id.btnCloseTask)) {
            finish();
        }


    }

}