package com.example.lab_10;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditDataActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    String taskID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_data);

        Toast.makeText(this, "edit data view", Toast.LENGTH_SHORT).show();
        Intent intent = getIntent();

        Button back = findViewById(R.id.backBtn);
        back.setOnClickListener(v -> finish());
    }

    public void searchTask(View view){

        EditText editText = findViewById(R.id.searchText);
        EditText resultText = findViewById(R.id.searchResultText);
        Button update = findViewById(R.id.updateBtn);
        String taskDesc = editText.getText().toString();
        database = FirebaseDatabase.getInstance("enter token here");
        databaseReference = database.getReference("tasks");
        databaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful() ) {
                    Log.d("SEARCH_DB", "found DB");
                    Toast.makeText(EditDataActivity.this, "database connected", Toast.LENGTH_SHORT).show();
                    DataSnapshot snapshot = task.getResult();
                    boolean found = false;
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        String description = childSnapshot.child("description").getValue(String.class);
                        if (description != null && description.equals(taskDesc)) {
                            found = true;
                            taskID = childSnapshot.getKey();
                            Toast.makeText(EditDataActivity.this, "Match found!", Toast.LENGTH_SHORT).show();
                            editText.setText("");
                            resultText.setText(description);
                            updateEdits(taskID);
                            update.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(taskID != null){
                                        Log.d("SEARCH_DB", "Update button pressed");
                                        updateEdits(taskID);
                                    }else{
                                        Toast.makeText(EditDataActivity.this, "No task selected for update", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            break;
                        }
                    }
                    if(!found){
                        Toast.makeText(EditDataActivity.this, "No task found with that description", Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Toast.makeText(EditDataActivity.this, "Test failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void updateEdits(String task_key){
        EditText updateText = findViewById(R.id.searchResultText);
        String editedTask = updateText.getText().toString();
        databaseReference.child(task_key).child("description").setValue(editedTask)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Log.d("SEARCH_DB", "task description changed ");
                            Toast.makeText(EditDataActivity.this, "Task updated successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d("SEARCH_DB", "error editing task");
                            Toast.makeText(EditDataActivity.this, "Failed to update task", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}