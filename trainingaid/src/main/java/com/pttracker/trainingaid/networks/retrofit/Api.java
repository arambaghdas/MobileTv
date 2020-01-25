package com.pttracker.trainingaid.networks.retrofit;

/**
 * @author Atif Ali
 * @since August 29, 2017 4:59 PM
 */

import android.content.Context;

import com.pttrackershared.utils.Constants;

/**
 * Created by Adnan Ali on 2/27/2017.
 */

public class Api {

    public static ApiService getApiService(Context context) {
        return ApiClient.getClient(Constants.BASE_URL, context).create(ApiService.class);
    }
}