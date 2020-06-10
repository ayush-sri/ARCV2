package com.arc.arcv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arc.arcv2.Model.User;
import com.arc.arcv2.Utils.Helper;
import com.arc.arcv2.Views.SnackbarWrapper;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class activity_login extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "sign in" ;
    RelativeLayout main_layout;
    TextView set_title,sign_in, dial_code, sign_in_facebook, sign_in_google;
    EditText et_phonenum;
    Button user_login_btn;
    FirebaseAuth mAuth;
    GoogleSignInOptions gso;
    GoogleSignInClient signInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Helper.user = new User();
        mAuth = FirebaseAuth.getInstance();
        initUi();
        signInUsingGoogle();
        user_login_btn.setOnClickListener(this);
        sign_in_google.setOnClickListener(this);

    }
    public void signInUsingGoogle()
    {
        //Toast.makeText(this, "signInUsingGoogle()", Toast.LENGTH_SHORT).show();
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        signInClient = GoogleSignIn.getClient(this,gso);

    }
    private void signIn() {
        //Toast.makeText(this, "signIn()", Toast.LENGTH_SHORT).show();
        Intent signInIntent = signInClient.getSignInIntent();
        startActivityForResult(signInIntent, 11);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 11) {
            //Toast.makeText(this, "google sign in triggered", Toast.LENGTH_SHORT).show();
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                //Toast.makeText(this, "firebaseAuthWithGoogle(String idToken) about to invoke", Toast.LENGTH_SHORT).show();
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                Toast.makeText(this,"Google sign in failed", Toast.LENGTH_SHORT).show();
                // ...
            }
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null)
        {
            //startActivity(new Intent(this,activity_register.class));
            FirebaseAuth.getInstance().signOut();
        }


    }

    private void firebaseAuthWithGoogle(String idToken) {
        //Toast.makeText(this, "firebaseAuthWithGoogle(String idToken)", Toast.LENGTH_SHORT).show();
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //Toast.makeText(activity_login.this, "login success", Toast.LENGTH_SHORT).show();
                            Helper.user.setUser_email(user.getEmail());
                            Helper.user.setUser_phone(user.getPhoneNumber());
                            startActivity(new Intent(activity_login.this,activity_register.class));

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(activity_login.this, "Some error occured", Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }
    private void initUi() {
        //all layout elements
        main_layout = findViewById(R.id.main_layout);
        set_title = findViewById(R.id.title);
        et_phonenum = findViewById(R.id.phonenum);
        sign_in = findViewById(R.id.sign_in);
        sign_in_google = findViewById(R.id.sign_in_google);
        sign_in_facebook = findViewById(R.id.sign_in_facebook);
        user_login_btn = findViewById(R.id.login);
        dial_code = findViewById(R.id.dial_code);
        //methods for Animation,etc
        startEnteringAnimation();
        changeStatusBar();
        setTypeFace();
        givePermissions();
    }

    private void setTypeFace() {
        Typeface ealing_reg = Typeface.createFromAsset(getAssets(),"fonts/ealing_regular.otf");
        Typeface ealing_bold = Typeface.createFromAsset(getAssets(),"fonts/ealing_black.otf");
        set_title.setTypeface(ealing_bold);
        user_login_btn.setTypeface(ealing_reg);
        sign_in.setTypeface(ealing_reg);
        et_phonenum.setTypeface(ealing_reg);
        dial_code.setTypeface(ealing_bold);
    }

    @SuppressLint("NewApi")
    private void changeStatusBar() {
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.status_bar_color));
    }
    @SuppressLint("NewApi")
    private void givePermissions() {
        if(!Settings.canDrawOverlays(this))
        {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, 2);
        }
    }

    private void startEnteringAnimation() {
        Animation fade = AnimationUtils.loadAnimation(this,R.anim.fade_in);
        Animation slide_in_right = AnimationUtils.loadAnimation(this,R.anim.slide_in_right);
        Animation slide_in_left = AnimationUtils.loadAnimation(this,R.anim.slide_in_left);
        main_layout.startAnimation(fade);
        et_phonenum.startAnimation(slide_in_right);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id)
        {
            case R.id.login:
                Intent i = new Intent(this,
                        verify.class);
                i.putExtra("phone",et_phonenum.getText().toString());
                startActivity(i);
                break;
            case R.id.sign_in_google:
                signIn();
                break;
        }
    }
}
