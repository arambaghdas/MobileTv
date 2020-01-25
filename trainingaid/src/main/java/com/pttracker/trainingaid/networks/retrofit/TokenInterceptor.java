package com.pttracker.trainingaid.networks.retrofit;

import android.content.Context;


import com.pttracker.trainingaid.utils.SaveUserPreferences;
import com.pttracker.trainingaid.utils.UserPrefManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Atif Ali
 * @since August 29, 2017 5:03 PM
 * TokenInterceptor appends auth token to all server calls
 */
public class TokenInterceptor implements Interceptor{

    private Context context;
    public TokenInterceptor(Context context){
        this.context = context;
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        UserPrefManager userPrefManager = new UserPrefManager(context);
        String accessToken = "Bearer "+ userPrefManager.getAccessToken();

        Request.Builder builder = originalRequest.newBuilder().header("x-api-key", accessToken).
                method(originalRequest.method(), originalRequest.body());
        return chain.proceed(builder.build());
    }
}