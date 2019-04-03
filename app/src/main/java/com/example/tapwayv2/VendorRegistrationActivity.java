package com.example.tapwayv2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tapwayv2.Actors.Vendor;
import com.example.tapwayv2.Actors.Vendor;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class VendorRegistrationActivity extends AppCompatActivity{

    EditText fullNameET, plotNoET, areaNameET, cityNameET, stateNameET, pinCodeET, landMarkET;
    String fName,phoneNumber, plotNumber, area, city,state,pincode,landmark;
    ImageView vendorProfile;
    FirebaseStorage storage;
    StorageReference storageReference;
     UploadTask uploadTask;
    final private int PICK_IMG_REQUEST = 71;
    private Uri filepath;
    private DatabaseReference databaseItems;// Creating a reference to firebase database
    String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_registration);


        vendorProfile = findViewById(R.id.profileImage);
        phoneNumber = getIntent().getStringExtra("phone_number");
        userType = getIntent().getStringExtra("type");



        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        databaseItems = FirebaseDatabase.getInstance().getReference("TapwayTable");
        // Get phone number from the user id
        ImageButton saveBtn = findViewById(R.id.submitBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveVendorToDB();
                uploadImage();
            }
        });

        vendorProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Profile Picture"),PICK_IMG_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMG_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filepath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filepath);
                vendorProfile.setImageBitmap(bitmap);
            }
            catch (IOException e) {
                e.printStackTrace();

            }
        }
    }

    public void uploadImage(){
        if (filepath != null) {
            final StorageReference ref = storageReference.child("vendor/" + phoneNumber);
            uploadTask = ref.putFile(filepath);
        }

    }

    private void saveVendorToDB() {

        getEditTextDetails();
        if(isAllFilled()){
            Vendor newVendor = new Vendor(phoneNumber,fName,plotNumber,area,city,state,pincode,landmark);
            databaseItems.child("Vendor").child(phoneNumber).setValue(newVendor);

            Toast.makeText(getApplicationContext(), "User Registered", Toast.LENGTH_LONG).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(VendorRegistrationActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            },1200);

        }
        else {
            Toast.makeText(getApplicationContext(), "User Registration Failed", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean isAllFilled() {
        //This functions return true if all the fields are entered.

        if(fName.isEmpty()){
            fullNameET.setError("Field Required");
            fullNameET.requestFocus();
            return false;
        }
        if(plotNumber.isEmpty()){
            plotNoET.setError("Field Required");
            plotNoET.requestFocus();
            return false;
        }
        if(area.isEmpty()){
            areaNameET.setError("Field Required");
            areaNameET.requestFocus();
            return false;
        }if(city.isEmpty()){
            cityNameET.setError("Field Required");
            cityNameET.requestFocus();
            return false;
        }
        if(state.isEmpty()){
            stateNameET.setError("Field Required");
            stateNameET.requestFocus();
            return false;
        }
        if(pincode.isEmpty()){
            pinCodeET.setError("Field Required");
            pinCodeET.requestFocus();
            return false;
        }
        /*if(pincode.length() != 6){
            pinCodeET.setError("Pincode is of 6 characters");
            pinCodeET.requestFocus();
            return false;
        }*/
        if(landmark.isEmpty()){
            landMarkET.setError("Field Required");
            landMarkET.requestFocus();
            return false;
        }

        return true;
    }

    private void getEditTextDetails() {

        fullNameET = (EditText) findViewById(R.id.nameBox);
        plotNoET = (EditText) findViewById(R.id.plotNum);
        areaNameET = (EditText) findViewById(R.id.areaName);
        cityNameET = (EditText) findViewById(R.id.cityName);
        stateNameET = (EditText) findViewById(R.id.stateName);
        pinCodeET = (EditText) findViewById(R.id.pincode);
        landMarkET = (EditText) findViewById(R.id.landmark);


        fName = fullNameET.getText().toString().trim();
        plotNumber = plotNoET.getText().toString().trim();
        area = areaNameET.getText().toString().trim();
        city = cityNameET.getText().toString().trim();
        state = stateNameET.getText().toString().trim();
        pincode = pinCodeET.getText().toString().trim();
        landmark = landMarkET.getText().toString().trim();

    }
}