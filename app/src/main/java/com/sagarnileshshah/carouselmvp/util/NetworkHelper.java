package com.sagarnileshshah.carouselmvp.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by sshah on 3/25/17.
 */

public class NetworkHelper {

  public static boolean isInternetAvailable(Context context){
    ConnectivityManager connectivityManager =
        (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

    NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
    boolean isConnected = activeNetwork != null &&
        activeNetwork.isConnectedOrConnecting();
    return isConnected;
  }
}
