package com.example.emosensor;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
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

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link allChats#newInstance} factory method to
 * create an instance of this fragment.
 */
public class allChats extends Fragment {
    LinearLayout chats;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public allChats() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment allChats.
     */
    // TODO: Rename and change types and number of parameters
    public static allChats newInstance(String param1, String param2) {
        allChats fragment = new allChats();
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
        View view = inflater.inflate(R.layout.fragment_all_chats, container, false);
        chats = view.findViewById(R.id.allChatScrlView);

        ArrayList<String[]> friendList = DataForUse.getFriendList();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Chats");
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Log.i("demo", "data changed");
                chats.removeAllViews();
                for(DataSnapshot child:dataSnapshot.getChildren()) {
                    for(String[] friend: friendList){
                        if(!friend[1].equals(child.getKey())){

                        }else{
                            View view = getLayoutInflater().inflate(R.layout.history_layout, null, false);
                            TextView historyName = view.findViewById(R.id.historyButton);
                            Button historyButton = view.findViewById(R.id.historyButton);
                            historyName.setText(friend[0]);
                            historyButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    DataForUse.userToChatWith = friend[0];
                                    startActivity(new Intent(getActivity(), EmoHistoryData.class).putExtra("EmoName", child.getKey()));
                                }
                            });
                            chats.addView(view);
                        }
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
        return view;
    }
}