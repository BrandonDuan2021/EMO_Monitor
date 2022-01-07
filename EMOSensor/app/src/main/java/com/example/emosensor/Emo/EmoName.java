package com.example.emosensor.Emo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.emosensor.ExpressLove;
import com.example.emosensor.FeelingEmo;
import com.example.emosensor.MainActivity;
import com.example.emosensor.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EmoName extends AppCompatActivity {
    EditText name;
    Button setName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emo_name);

        this.name = findViewById(R.id.emoEntries);
        this.setName = findViewById(R.id.sendEmo);

        setName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().contains("/")){
                    Toast toast = Toast.makeText(getApplicationContext(), "Invalid name!", Toast.LENGTH_SHORT);
                    toast.setMargin(50, 50);
                    toast.show();
                }else {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                    startActivity(new Intent(EmoName.this, EmoEvent.class).putExtra("EmoName", "Emo Event " + name.getText().toString()));
                }
            }
        });
    }
}
