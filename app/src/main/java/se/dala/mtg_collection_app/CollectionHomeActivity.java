package se.dala.mtg_collection_app;

import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CollectionHomeActivity extends AppCompatActivity {

    private FirebaseAuth authentication;
    private FirebaseUser currentUser;
    private List<String> sets = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_collection_home);
        setSets();
        authentication = FirebaseAuth.getInstance();

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setSubtitle("");

        /*GridLayout grid = findViewById(R.id.mainGrid);
        for (String set : sets) {
            grid.addView(findViewById(R.id.test_text));
        }*/

    }

    @Override
    public void onStart() {
        super.onStart();
        currentUser = authentication.getCurrentUser();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            authentication.signOut();
            Intent intent = new Intent(this, SignInActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void setSets() {
        AssetManager assetsManager = getApplicationContext().getAssets(); // or getBaseContext()
        try{
            InputStream inputStream = assetsManager.open("sets.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String set;
            while((set = reader.readLine()) != null) {
                System.out.println(set);
                sets.add(set);
            }
            reader.close();
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    private void setGrid() {

    }

    public void startCollectionActivity(View view) {
        Intent intent = new Intent(this, CollectionActivity.class);
        startActivity(intent);
    }
}
