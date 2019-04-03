package com.example.tapwayv2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.tapwayv2.Actors.DeliveryBoy;
import com.example.tapwayv2.Actors.Vendor;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class DeliveryBoyRegistrationActivity extends AppCompatActivity {

    EditText fullName;               //To get data
    ImageButton imgBtn;              // To upload image
    String phoneNumber,userType;     //Intent variables
    String boyName;
    DatabaseReference databaseItems; // Creating a reference to Firebase database
    final private int PICK_IMG_REQUEST = 71;
    private Uri filepath;
    StorageReference storageReference;
    FirebaseStorage storage;
    UploadTask uploadTask;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_boy_registration);

        fullName = findViewById(R.id.boy_name);
        imgBtn = findViewById(R.id.button_register_boy);

        phoneNumber = getIntent().getStringExtra("phone_number");
        userType = getIntent().getStringExtra("type");

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDeliveryBoyToDB();
                uploadImage();

            }
        });

        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

    }

    private void uploadImage() {

        if (filepath != null) {
            final StorageReference ref = storageReference.child("vendor/" + phoneNumber);
            uploadTask = ref.putFile(filepath);
        }

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
                imgBtn.setImageBitmap(bitmap);
            }
            catch (IOException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }
    }

    private void saveDeliveryBoyToDB() {

        boyName = fullName.getText().toString().trim();

        if(isAllFilled()){
            DeliveryBoy deliveryBoy = new DeliveryBoy(phoneNumber,boyName);
           databaseItems.child("DeliveryBoy").child(phoneNumber).setValue(deliveryBoy);

            Toast.makeText(getApplicationContext(), "User Registered", Toast.LENGTH_LONG).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(DeliveryBoyRegistrationActivity.this, MainActivity.class);
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
        if(boyName.isEmpty()){
            fullName.setError("Field cannot be empty");
            fullName.requestFocus();
            return false;
        }
        return true;
    }


}
