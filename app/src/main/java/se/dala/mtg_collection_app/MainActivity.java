package se.dala.mtg_collection_app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private FirebaseAuth authentication;
    private final String authFailed = "Authentication Failed.";
    private final String authConfirmed = "Authentication Success.";
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        TextView signupText = findViewById(R.id.signupText);
        SpannableString text = new SpannableString("Click here to signup");

        ForegroundColorSpan clickColor = new ForegroundColorSpan(getResources().getColor(R.color.colorSwitchGreen));
        ClickableSpan clickable = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SignupActivity.class);
                startActivity(intent);
            }
        };
        text.setSpan(clickable, 6, 10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.setSpan(clickColor, 6, 10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        signupText.setText(text);
        signupText.setMovementMethod(LinkMovementMethod.getInstance());

        authentication = FirebaseAuth.getInstance();
        toast = Toast.makeText(MainActivity.this, "",
                Toast.LENGTH_LONG);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = authentication.getCurrentUser();
        updateUI(currentUser);
    }

    public void startSecondActivity(View view) {
        EditText userNameInput = findViewById(R.id.username);
        EditText passwordInput = findViewById(R.id.password);
        String userName = userNameInput.getText().toString();
        String password = passwordInput.getText().toString();
        authentication.signInWithEmailAndPassword(userName, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = authentication.getCurrentUser();
                            Log.i(LOG_TAG, "Sign in with email: Success");
                            setToastText(authConfirmed);
                            updateUI(user);
                        } else {
                            Log.w(LOG_TAG, "Sign in with email: Failed", task.getException());
                            setToastText(authFailed).show();
                            updateUI(null);
                        }
                    }
                });
    }

    public void updateUI(FirebaseUser user) {
        if(user != null) {
            Intent intent = new Intent(this, CollectionHomeActivity.class);
            startActivity(intent);
        }
    }

    private Toast setToastText(String text) {
        toast.setText(text);
        return toast;
    }
}
