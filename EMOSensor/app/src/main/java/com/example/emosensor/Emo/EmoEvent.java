package com.example.emosensor.Emo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.emosensor.R;
import com.example.emosensor.savedData.EmoWords;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();


        emoSend = findViewById(R.id.sendEmo);
        scrollView = findViewById(R.id.LinearLayoutEmo);
        emoWords = findViewById(R.id.emoEntries);

        emoWords.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            emoWords.setText("");
                                        }
                                    });


        emoSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = getLayoutInflater().inflate(R.layout.dialogue, null, false);
                String words = emoWords.getText().toString();
                String time = Calendar.getInstance().getTime().toString();
                TextView timeShow = view.findViewById(R.id.timeEmo);
                TextView dialogue = view.findViewById(R.id.dialogueEmo);
                timeShow.setText(time);
                dialogue.setText(words);

                scrollView.addView(view);

                emoWords.setText("");



                //Writing to a realtime database
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                EmoWords toBeUploaded = new EmoWords(time, words, true);
                ref.child("EmoWords").child("test event").setValue(toBeUploaded);



            }
        });
    }


}