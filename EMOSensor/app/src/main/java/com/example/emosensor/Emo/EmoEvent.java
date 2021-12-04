package com.example.emosensor.Emo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.emosensor.R;

import java.util.Calendar;
import java.util.Date;
import java.util.zip.Inflater;

public class EmoEvent extends AppCompatActivity {
    Button emoSend;
    EditText emoWords;
    LinearLayout scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emo_event);

        emoSend = findViewById(R.id.sendEmo);
        scrollView = findViewById(R.id.LinearLayoutEmo);
        emoWords = findViewById(R.id.emoEntries);


        emoSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = getLayoutInflater().inflate(R.layout.dialogue, null, false);
                String words = emoWords.getText().toString();
                String time = Calendar.getInstance().getTime().toString();
                TextView timeShow = findViewById(R.id.timeEmo);
                TextView dialogue = findViewById(R.id.dialogueEmo);

                //scrollView.addView(view);

                timeShow.setText(time);
                dialogue.setText(words);

            }
        });

    }

}