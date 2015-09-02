package com.example.ranjana.taskapplication;

/**
 * Created by Ranjana on 8/24/2015.
 */

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class TaskDetails extends ActionBarActivity implements android.view.View.OnClickListener {
    private Button btnSave, btnDelete, btnClose;
    private EditText editTaskTitle, editTaskDetails, editTaskDate, editTaskPriority;
    private RadioGroup editTaskPriorityRadio;
    private int _Task_Id = 0;
    private TaskOperations taskCollection;
    private Task task;
    private RadioButton editTaskPriorityHigh, editTaskPriorityLow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_fields);

        btnSave = (Button) findViewById(R.id.btnSaveTask);
        btnDelete = (Button) findViewById(R.id.btnDeleteTask);
        btnClose = (Button) findViewById(R.id.btnCloseTask);

        editTaskTitle = (EditText) findViewById(R.id.editTaskTitle);
        editTaskDetails = (EditText) findViewById(R.id.editTaskDetails);
        editTaskPriorityRadio = (RadioGroup) findViewById(R.id.radioPriority);
        editTaskDate = (EditText) findViewById(R.id.editTaskDate);

        editTaskPriority = (EditText) findViewById(R.id.editTaskPriority);
        editTaskPriorityHigh = (RadioButton) findViewById(R.id.priorityHigh);
        editTaskPriorityLow = (RadioButton) findViewById(R.id.priorityLow);

        editTaskPriorityRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (editTaskPriorityHigh.isChecked()) {
                    editTaskPriority.setText("High");
                } else if (editTaskPriorityLow.isChecked()) {
                    editTaskPriority.setText("Low");
                }
            }
        });

        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String DateFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(DateFormat, Locale.US);
                editTaskDate.setText(sdf.format(myCalendar.getTime()));
            }

        };

        editTaskDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(TaskDetails.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnSave.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnClose.setOnClickListener(this);

        _Task_Id = 0;
        Intent intent = getIntent();
        _Task_Id = intent.getIntExtra("task_Id", 0);
        taskCollection = new TaskOperations(this);
        task = new Task();
        task = taskCollection.getSingleTask(_Task_Id);
        editTaskTitle.setText(task.taskTitle);
        editTaskDetails.setText(task.taskDetails);
        editTaskDate.setText(task.taskDate);
        editTaskPriority.setText(task.taskPriority);
        if ((editTaskPriority.getText().toString().equals("High"))) {
            editTaskPriorityHigh.setChecked(true);
        } else if ((editTaskPriority.getText().toString().equals("Low"))) {
            editTaskPriorityLow.setChecked(true);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
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
            btnSave.setAlpha(0.7f);
            taskCollection = new TaskOperations(this);
            task = new Task();
            task.taskTitle = editTaskTitle.getText().toString();
            task.taskDetails = editTaskDetails.getText().toString();
            task.taskPriority = editTaskPriority.getText().toString();
            task.taskDate = editTaskDate.getText().toString();
            task.task_Id = _Task_Id;

            if (_Task_Id == 0) {
                _Task_Id = taskCollection.addTask(task);
                Toast.makeText(this, "New Task Added", Toast.LENGTH_SHORT).show();
            } else {
                taskCollection.editTask(task);
                Toast.makeText(this, "Task was updated", Toast.LENGTH_SHORT).show();
            }
        } else if (view == findViewById(R.id.btnDeleteTask)) {
            btnDelete.setAlpha(0.7f);
            TaskOperations taskCollection = new TaskOperations(this);
            taskCollection.deleteTask(_Task_Id);
            Toast.makeText(this, "Task Deleted", Toast.LENGTH_SHORT);
            finish();
        } else if (view == findViewById(R.id.btnCloseTask)) {
            btnClose.setAlpha(0.7f);
            finish();
        }


    }

}