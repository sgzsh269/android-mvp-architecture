package com.sagarnileshshah.carouselmvp.data.remote;


import com.sagarnileshshah.carouselmvp.data.models.photo.Response;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * The interface defining methods to fetch data and their respective API
 * endpoints using Retrofit lib.
 */
public interface ApiService {

    @GET("services/rest")
    Call<Response> getPhotos(@QueryMap Map<String, String> queryMap);

    @GET("services/rest")
    Call<com.sagarnileshshah.carouselmvp.data.models.comment.Response> getComments(
            @Query(RemoteDataSource.QUERY_PARAM_PHOTO_ID) String photoId,
            @QueryMap Map<String, String> queryMap);
}
