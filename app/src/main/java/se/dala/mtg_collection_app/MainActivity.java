package se.dala.mtg_collection_app;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

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
    }
}
