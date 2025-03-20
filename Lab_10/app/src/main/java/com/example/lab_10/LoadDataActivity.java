package com.example.lab_10;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lab_10.Task.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LoadDataActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    private List<Task> itemsList = new ArrayList<Task>();
    ArrayAdapter<Task> itemsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_load_data);
        Toast.makeText(this, "load data view", Toast.LENGTH_SHORT).show();
        Intent intent = getIntent();

        database = FirebaseDatabase.getInstance("token here");
        databaseReference = database.getReference("tasks");
        Button back = findViewById(R.id.backBtn);
        back.setOnClickListener(v -> finish());

        itemsAdapter = new ArrayAdapter<Task>(this, android.R.layout.simple_list_item_1, itemsList);
        ListView listView = (ListView) findViewById(R.id.simpleListView);
        listView.setAdapter(itemsAdapter);
    }
    public void loadData(View view) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Toast.makeText(LoadDataActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                    String datalist = "";
                    for (DataSnapshot taskSnapshot : dataSnapshot.getChildren()) {
                        Task task = taskSnapshot.getValue(Task.class);
                        itemsAdapter.add(task);
//Logging the data from database
//Log.d("Main activity", "Task value is: " + task.getDescription());
                        datalist = datalist + task.getDescription() + "\n";
                    }
                    TextView show = findViewById(R.id.textView2);
                    show.setText(datalist);

                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
// Failed to read value
                Log.w("Main activity", "Failed to read value.", error.toException());
            }
        });
    }
}