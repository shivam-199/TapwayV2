package com.example.tapwayv2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class PhoneActivity extends AppCompatActivity {

    EditText phoneNumberField;      //Field to enter the number
    ImageButton proceed;            //Button which send the number to the server
    String phoneNumber;
    String userType;
    Intent intent;
    DatabaseReference databaseReference;
    ProgressBar progressBar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        FirebaseApp.initializeApp(this);

        mAuth = FirebaseAuth.getInstance();

        phoneNumberField = findViewById(R.id.phone_number);
        proceed = findViewById(R.id.insert_number);
        progressBar = findViewById(R.id.cyclicProgress);


        //Obtaining the user type which we require for the next activity
        userType = getIntent().getStringExtra("type");


        //sending the phone number of the user to the next activity so that the message containing the code can be sent
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Validation for the phone number
                phoneNumber = phoneNumberField.getText().toString().trim();
                phoneNumber = "+91" + phoneNumber;
                progressBar.setVisibility(View.VISIBLE);


                    if (phoneNumber.isEmpty() || phoneNumber.length() < 13 || phoneNumber.length() > 13) {
                        Toast.makeText(PhoneActivity.this, "10 Digit Phone Number Required", Toast.LENGTH_SHORT).show();
                        phoneNumberField.requestFocus();
                    }
                    //concatenating the country code +91 for India and sending it to the next activity

                    // Toast.makeText(getApplicationContext(), phoneNumber+"onclick", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    intent = new Intent(PhoneActivity.this, VerificationActivity.class);
                    intent.putExtra("phone_number", phoneNumber);
                    intent.putExtra("type", userType.trim());
                    startActivity(intent);

                }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(PhoneActivity.this,MainActivity.class));
        }

    }
}


/*

 */