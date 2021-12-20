package com.example.emosensor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.emosensor.Emo.EmoEvent;
import com.example.emosensor.Emo.EmoName;

public class FeelingEmo extends AppCompatActivity {
    Button saySth;
    Button history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeling_emo);

        saySth = findViewById(R.id.tellSth);
        history = findViewById(R.id.historyEmo);

        saySth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FeelingEmo.this, EmoName.class));
            }
        });
    }
}