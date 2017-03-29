package com.sagarnileshshah.carouselmvp.data.remote;


import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sagarnileshshah.carouselmvp.BuildConfig;
import com.sagarnileshshah.carouselmvp.data.DataSource;
import com.sagarnileshshah.carouselmvp.data.models.comment.Comment;
import com.sagarnileshshah.carouselmvp.data.models.photo.Photo;
import com.sagarnileshshah.carouselmvp.util.threading.MainUiThread;
import com.sagarnileshshah.carouselmvp.util.threading.ThreadExecutor;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RemoteDataSource extends DataSource {

    private static final String API_KEY = BuildConfig.FLICKR_API_KEY;
    private static final String BASE_API_URL = "https://api.flickr.com/services/rest/";
    private static final String QUERY_PARAM_API_KEY = "api_key";
    private static final String QUERY_PARAM_METHOD = "method";
    private static final String QUERY_PARAM_NO_JSON_CALLBACK = "nojsoncallback";
    private static final String QUERY_PARAM_FORMAT = "format";
    private static final String QUERY_PARAM_VALUE_JSON = "json";
    private static final String QUERY_PARAM_VALUE_NO_JSON_CALLBACK = "1";
    private static final String QUERY_PARAM_PER_PAGE = "per_page";
    private static final String QUERY_PARAM_VALUE_PER_PAGE = "10";
    private static final String QUERY_PARAM_PAGE = "page";
    private static final String QUERY_PARAM_PHOTO_ID = "photo_id";
    private static final String PHOTOS_ENDPOINT = "flickr.interestingness.getList";
    private static final String COMMENTS_ENDPOINT = "flickr.photos.comments.getList";

    private static RemoteDataSource remoteDataSource;
    private static OkHttpClient okHttpClient;

    private RemoteDataSource(OkHttpClient okhttpClient, MainUiThread mainUiThread,
                             ThreadExecutor threadExecutor) {
        super(mainUiThread, threadExecutor);
        okHttpClient = okhttpClient;
    }

    public static synchronized RemoteDataSource getInstance(MainUiThread mainUiThread,
                                                            ThreadExecutor threadExecutor) {
        if (remoteDataSource == null) {
            OkHttpClient okHttpClient =
                    new OkHttpClient.Builder().addNetworkInterceptor(new StethoInterceptor()).build();
            remoteDataSource = new RemoteDataSource(okHttpClient, mainUiThread, threadExecutor);
        }
        return remoteDataSource;
    }

    @Override
    public void getPhotos(int page, final GetPhotosCallback callback) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_API_URL).newBuilder();
        urlBuilder.addQueryParameter(QUERY_PARAM_API_KEY, API_KEY);
        urlBuilder.addQueryParameter(QUERY_PARAM_METHOD, PHOTOS_ENDPOINT);
        urlBuilder.addQueryParameter(QUERY_PARAM_NO_JSON_CALLBACK, QUERY_PARAM_VALUE_NO_JSON_CALLBACK);
        urlBuilder.addQueryParameter(QUERY_PARAM_FORMAT, QUERY_PARAM_VALUE_JSON);
        urlBuilder.addQueryParameter(QUERY_PARAM_PER_PAGE, QUERY_PARAM_VALUE_PER_PAGE);
        urlBuilder.addQueryParameter(QUERY_PARAM_PAGE, String.valueOf(page));

        String url = urlBuilder.build().toString();

        Request request = new Request.Builder().url(url).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mainUiThread.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailure(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                mainUiThread.post(new Runnable() {
                    @Override
                    public void run() {
                        if (response.isSuccessful()) {
                            Gson gson = new GsonBuilder().create();
                            String responseString = null;
                            try {
                                responseString = response.body().string();
                            } catch (IOException e) {
                                callback.onFailure(e);
                            }
                            com.sagarnileshshah.carouselmvp.data.models.photo.Response payload =
                                    gson.fromJson(responseString,
                                            com.sagarnileshshah.carouselmvp.data.models.photo.Response.class);

                            List<Photo> photos = payload.getPhotos().getPhoto();

                            callback.onSuccess(photos);
                        }
                    }
                });
            }
        });
    }

    @Override
    public void getComments(String photoId, final GetCommentsCallback callback) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_API_URL).newBuilder();
        urlBuilder.addQueryParameter(QUERY_PARAM_API_KEY, API_KEY);
        urlBuilder.addQueryParameter(QUERY_PARAM_METHOD, COMMENTS_ENDPOINT);
        urlBuilder.addQueryParameter(QUERY_PARAM_NO_JSON_CALLBACK, QUERY_PARAM_VALUE_NO_JSON_CALLBACK);
        urlBuilder.addQueryParameter(QUERY_PARAM_FORMAT, QUERY_PARAM_VALUE_JSON);
        urlBuilder.addQueryParameter(QUERY_PARAM_PHOTO_ID, String.valueOf(photoId));

        String url = urlBuilder.build().toString();

        Request request = new Request.Builder().url(url).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mainUiThread.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailure(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                mainUiThread.post(new Runnable() {
                    @Override
                    public void run() {
                        if (response.isSuccessful()) {
                            Gson gson = new GsonBuilder().create();
                            String responseString = null;
                            try {
                                responseString = response.body().string();
                            } catch (IOException e) {
                                callback.onFailure(e);
                            }
                            com.sagarnileshshah.carouselmvp.data.models.comment.Response payload =
                                    gson.fromJson(responseString,
                                            com.sagarnileshshah.carouselmvp.data.models.comment.Response.class);

                            List<Comment> comments = payload.getComments().getComment();

                            callback.onSuccess(comments);
                        }
                    }
                });
            }
        });
    }
}
