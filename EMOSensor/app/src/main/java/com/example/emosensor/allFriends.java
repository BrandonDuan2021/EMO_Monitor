package com.example.emosensor;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.emosensor.Emo.EmoHistory;
import com.example.emosensor.Emo.EmoHistoryData;
import com.example.emosensor.savedData.DataForUse;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link allFriends#newInstance} factory method to
 * create an instance of this fragment.
 */
public class allFriends extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public allFriends() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment allFriends.
     */
    // TODO: Rename and change types and number of parameters
    public static allFriends newInstance(String param1, String param2) {
        allFriends fragment = new allFriends();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_friends, container, false);
        LinearLayout friendList = view.findViewById(R.id.friendList);
        Button addFriendBtn = view.findViewById(R.id.addFriendBtn);


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User").child(DataForUse.userName).child("friends");
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child:dataSnapshot.getChildren()) {
                    View view = getLayoutInflater().inflate(R.layout.history_layout, null, false);
                    TextView historyName = view.findViewById(R.id.historyButton);
                    Button historyButton = view.findViewById(R.id.historyButton);
                    historyName.setText(child.getKey());
                    historyButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DataForUse.userToChatWith = child.getKey();
                            startActivity(new Intent(getActivity(), EmoHistoryData.class));
                        }
                    });
                    friendList.addView(view);



                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("warning", "loadPost:onCancelled",
                        databaseError.toException());
            }
        };
        ref.addValueEventListener(listener);
        addFriendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddFriend.class));
            }
        });

        return view;
    }
}