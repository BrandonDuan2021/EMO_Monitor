package com.example.emosensor.Emo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.emosensor.FeelingEmo;
import com.example.emosensor.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EmoHistory extends AppCompatActivity {
    LinearLayout history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emo_history);

        history = findViewById(R.id.emoHistoryLayout);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("EmoWords");
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Log.i("demo", "data changed");
                for(DataSnapshot child:dataSnapshot.getChildren()) {
                    View view = getLayoutInflater().inflate(R.layout.history_layout, null, false);
                    TextView historyName = view.findViewById(R.id.historyButton);
                    Button historyButton = view.findViewById(R.id.historyButton);
                    historyName.setText(child.getKey());
                    historyButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(EmoHistory.this, EmoHistoryData.class).putExtra("EmoName", child.getKey()));
                        }
                    });
                    history.addView(view);



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
}