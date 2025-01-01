package com.example.golive;

import static com.example.golive.R.id.live_id_input1;
//
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.zegocloud.uikit.prebuilt.livestreaming.ZegoUIKitPrebuiltLiveStreamingConfig;
import com.zegocloud.uikit.prebuilt.livestreaming.ZegoUIKitPrebuiltLiveStreamingFragment;

public class LiveActivity extends AppCompatActivity{

    String UserId , name , liveId;
    boolean isHost;
    TextView liveidText;
    ImageView sharebtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);

        liveidText = findViewById(live_id_input1);
        sharebtn = findViewById(R.id.share_button);



        UserId = getIntent().getStringExtra("user_id");
        name = getIntent().getStringExtra("name");
        liveId = getIntent().getStringExtra("live_id");
        isHost = getIntent().getBooleanExtra("host",true);
        liveidText.setText(liveId);

        addfragment();
        sharebtn.setOnClickListener((v) -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("text/plain");

            intent.putExtra(Intent.EXTRA_TEXT,"Join my linve in LiveGram app \n Live ID : "+ liveId );
            startActivity(Intent.createChooser(intent,"Share via"));
        });

    }


    void addfragment(){
        ZegoUIKitPrebuiltLiveStreamingConfig   config;
        if(isHost){
           config = ZegoUIKitPrebuiltLiveStreamingConfig.host();
        }else {
            config = ZegoUIKitPrebuiltLiveStreamingConfig.audience();
        }
        ZegoUIKitPrebuiltLiveStreamingFragment Fragment = ZegoUIKitPrebuiltLiveStreamingFragment.newInstance(
                AppConstants.appId,AppConstants.appSignature,UserId,name,liveId,config
        );
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,Fragment).commitNow();

    }

}
