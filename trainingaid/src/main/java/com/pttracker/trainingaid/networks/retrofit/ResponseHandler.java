package com.pttracker.trainingaid.networks.retrofit;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pttracker.trainingaid.networks.ErrorResponse;


import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * @author Atif Ali
 * @since September 05, 2017 10:32 AM
 */

/**
 * ResponseHandler is middleware to parse retrofit responses
 */
public abstract class ResponseHandler<T> implements retrofit2.Callback<T> {
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.code() / 100 == 2)//series of 200
        {
            onSuccess(response);
        } else {
            if (response.code() == 400) {
                Gson gson = new GsonBuilder().create();
                try {
                    ErrorResponse errorResponse = gson.fromJson(response.errorBody().string(), ErrorResponse.class);
                    onFailure(response.code(), errorResponse.getError().getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                    onFailure(response.code(), e.getMessage());
                }
            } else {
                onFailure(response.code(), response.message());
            }
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        onFailure(400, t.getLocalizedMessage());
    }

    public abstract void onSuccess(Response<T> response);

    public abstract void onFailure(int errorCode, String errorMessage);
}
