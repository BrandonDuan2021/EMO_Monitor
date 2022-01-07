package com.example.emosensor.Emo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.emosensor.R;
import com.example.emosensor.savedData.DataForUse;
import com.example.emosensor.savedData.EmoWords;
import com.example.emosensor.savedData.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class EmoHistoryData extends AppCompatActivity {
    Button emoSend;
    EditText emoWords;
    LinearLayout scrollView;
    TextView userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emo_event);

        emoSend = findViewById(R.id.sendEmo);
        scrollView = findViewById(R.id.LinearLayoutEmo);
        emoWords = findViewById(R.id.emoEntries);
        userName = findViewById(R.id.userNameShowOnScr);
        userName.setText(DataForUse.userToChatWith);

        //Intent intent = getIntent();
        //Bundle bundle = intent.getExtras();
        //String childName = (String)bundle.get("EmoName");


        //DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("EmoWords").child(childName);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("User").child(DataForUse.userName).child("friends");
        ValueEventListener idGetter = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataForUse.chatId = (String) dataSnapshot.child(DataForUse.userToChatWith).getValue();
                System.out.println(DataForUse.chatId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        };
        ref.addValueEventListener(idGetter);
        System.out.println("LLL");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference().child("Chats").child(DataForUse.chatId);
                ValueEventListener listener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Log.i("demo", "data changed");
                        scrollView.removeAllViews();
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            String time = (String) child.child("time").getValue();
                            String dialogue = (String) child.child("words").getValue();

                            View view = getLayoutInflater().inflate(R.layout.dialogue, null, false);
                            TextView timeView = view.findViewById(R.id.timeEmo);
                            TextView dialogueView = view.findViewById(R.id.dialogueEmo);
                            timeView.setText(time);
                            dialogueView.setText(dialogue);
                            scrollView.addView(view);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("warning", "loadPost:onCancelled",
                                databaseError.toException());
                    }
                };
                chatRef.addValueEventListener(listener);

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
                        EmoWords toBeUploaded = new EmoWords(time, words, User.userName);
                        chatRef.child(time).setValue(toBeUploaded);


                    }
                });


            }

            private void chatIdGetter() {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("User").child(DataForUse.userName).child("friends");
                ValueEventListener idGetter = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        System.out.println("LLL");
                        String x = (String) dataSnapshot.child(DataForUse.userToChatWith).getValue();
                        System.out.println(x);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                };
                ref.addValueEventListener(idGetter);
            }

        }, 100);   //5 seconds

    }
}

