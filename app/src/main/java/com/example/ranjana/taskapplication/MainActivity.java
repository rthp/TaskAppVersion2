package com.example.ranjana.taskapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends Activity implements android.view.View.OnClickListener {

    private Button btnAddTask, btnViewAllTasks;
    private TextView task_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddTask = (Button) findViewById(R.id.btnAddTask);
        btnAddTask.setOnClickListener(this);

        btnViewAllTasks = (Button) findViewById(R.id.btnViewTasks);
        btnViewAllTasks.setOnClickListener(this);

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

    @Override
    public void onClick(View view) {
        if (view == findViewById(R.id.btnAddTask)) {
            btnAddTask.setAlpha(0.7f);
            Intent intent = new Intent(this, TaskDetails.class);
            intent.putExtra("task_Id", 0);
            startActivity(intent);

        } else {
            btnViewAllTasks.setAlpha(0.7f);
            TaskOperations taskCollection = new TaskOperations(this);
            ArrayList<HashMap<String, String>> taskList = taskCollection.getAllTasks();
            if (taskList.size() != 0) {
                Intent intent = new Intent(this, ViewTasks.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Empty Task List!", Toast.LENGTH_SHORT).show();
            }

        }
    }

}
