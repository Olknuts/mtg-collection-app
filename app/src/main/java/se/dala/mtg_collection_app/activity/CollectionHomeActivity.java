package se.dala.mtg_collection_app.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import se.dala.mtg_collection_app.R;
import se.dala.mtg_collection_app.SignInActivity;
import se.dala.mtg_collection_app.activity.adapters.GridSymbolAdapter;
import se.dala.mtg_collection_app.activity.views.CustomButton;
import se.dala.mtg_collection_app.init.ExpansionSymbolInitializer;
import se.dala.mtg_collection_app.model.Expansion;
import se.dala.mtg_collection_app.model.ExpansionSymbol;

public class CollectionHomeActivity extends AppCompatActivity {

    private FirebaseAuth authentication;
    private FirebaseUser currentUser;
    private List<String> sets = new ArrayList<>();
    private List<ExpansionSymbol> expansionSymbols;
    List<Button> buttonsInGrid;

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
        expansionSymbols = ExpansionSymbolInitializer.symbolInitializer(CollectionHomeActivity.this);
        updateUI();
    }

    private void updateUI() {
        CollectionHomeActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                GridView gridView = findViewById(R.id.mainGrid);
                GridSymbolAdapter gridSymbolAdapter = new GridSymbolAdapter(CollectionHomeActivity.this, expansionSymbols);
                gridView.setAdapter(gridSymbolAdapter);
                addOnClickListener();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        currentUser = authentication.getCurrentUser();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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

    private void addOnClickListener() {
        buttonsInGrid = new ArrayList<>();
        GridView gridView = findViewById(R.id.mainGrid);
        for (int i = 0; i < gridView.getChildCount(); i++) {
            Button button = (Button)gridView.getChildAt(i);
            button.setOnClickListener(new ButtonClickListener());
            buttonsInGrid.add(button);
        }
    }

    public void startCollectionActivity(View view) {
        /*GridView gridView = findViewById(R.id.mainGrid);
        GridView grid = view.findViewWithTag()
        System.out.println(gridView.getChildCount() + "  ++++++++++++++++++++++++++++++++++++++++++++++");*/
        Button button = (Button)view;
        System.out.println(button.getTag().toString());
        Intent intent = new Intent(this, CollectionActivity.class).putExtra("<StringName>", button.getTag().toString());
        startActivity(intent);
    }

    class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            startCollectionActivity(v);
        }
    }
}
