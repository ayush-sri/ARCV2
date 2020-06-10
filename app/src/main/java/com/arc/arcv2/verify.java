package com.arc.arcv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.UriPermission;
import android.graphics.Typeface;
import android.hardware.input.InputManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arc.arcv2.Model.User;
import com.arc.arcv2.Utils.Helper;
import com.bigbangbutton.editcodeview.EditCodeListener;
import com.bigbangbutton.editcodeview.EditCodeView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.net.URI;
import java.util.concurrent.TimeUnit;

public class verify extends AppCompatActivity implements EditCodeListener {
    private static final String TAG = "verify";
    TextView otp_title,verify_title;
    EditCodeView otp_et;
    RelativeLayout main_layout;
    PhoneAuthProvider.ForceResendingToken mResendToken;
    String mVerificationId;
    FirebaseAuth mAuth;
    private String smscode;
    SharedPreferences sharedPreferences;

    String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        mAuth = FirebaseAuth.getInstance();
        Helper.user = new User();
        Bundle b = getIntent().getExtras();
        phone  = b.getString("phone");
        initUi();
        givePermissions();
        otp_et.setEditCodeListener(this);
        invokeOTPVerification("+91"+phone);
    }




    private void verifySignInCode(String code) {
        PhoneAuthCredential credential;
        credential = PhoneAuthProvider.getCredential(smscode,code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                hideKeyboard();
                                Snackbar.make(main_layout,"Wrong OTP",Snackbar.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

    private void givePermissions() {
        if(!Settings.canDrawOverlays(this))
        {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, 2);
        }
    }

    private void initUi() {
        main_layout = findViewById(R.id.main_layout_verif);
        otp_title = findViewById(R.id.otp_title);
        verify_title = findViewById(R.id.verify_title);
        otp_et = findViewById(R.id.verif);

        setTypeFace();
        startEnteringAnimation();
        changeStatusBar();

    }
    private void changeStatusBar() {
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.status_bar_color));
    }

    private void startEnteringAnimation() {
        Animation fade = AnimationUtils.loadAnimation(this,R.anim.fade_in);
        Animation slide_in_right = AnimationUtils.loadAnimation(this,R.anim.slide_in_right);
        Animation slide_in_left = AnimationUtils.loadAnimation(this,R.anim.slide_in_left);
        main_layout.startAnimation(fade);
        otp_et.startAnimation(slide_in_right);
    }

    private void setTypeFace() {
        Typeface ealing_reg = Typeface.createFromAsset(getAssets(),"fonts/ealing_regular.otf");
        Typeface ealing_bold = Typeface.createFromAsset(getAssets(),"fonts/ealing_black.otf");
        otp_title.setTypeface(ealing_reg);
        otp_title.setTypeface(ealing_reg);
    }

    public void invokeOTPVerification(String phoneNumber)
    {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,// Activity (for callback binding)
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential credential) {
                        // This callback will be invoked in two situations:
                        // 1 - Instant verification. In some cases the phone number can be instantly
                        //     verified without needing to send or enter a verification code.
                        // 2 - Auto-retrieval. On some devices Google Play services can automatically
                        //     detect the incoming verification SMS and perform verification without
                        //     user action.
                        Log.d(TAG, "onVerificationCompleted:" + credential);
                    }
                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        // This callback is invoked in an invalid request for verification is made,
                        // for instance if the the phone number format is not valid.
                        Log.w(TAG, "onVerificationFailed", e);

                        if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            // Invalid request
                            // ...
                            Toast.makeText(verify.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (e instanceof FirebaseTooManyRequestsException) {
                            // The SMS quota for the project has been exceeded
                            // ...
                            Toast.makeText(verify.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        // Show a message and update the UI
                        // ...
                    }

                    @Override
                    public void onCodeSent(@NonNull String verificationId,
                                           @NonNull PhoneAuthProvider.ForceResendingToken token) {
                        // The SMS verification code has been sent to the provided phone number, we
                        // now need to ask the user to enter the code and then construct a credential
                        // by combining the code with a verification ID.
                        Log.d(TAG, "onCodeSent:" + verificationId);

                        // Save verification ID and resending token so we can use them later
                        mVerificationId = verificationId;
                        mResendToken = token;
                        Snackbar.make(main_layout,"OTP sent",Snackbar.LENGTH_SHORT).show();
                        // ...
                    }
                }
        );

    }
    @Override
    public void onCodeReady(String code) {
            verifySignInCode(code);
    }

    private void hideKeyboard()
    {
        InputMethodManager inputManager = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        if(inputManager!=null)
        {
            inputManager.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),0);
        }
    }


}
