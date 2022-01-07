package com.example.emosensor.savedData;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.emosensor.Emo.EmoHistoryData;
import com.example.emosensor.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DataForUse {
    public static String userName;
    public static String userToChatWith;
    public static String chatId;

    public static ArrayList<String[]> getFriendList(){
        ArrayList<String[]> friendList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User").child(DataForUse.userName).child("friends");
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child:dataSnapshot.getChildren()) {
                    String[] friend = new String[2];
                    friend[0] = (String)child.getKey();
                    friend[1] = (String)child.getValue();
                    friendList.add(friend);
                    System.out.println(friendList);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("warning", "loadPost:onCancelled",
                        databaseError.toException());
            }
        };
        ref.addValueEventListener(listener);
        return friendList;
    }
}
