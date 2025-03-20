package com.example.lab_10;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lab_10.Task.Task;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Task> itemsList = new ArrayList<Task>();
    ArrayAdapter<Task> itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void addData(View view){
        Intent addintent = new Intent(this, AddActivity.class);
        startActivity(addintent);
    }
    public void editData(View view){
        Intent editintent = new Intent(this, EditDataActivity.class);
        startActivity(editintent);
    }
    public void loadData(View view){
        Intent loadintent = new Intent(this, LoadDataActivity.class);
        startActivity(loadintent);
    }
}