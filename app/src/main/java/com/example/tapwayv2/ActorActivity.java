package com.example.tapwayv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tapwayv2.PhoneActivity;
import com.example.tapwayv2.R;

public class ActorActivity extends AppCompatActivity {

    Button vendorBtn, deliveryBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actor);

        vendorBtn =  findViewById(R.id.vendor);
        vendorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ActorActivity.this, PhoneActivity.class);
                i.putExtra("type","1");
                startActivity(i);
            }
        });

        deliveryBtn =  findViewById(R.id.deliveryboy);
        deliveryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActorActivity.this,PhoneActivity.class);
                intent.putExtra("type","2");
                startActivity(intent);
            }
        });

    }



}