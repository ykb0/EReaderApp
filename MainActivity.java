package com.consite.e_reader;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity {

    RelativeLayout layoutPhone ;
    RelativeLayout layoutOtp;

    EditText editTextPhone;
    EditText editTextOtp;

    ProgressBar progressBarPhone;
    //ProgressBar progressBarOtp;

    private FirebaseAuth mAuth;

    Button button;

    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    private static final String TAG = "PhoneAuthActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutPhone = findViewById(R.id.phone);
        layoutOtp = findViewById(R.id.otp);

        editTextPhone = findViewById(R.id.edit1);
        editTextOtp = findViewById(R.id.edit2);

        progressBarPhone = findViewById(R.id.progress1);
//        progressBarOtp = findViewById(R.id.progress2);

        button = findViewById(R.id.next);

        mAuth = FirebaseAuth.getInstance();

    }

    public void sendOtp(View view){

        progressBarPhone.setVisibility(View.VISIBLE);
        editTextPhone.setVisibility(View.INVISIBLE);
//        next.setVisibility(View.INVISIBLE);

        String phoneNumber = editTextPhone.getText().toString();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                MainActivity.this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                        signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {

                    }

                    @Override
                    public void onCodeSent(String verificationId,
                                           PhoneAuthProvider.ForceResendingToken token) {
                        // The SMS verification code has been sent to the provided phone number, we
                        // now need to ask the user to enter the code and then construct a credential
                        // by combining the code with a verification ID.
//                        Log.d(TAG, "onCodeSent:" + verificationId);

                        // Save verification ID and resending token so we can use them later
                        mVerificationId = verificationId;
                        mResendToken = token;

                        progressBarPhone.setVisibility(View.INVISIBLE);
                        //layoutOtp.setVisibility(View.VISIBLE);
//                        progressBarOtp.setVisibility(View.VISIBLE);

                        //if i set next.setEnabled(false) instead of next.setVisibility(View.INVISIBLE);
                        //THEN I WRITE HERE next.setText("Verify Code");
                        button.setText("Verify Code");
                        button.setEnabled(true);
                        Toast toast = Toast.makeText(MainActivity.this,"Enter OTP and This Toast is different from Others",Toast.LENGTH_SHORT);
                        toast.setGravity(10,10,10);
                        View view = toast.getView();
                        view.setBackgroundColor(Color.red(255)); // Not Working
                        toast.show();

                        // ...
                    }

                }
        );
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            startActivity(new Intent(MainActivity.this,SlideIntro.class));
                            finish();
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                          //  Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }
}
