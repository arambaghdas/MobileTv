package com.pttracker.trainingaid.networks.retrofit;

import android.content.Context;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

/**
 * @author Atif Ali
 * @since August 29, 2017 5:01 PM
 * TokenAuthenticator manages unauthorized server response
 */

public class TokenAuthenticator implements Authenticator {

    private Context context;

    public TokenAuthenticator(Context context) {
        this.context = context;
    }

    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        return null;
    }
}
