package com.example.tapwayv2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerificationActivity extends AppCompatActivity {

    Button confirm;
    Button resendCode;
    EditText receivedCode;
    String verificationCode;
    String userType;
    String phoneNumber;
    PhoneAuthCredential credential;
    FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        mAuth = FirebaseAuth.getInstance();

        receivedCode = findViewById(R.id.otp);
        confirm = findViewById(R.id.login);
        resendCode = findViewById(R.id.resend_code);
        progressBar = findViewById(R.id.cyclicProgress);

        //Obtaining the phone number and the user type from the previous activity
        phoneNumber = getIntent().getStringExtra("phone_number");
        userType = getIntent().getStringExtra("type");

        sendVerificationCode();

        //checking the received code manually
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code  = receivedCode.getText().toString().trim();
                if (code.isEmpty() || code.length() < 6 || code.length() > 6) {
                    Toast.makeText(VerificationActivity.this, "Code length should be 6", Toast.LENGTH_SHORT).show();
                    receivedCode.requestFocus();

                }
                progressBar.setVisibility(View.VISIBLE);
                verifyCode(code);

            }
        });

        resendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendVerificationCode();
            }
        });

    }

    //the function to verify the code with the user input code
    private void verifyCode (String code) {
        credential = PhoneAuthProvider.getCredential(verificationCode, code);
        signInWithCredential(credential);
    }

    //this checks signs in the user with the credential received according to the user type and the code
    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);

                            //Leading the user to different registration pages according to the user type
                            if (userType.equals("1")) {
                                Intent intent = new Intent(VerificationActivity.this,VendorRegistrationActivity.class);
                                //Stack is cleared so that the user can directly exit the app on "BACK" button press and not go to the login page again
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.putExtra("phone_number", phoneNumber);
                                intent.putExtra("type", userType.trim());
                                startActivity(intent);
                            }
                            else {
                                Intent intent = new Intent(VerificationActivity.this,DeliveryBoyRegistrationActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.putExtra("phone_number", phoneNumber);
                                intent.putExtra("type", userType.trim());
                                startActivity(intent);
                            }

                        }
                        else {
                            Toast.makeText(VerificationActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

    //Sending the Message to the user with the code
    private void sendVerificationCode() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );
        Toast.makeText(this, "Sending Code", Toast.LENGTH_SHORT).show();
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        //Automatic scanning of the code and inserting it going to the next activity
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            Toast.makeText(VerificationActivity.this, "Instant Verification is getting instantiated", Toast.LENGTH_SHORT).show();
            if (code != null) {
                Toast.makeText(VerificationActivity.this, code, Toast.LENGTH_SHORT).show();
                receivedCode.setText(code);
                verifyCode(code);
            }

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(VerificationActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

        }

        @Override
        //Code is taken for manual entry verification
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationCode = s;
            Toast.makeText(VerificationActivity.this, "Verification Code Sent", Toast.LENGTH_SHORT).show();
        }
    };
}