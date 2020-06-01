package com.example.restapi.OTP;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.restapi.MainActivity;
import com.example.restapi.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OtpActivity extends AppCompatActivity {

    EditText et_phone;
    Button btn_sendotp;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    ProgressDialog progressDialog;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        et_phone = findViewById(R.id.txt_phone);
        btn_sendotp = findViewById(R.id.btn_Login);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Tunggu Sob ...");

        btn_sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String phone_number = "+"+et_phone.getText().toString();

               if (phone_number.isEmpty()){
                   et_phone.setError("Harap Isi");
               }else{
                   progressDialog.show();
                   PhoneAuthProvider.getInstance().verifyPhoneNumber(
                           phone_number,        // Phone number to verify
                           60,                  // Timeout duration
                           TimeUnit.SECONDS,    // Unit of timeout
                           OtpActivity.this,                // Activity (for callback binding)
                           mCallbacks);         // OnVerificationStateChangedCallbacks
               }
            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                progressDialog.hide();
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                progressDialog.hide();
                btn_sendotp.setEnabled(true);
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.hide();
                        Intent verifIntent = new Intent(OtpActivity.this, VerifActivity.class);
                        verifIntent.putExtra("AuthCredential", s);
                        startActivity(verifIntent);
                    }
                }, 7000);

            }
        };
    }

    @Override
    protected void onStart(){
        super.onStart();
        if (mUser != null){
           sendUsertoHome();
        }
    }

    public void sendUsertoHome(){
        Intent homeIntent = new Intent(OtpActivity.this, MainActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeIntent);
        finish();
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(OtpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.hide();
                            sendUsertoHome();
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                progressDialog.hide();
                                Toast.makeText(getApplicationContext(), "Ada Masalah", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

}
