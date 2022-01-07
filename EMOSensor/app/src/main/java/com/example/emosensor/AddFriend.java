package com.example.emosensor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emosensor.Emo.EmoHistory;
import com.example.emosensor.Emo.EmoHistoryData;
import com.example.emosensor.savedData.DataForUse;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.CountDownLatch;

public class AddFriend extends AppCompatActivity {
    Button add;
    EditText userName;
    int friendIndicator;
    int friendChecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);


        add = findViewById(R.id.addFriendBtnInPage);
        userName = findViewById(R.id.enterFriendName);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                friendChecker = 0;
                friendIndicator = 0;
                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("User");
                DatabaseReference ref = userRef.child(DataForUse.userName);

                new CountDownTimer(100, 100) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        ValueEventListener checker = new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                //Log.i("demo", "data changed");
                                for(DataSnapshot child:dataSnapshot.getChildren()) {
                                    if(child.getKey().equals(userName.getText().toString())){
                                        friendChecker = 1;
                                        break;
                                    }
                                }

                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.w("warning", "loadPost:onCancelled",
                                        databaseError.toException());
                            }
                        };
                        userRef.addValueEventListener(checker);

                        ValueEventListener listener = new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                //Log.i("demo", "data changed");
                                for(DataSnapshot child:dataSnapshot.child("friends").getChildren()) {
                                    if(child.getKey().equals(userName.getText().toString())){
                                        friendIndicator = 1;
                                    }
                                }

                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.w("warning", "loadPost:onCancelled",
                                        databaseError.toException());
                            }
                        };
                        ref.addValueEventListener(listener);
                    }

                    @Override
                    public void onFinish() {
                        if(friendChecker == 1 && friendIndicator == 0){
                            ref.child("friends").child(userName.getText().toString()).setValue(DataForUse.userName + userName.getText().toString());
                            FirebaseDatabase.getInstance().getReference("User").child(userName.getText().toString()).child("friends").child(DataForUse.userName).setValue(DataForUse.userName + userName.getText().toString());
                        }else if(friendChecker == 0){
                            Toast.makeText(getApplicationContext(), "User does not exist!", Toast.LENGTH_SHORT).show();
                        }else if(friendIndicator == 1){
                            Toast.makeText(getApplicationContext(), "Friend already exists!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }.start();



            }
        });


    }
}