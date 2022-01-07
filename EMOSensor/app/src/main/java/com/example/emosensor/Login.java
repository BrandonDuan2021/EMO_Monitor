package com.example.emosensor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.emosensor.Emo.EmoEvent;
import com.example.emosensor.Emo.HomePage;
import com.example.emosensor.savedData.DataForUse;
import com.example.emosensor.savedData.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    EditText userNameView;
    EditText passwordView;
    Button logInBtn;
    Button signUpBtn;
    int isExist = 0;
    int isValid = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userNameView = findViewById(R.id.userName);
        passwordView = findViewById(R.id.password);
        logInBtn = findViewById(R.id.logInBtn);
        signUpBtn = findViewById(R.id.signUpBtn);

        logInBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                User.userName = userNameView.getText().toString();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("User");
                isExist = 0;
                isValid = 0;
                ValueEventListener listener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ref.removeEventListener(this);
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            if (userNameView.getText().toString().equals("")) {
                                Toast toast = Toast.makeText(getApplicationContext(), "Username cannot be empty!", Toast.LENGTH_SHORT);
                                toast.setMargin(50, 50);
                                toast.show();
                                isValid = 1;
                                break;
                            } else if (passwordView.getText().toString().equals("")) {
                                Toast toast = Toast.makeText(getApplicationContext(), "Password cannot be empty!", Toast.LENGTH_SHORT);
                                toast.setMargin(50, 50);
                                toast.show();
                                isValid = 1;
                                break;
                            } else if(!child.child("userName").exists()){
                                break;
                            }
                            else if (child.child("userName").getValue(String.class).equals(userNameView.getText().toString()) && child.child("password").getValue(String.class).equals(passwordView.getText().toString())) {
                                isExist = 1;
                                break;
                            }
                        }
                        if(isValid == 0){
                            if (isExist == 0) {
                                Toast toast = Toast.makeText(getApplicationContext(), "Username or password not correct", Toast.LENGTH_SHORT);
                                toast.setMargin(50, 50);
                                toast.show();
                            } else {
                                //startActivity(new Intent(Login.this, MainActivity.class).putExtra("UserName", userNameView.getText().toString()));
                                DataForUse.userName = userNameView.getText().toString();
                                startActivity(new Intent(Login.this, HomePage.class).putExtra("UserName", userNameView.getText().toString()));
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
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User(userNameView.getText().toString(), passwordView.getText().toString());

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("User");
                isExist = 0;
                ValueEventListener listener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ref.removeEventListener(this);
                        for(DataSnapshot child:dataSnapshot.getChildren()) {
                            if(!child.child("userName").exists()){
                                break;
                            }
                            if(child.child("userName").getValue(String.class).equals(userNameView.getText().toString())){
                                isExist = 1;
                                break;
                            }
                        }

                        if(isExist == 0) {
                            System.out.println(isExist);
                            if(userNameView.getText().toString().equals("")){
                                Toast toast=Toast. makeText(getApplicationContext(),"Username cannot be empty!",Toast. LENGTH_SHORT);
                                toast. setMargin(50,50);
                                toast. show();
                            }else if(passwordView.getText().toString().equals("")){
                                Toast toast=Toast. makeText(getApplicationContext(),"Password cannot be empty!",Toast. LENGTH_SHORT);
                                toast. setMargin(50,50);
                                toast. show();

                            }else {
                                System.out.println(userNameView.getText().toString()+ "shit");
                                ref.child(userNameView.getText().toString()).setValue(user);
                                DataForUse.userName = userNameView.getText().toString();
                                //startActivity(new Intent(Login.this, MainActivity.class).putExtra("UserName", userNameView.getText().toString()));
                                startActivity(new Intent(Login.this, EmoEvent.class).putExtra("UserName", userNameView.getText().toString()));
                            }
                        }else{
                            System.out.println(isExist);
                            Toast toast=Toast. makeText(getApplicationContext(),"User already exists",Toast. LENGTH_SHORT);
                            toast. setMargin(50,50);
                            toast. show();
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
        });


    }
}