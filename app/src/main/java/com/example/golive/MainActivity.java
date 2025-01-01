package com.example.golive;



import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Random;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

        Button liveButton;
        TextInputEditText live_id_Input,name_input;
        String LiveId,name,UserID;


        SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences =  getSharedPreferences("name_pref", MODE_PRIVATE);

        liveButton = findViewById(R.id.go_live_button);
        live_id_Input = findViewById(R.id.live_id_input);
        name_input=findViewById(R.id.namp);

        name_input.setText(sharedPreferences.getString("name" ,""));


        live_id_Input.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                            LiveId= live_id_Input.getText().toString();
                            if (LiveId.length() == 0){
                                liveButton.setText("Start Live");
                            }else {
                                liveButton.setText("GO LIVE");
                            }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                }
        );

        liveButton.setOnClickListener(
                (callback) -> {
                    name = name_input.getText().toString();
                    if(name.isEmpty()){
                        name_input.setError("Name is Required");
                        name_input.requestFocus();
                        return;
                    }
                    LiveId = live_id_Input.getText().toString();
                    Log.i("MainActivity", "LiveId: " + LiveId);
                    if (LiveId.length() > 0 && LiveId.length() != 5) {
                        live_id_Input.setError("Invalid LIVE ID");
                        live_id_Input.requestFocus();
                        return;
                    }

                    startmeeting();

                }
        );


    }

        void startmeeting(){
            Log.i("Log","Test");

            sharedPreferences.edit().putString("name",name).apply();

            boolean isHost = true;
            if(LiveId.length() == 5 ){
                isHost = false;
            }else {
                Log.i("Log", "IN START MEETING");

                LiveId = generateLiveID();
            }
                UserID = UUID.randomUUID().toString();
                Log.i("MainActivity", "LiveId: " + LiveId);

                Intent intent = new Intent(MainActivity.this, LiveActivity.class);
                intent.putExtra("user_id",UserID);
                intent.putExtra("name",name);
                intent.putExtra("live_id",LiveId);
                intent.putExtra("host",isHost);
                startActivity(intent);



        }

        String generateLiveID(){
        StringBuilder id = new StringBuilder();
        while (id.length() != 5){
            int random = new Random().nextInt(10);
            id.append(random);
            }
            return id.toString();
        }

}