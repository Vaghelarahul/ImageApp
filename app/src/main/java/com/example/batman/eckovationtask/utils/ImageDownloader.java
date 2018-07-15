package com.example.batman.eckovationtask.utils;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.batman.eckovationtask.BuildConfig;
import com.example.batman.eckovationtask.entities.ImagesDataModel;
import com.example.batman.eckovationtask.interfaceapi.RetrofitApi;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImageDownloader implements LoaderManager.LoaderCallbacks<Bitmap> {

    private static final String TAG = ImageDownloader.class.getSimpleName();

    private static RetrofitApi retrofitApi;
    private static Retrofit retrofit;

    private static String method = "flickr.photos.search";
    private static String format = "json";

    private Context context;
    private ImageView imageView;
    private String url;
    private int loaderId;
    private LoaderManager loaderManager;

    public ImageDownloader(Context context, ImageView imageView, String url, int loaderId, LoaderManager loaderManager) {
        this.context = context;
        this.imageView = imageView;
        this.url = url;
        this.loaderId = loaderId;
        this.loaderManager = loaderManager;

        loaderManager.initLoader(loaderId, null, this);
    }

    public static RetrofitApi client(String baseUrl) {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(baseUrl)
                    .build();
            retrofitApi = retrofit.create(RetrofitApi.class);
        }
        return retrofitApi;
    }

    public static Bitmap getImage(String urlString) {

        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if (url == null) {
            return null;
        }

        HttpURLConnection urlConnection;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void fetchImageData(String url, String query, final ImageDataCall imageDataCall) {

        Call<ImagesDataModel> call = client(url).getImageData(method, BuildConfig.API_KEY, query, format, "1");
        Log.d(TAG, "Url: " + call.request().url());

        call.enqueue(new Callback<ImagesDataModel>() {
            @Override
            public void onResponse(Call<ImagesDataModel> call, Response<ImagesDataModel> response) {

                if (response != null) {
                    imageDataCall.onDataFetched(response.body());
                }
            }

            @Override
            public void onFailure(Call<ImagesDataModel> call, Throwable t) {
                imageDataCall.onError("Image load failed");
                if (t != null) {
                    Log.d(TAG, "onFailure: " + t.getMessage());
                }
            }
        });
    }

    @Override
    public Loader<Bitmap> onCreateLoader(int id, Bundle args) {
        if (url == null) return null;
        return new ImageAsyncTaskLoader(context, url);
    }

    @Override
    public void onLoadFinished(Loader<Bitmap> loader, Bitmap bitmap) {
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            loaderManager.destroyLoader(loaderId);
        }
    }

    @Override
    public void onLoaderReset(Loader<Bitmap> loader) {
    }

    public interface ImageDataCall {
        void onDataFetched(ImagesDataModel imagesData);

        void onError(String error);
    }

    public static class ImageAsyncTaskLoader extends AsyncTaskLoader<Bitmap> {

        private String url;

        public ImageAsyncTaskLoader(Context context, String url) {
            super(context);
            this.url = url;
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            forceLoad();
        }

        @Override
        public Bitmap loadInBackground() {
            return getImage(url);
        }
    }
}
