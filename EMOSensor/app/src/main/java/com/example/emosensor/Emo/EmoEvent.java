package com.example.emosensor.Emo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emosensor.R;
import com.example.emosensor.savedData.EmoWords;
import com.example.emosensor.savedData.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;
import java.util.zip.Inflater;

public class EmoEvent extends AppCompatActivity {
    Button emoSend;
    EditText emoWords;
    LinearLayout scrollView;
    ScrollView sclView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emo_event);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();


        emoSend = findViewById(R.id.sendEmo);
        scrollView = findViewById(R.id.LinearLayoutEmo);
        emoWords = findViewById(R.id.emoEntries);
        sclView = findViewById(R.id.scrollViewEmo);

        emoWords.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            emoWords.setText("");
                                        }
                                    });
        //delete
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Log.i("demo", "data changed");
                scrollView.removeAllViews();
                for(DataSnapshot child:dataSnapshot.child("familyla").child("EmoWords").child("FamilyChat").getChildren()) {
                    String time = (String)child.child("time").getValue();
                    String dialogue = (String)child.child("words").getValue();
                    String userName = (String)child.child("user").getValue();

                    View view = getLayoutInflater().inflate(R.layout.dialogue, null, false);
                    TextView timeView = view.findViewById(R.id.timeEmo);
                    TextView dialogueView = view.findViewById(R.id.dialogueEmo);
                    TextView userNameView = view.findViewById(R.id.nameEmo);
                    timeView.setText(time);
                    dialogueView.setText(dialogue);
                    userNameView.setText(userName);
                    scrollView.addView(view);
                }
                sclView.fullScroll(View.FOCUS_DOWN);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("warning", "loadPost:onCancelled",
                        databaseError.toException());
            }
        };
        ref.addValueEventListener(listener);
        //


        emoSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!emoWords.getText().toString().equals("")) {
                    final View view = getLayoutInflater().inflate(R.layout.dialogue, null, false);
                    String words = emoWords.getText().toString();
                    String time = Calendar.getInstance().getTime().toGMTString();
                    TextView name = view.findViewById(R.id.nameEmo);
                    TextView timeShow = view.findViewById(R.id.timeEmo);
                    TextView dialogue = view.findViewById(R.id.dialogueEmo);
                    timeShow.setText(time);
                    dialogue.setText(words);
                    name.setText(User.userName);

                    scrollView.addView(view);


                    emoWords.setText("");

                    //Intent intent = getIntent();
                    //Bundle bundle = intent.getExtras();
                    //String childName = (String)bundle.get("EmoName");


                    //Writing to a realtime database
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                    EmoWords toBeUploaded = new EmoWords(time, words, User.userName);
                    ref.child("familyla").child("EmoWords").child("FamilyChat").child(time).setValue(toBeUploaded);
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Please enter something!", Toast.LENGTH_SHORT);
                    toast.setMargin(50, 50);
                    toast.show();
                }




            }
        });
    }


}