package com.example.batman.eckovationtask.interfaceapi;

import com.example.batman.eckovationtask.entities.ImagesDataModel;

import java.io.InputStream;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface RetrofitApi {

    @GET("rest/")
    Call<ImagesDataModel> getImageData(@Query("method") String method,
                                       @Query("api_key") String api_key,
                                       @Query("text") String text,
                                       @Query("format") String format,
                                       @Query("nojsoncallback") String nojsoncallback);

}
