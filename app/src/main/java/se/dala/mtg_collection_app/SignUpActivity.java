package se.dala.mtg_collection_app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    private static final String LOG_TAG = SignInActivity.class.getSimpleName();
    private FirebaseAuth mAuth;
    private final String authFailed = "Authentication Failed.";
    private final String authConfirmed = "Authentication Success.";
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        toast = Toast.makeText(SignUpActivity.this, "",
                Toast.LENGTH_LONG);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    public void startSecondActivity(View view) {
        EditText userNameInput = findViewById(R.id.username);
        EditText passwordInput = findViewById(R.id.password);
        EditText passwordValidationInput = findViewById(R.id.validation);
        String userName = userNameInput.getText().toString();
        String password = passwordInput.getText().toString();
        String passwordValidation = passwordValidationInput.getText().toString();
        if (validateSignupCredentials(userName, password, passwordValidation)) {
            mAuth.createUserWithEmailAndPassword(userName, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                // If sign in succeeds, display a message(toast) to the user.
                                Log.i(LOG_TAG, "signInWithEmail: success");
                                setToastText(authConfirmed);
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message(toast) to the user.
                                Log.w(LOG_TAG, "signInWithEmail: failure", task.getException());
                                setToastText(authFailed).show();
                                updateUI(null);
                            }
                        }
                    });
        } else {
            //Todo Make text visable: Passwords don't match
        }


    }

    public void updateUI(FirebaseUser user) {
        if(user != null) {
            Intent intent = new Intent(this, CollectionHomeActivity.class);
            startActivity(intent);
        }
    }

    private boolean validateSignupCredentials(String username, String password, String passwordValidation) {
        //Todo Implement username validation.
        return validatePassword(password, passwordValidation);
    }

    private boolean validatePassword(String password, String passwordValidation) {
        return password.equals(passwordValidation);
    }

    private Toast setToastText(String text) {
        toast.setText(text);
        return toast;
    }
}
