package se.dala.mtg_collection_app.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import se.dala.mtg_collection_app.R;
import se.dala.mtg_collection_app.activity.adapters.GridCardAdapter;
import se.dala.mtg_collection_app.model.Expansion;
import se.dala.mtg_collection_app.service.ApiService;

public class CollectionActivity extends AppCompatActivity {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_collection);
        loadDataFromApi();
    }

    private void loadDataFromApi() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://mtgcollectionapi.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Single<Expansion> expansionSingle = apiService.getExpansionData(getIntent().getStringExtra("<StringName>"));

        expansionSingle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Expansion>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(Expansion expansion) {
                        List<String> cardUrls = new ArrayList<>();
                        for (int i = 0; i < expansion.getCards().size(); i++) {
                            cardUrls.add(expansion.getCards().get(i).getImageUrl());
                        }
                        updateUI(cardUrls);
                    }
                    @Override
                    public void onError(Throwable e) {
                        System.out.println(e.getMessage());
                    }

                });
    }

    private void updateUI(final List<String> cardUrls) {
        CollectionActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                GridView gridView = findViewById(R.id.gridView);
                GridCardAdapter adapter = new GridCardAdapter(CollectionActivity.this, cardUrls);
                gridView.setAdapter(adapter);
            }
        });
    }
}
