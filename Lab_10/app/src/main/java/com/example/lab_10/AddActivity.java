package com.example.lab_10;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.lab_10.Task.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);
        Toast.makeText(this, "add data view", Toast.LENGTH_SHORT).show();
        Intent intent = getIntent();


        Button back = findViewById(R.id.backBtn);
        back.setOnClickListener(v -> finish());
    }
    public void addTask(View view) {

        @SuppressLint("WrongViewCast")
        EditText editText = findViewById(R.id.addTaskText);
        String taskDesc = editText.getText().toString();
        Task newTask = new Task(taskDesc);
        database = FirebaseDatabase.getInstance("token here");
        databaseReference = database.getReference("tasks");
        databaseReference.push().setValue(newTask);
        editText.setText("");
    }
}