package se.dala.mtg_collection_app.service;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import se.dala.mtg_collection_app.model.Expansion;

public interface ApiService {

    @GET("set/{set}/")
    Single<Expansion> getExpansionData(@Path("set") String set);
}