package com.example.ranjana.taskapplication;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends ListActivity  implements android.view.View.OnClickListener{

    Button btnAddTask,btnViewAllTasks;
    TextView task_Id;

    @Override
    public void onClick(View view) {
        if (view== findViewById(R.id.btnAddTask)){

            Intent intent = new Intent(this,TaskDetails.class);
            intent.putExtra("task_Id",0);
            startActivity(intent);

        }else {

            TaskContainer taskCollection = new TaskContainer(this);

            ArrayList<HashMap<String, String>> taskList =  taskCollection.getTaskList();
            if(taskList.size()!=0) {
                ListView listView = getListView();
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        task_Id = (TextView) view.findViewById(R.id.task_Id);
                        String taskId = task_Id.getText().toString();
                        Intent objIndent = new Intent(getApplicationContext(), TaskDetails.class);
                        objIndent.putExtra("task_Id", Integer.parseInt(taskId));
                        startActivity(objIndent);
                    }
                });
                ListAdapter adapter = new SimpleAdapter
                        ( MainActivity.this,taskList, R.layout.view_each_task, new String[] { "id","name"}, new int[] {R.id.task_Id, R.id.task_Name});
                setListAdapter(adapter);
            }else{
                Toast.makeText(this,"Empty Task List!",Toast.LENGTH_SHORT).show();
            }

        }
    }

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

}
