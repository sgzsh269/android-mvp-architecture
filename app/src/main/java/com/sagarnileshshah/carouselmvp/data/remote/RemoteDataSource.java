package com.sagarnileshshah.carouselmvp.data.remote;


import com.sagarnileshshah.carouselmvp.BuildConfig;
import com.sagarnileshshah.carouselmvp.data.DataSource;
import com.sagarnileshshah.carouselmvp.util.threading.MainUiThread;
import com.sagarnileshshah.carouselmvp.util.threading.ThreadExecutor;

import java.util.HashMap;
import java.util.Map;

/**
 * The class for fetching data from Flickr API on a background thread and returning data via
 * callbacks on the main UI thread
 */
public class RemoteDataSource extends DataSource {

    public static final String API_KEY = BuildConfig.FLICKR_API_KEY;
    public static final String BASE_API_URL = "https://api.flickr.com";
    public static final String PHOTOS_ENDPOINT = "flickr.interestingness.getList";
    public static final String COMMENTS_ENDPOINT = "flickr.photos.comments.getList";

    public static final String QUERY_PARAM_PHOTO_ID = "photo_id";
    public static final String QUERY_PARAM_API_KEY = "api_key";
    public static final String QUERY_PARAM_METHOD = "method";
    public static final String QUERY_PARAM_NO_JSON_CALLBACK = "nojsoncallback";
    public static final String QUERY_PARAM_FORMAT = "format";
    public static final String QUERY_PARAM_VALUE_JSON = "json";
    public static final String QUERY_PARAM_VALUE_NO_JSON_CALLBACK = "1";
    public static final String QUERY_PARAM_PER_PAGE = "per_page";
    public static final String QUERY_PARAM_VALUE_PER_PAGE = "10";
    public static final String QUERY_PARAM_PAGE = "page";


    private static RemoteDataSource remoteDataSource;

    private ApiService apiService;

    private RemoteDataSource(MainUiThread mainUiThread,
            ThreadExecutor threadExecutor, ApiService apiService) {
        super(mainUiThread, threadExecutor);
        this.apiService = apiService;

    }

    public static synchronized RemoteDataSource getInstance(MainUiThread mainUiThread,
            ThreadExecutor threadExecutor,
            ApiService apiService) {
        if (remoteDataSource == null) {

            remoteDataSource = new RemoteDataSource(mainUiThread, threadExecutor, apiService);
        }
        return remoteDataSource;
    }

    @Override
    public void getPhotos(int page, final GetPhotosCallback callback) {

        Map<String, String> queryMap = new HashMap<>();
        queryMap.put(QUERY_PARAM_METHOD, PHOTOS_ENDPOINT);
        queryMap.put(QUERY_PARAM_PER_PAGE, QUERY_PARAM_VALUE_PER_PAGE);
        queryMap.put(QUERY_PARAM_PAGE, String.valueOf(page));

        retrofit2.Call<com.sagarnileshshah.carouselmvp.data.models.photo.Response> call =
                apiService.getPhotos(queryMap);

        call.enqueue(
                new retrofit2.Callback<com.sagarnileshshah.carouselmvp.data.models.photo
                        .Response>() {
                    @Override
                    public void onResponse(
                            retrofit2.Call<com.sagarnileshshah.carouselmvp.data.models.photo
                                    .Response> call,
                            retrofit2.Response<com.sagarnileshshah.carouselmvp.data.models.photo
                                    .Response> response) {
                        if (response.isSuccessful()) {
                            com.sagarnileshshah.carouselmvp.data.models.photo.Response
                                    photoResponse = response.body();
                            callback.onSuccess(photoResponse.getPhotos().getPhoto());
                        } else {
                            callback.onFailure(new Throwable());
                        }
                    }

                    @Override
                    public void onFailure(
                            retrofit2.Call<com.sagarnileshshah.carouselmvp.data.models.photo
                                    .Response> call,
                            Throwable t) {
                        callback.onFailure(t);
                    }
                });
    }

    @Override
    public void getComments(String photoId, final GetCommentsCallback callback) {

        Map<String, String> queryMap = new HashMap<>();
        queryMap.put(QUERY_PARAM_METHOD, COMMENTS_ENDPOINT);

        retrofit2.Call<com.sagarnileshshah.carouselmvp.data.models.comment.Response> call =
                apiService.getComments(photoId, queryMap);

        call.enqueue(
                new retrofit2.Callback<com.sagarnileshshah.carouselmvp.data.models.comment
                        .Response>() {
                    @Override
                    public void onResponse(
                            retrofit2.Call<com.sagarnileshshah.carouselmvp.data.models.comment
                                    .Response> call,
                            retrofit2.Response<com.sagarnileshshah.carouselmvp.data.models
                                    .comment.Response> response) {
                        if (response.isSuccessful()) {
                            com.sagarnileshshah.carouselmvp.data.models.comment.Response
                                    commentsResponse = response.body();
                            callback.onSuccess(commentsResponse.getComments().getComment());
                        } else {
                            callback.onFailure(new Throwable());
                        }
                    }

                    @Override
                    public void onFailure(
                            retrofit2.Call<com.sagarnileshshah.carouselmvp.data.models.comment
                                    .Response> call,
                            Throwable t) {
                        callback.onFailure(t);
                    }
                });
    }
}
