package com.sagarnileshshah.carouselmvp.di;


import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.sagarnileshshah.carouselmvp.data.DataRepository;
import com.sagarnileshshah.carouselmvp.data.local.LocalDataSource;
import com.sagarnileshshah.carouselmvp.data.remote.ApiService;
import com.sagarnileshshah.carouselmvp.data.remote.RemoteDataSource;
import com.sagarnileshshah.carouselmvp.util.NetworkHelper;
import com.sagarnileshshah.carouselmvp.util.threading.MainUiThread;
import com.sagarnileshshah.carouselmvp.util.threading.ThreadExecutor;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.sagarnileshshah.carouselmvp.data.remote.RemoteDataSource.API_KEY;
import static com.sagarnileshshah.carouselmvp.data.remote.RemoteDataSource.BASE_API_URL;
import static com.sagarnileshshah.carouselmvp.data.remote.RemoteDataSource.QUERY_PARAM_API_KEY;
import static com.sagarnileshshah.carouselmvp.data.remote.RemoteDataSource.QUERY_PARAM_FORMAT;
import static com.sagarnileshshah.carouselmvp.data.remote.RemoteDataSource
        .QUERY_PARAM_NO_JSON_CALLBACK;
import static com.sagarnileshshah.carouselmvp.data.remote.RemoteDataSource.QUERY_PARAM_VALUE_JSON;
import static com.sagarnileshshah.carouselmvp.data.remote.RemoteDataSource
        .QUERY_PARAM_VALUE_NO_JSON_CALLBACK;

public class Injection {

    public static DataRepository provideDataRepository(MainUiThread mainUiThread,
            ThreadExecutor threadExecutor, DatabaseDefinition databaseDefinition) {


        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                HttpUrl httpUrl = chain.request().url().newBuilder()
                        .addQueryParameter(QUERY_PARAM_API_KEY, API_KEY)
                        .addQueryParameter(QUERY_PARAM_NO_JSON_CALLBACK,
                                QUERY_PARAM_VALUE_NO_JSON_CALLBACK)
                        .addQueryParameter(QUERY_PARAM_FORMAT, QUERY_PARAM_VALUE_JSON)
                        .build();
                Request newRequest = chain.request().newBuilder().url(httpUrl).build();
                return chain.proceed(newRequest);
            }
        };

        OkHttpClient okHttpClient =
                new OkHttpClient.Builder()
                        .addNetworkInterceptor(new StethoInterceptor())
                        .addInterceptor(interceptor)
                        .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        return DataRepository.getInstance(
                RemoteDataSource.getInstance(mainUiThread, threadExecutor, apiService),
                LocalDataSource.getInstance(mainUiThread, threadExecutor, databaseDefinition),
                NetworkHelper.getInstance());
    }
}
