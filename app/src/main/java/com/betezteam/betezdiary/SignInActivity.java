package com.betezteam.betezdiary;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class SignInActivity extends AppCompatActivity {
    private GoogleSignInClient mGoogleSignInClient;
    private static final String TAG = "GoogleSigninActivity";
    private static final int RC_SIGN_IN = 2001;

    private FirebaseAuth mAuth;

    private Button nextButton;
    private SignInButton googleSignInButton;
    private TextView signInNotif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("415088894391-247c660ehd5kfevmnnnf5for5j2nln0q.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mAuth = FirebaseAuth.getInstance();
        Log.d("cg", "dmm");

        nextButton = findViewById(R.id.sign_in_next);
        nextButton.setVisibility(View.GONE);

        googleSignInButton = findViewById(R.id.google_sign_in_button);
        googleSignInButton.setVisibility(View.GONE);
        
        signInNotif = findViewById(R.id.sign_in_notif);
        
        assignEvents();
    }

    private void assignEvents() {
        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, LockRegister.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            signInNotif.setText("Trước tiên bạn phải đăng nhập");
            googleSignInButton.setVisibility(View.VISIBLE);
        } else {
            Intent intent = new Intent(this, LockActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            firebaseAuthWithGoogle(account);
            // Signed in successfully, show authenticated UI.
            // TODO: 6/13/2018 redirect to set lock pass
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            signInFailNotif();
            Log.d("cg1", GoogleSignInStatusCodes.getStatusCodeString(e.getStatusCode()));
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            // TODO: 6/14/2018   taskTodo
                            signInNotif.setText("Đăng nhập thành công! Nhấn \"Tiếp theo\" để thiết lập mật khẩu...");
                            nextButton.setVisibility(View.VISIBLE);
                            googleSignInButton.setVisibility(View.GONE);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            signInFailNotif();
                        }
                    }
                });
    }

    private void signInFailNotif() {
        TextView signInFailNotif = findViewById(R.id.sign_in_notif);
        signInFailNotif.setText(R.string.sign_in_fail_notif);
    }


    public static void signOut(Context context) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(context, LockActivity.class);
        context.startActivity(intent);
    }

    public void next(View v){
        // TODO: 6/14/2018 to the register screen
        finish();
    }
}
