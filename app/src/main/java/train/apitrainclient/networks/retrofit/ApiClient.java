package train.apitrainclient.networks.retrofit;

import android.content.Context;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Atif Ali
 * @since August 29, 2017 5:00 PM
 */

public class ApiClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl, Context context){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        TokenInterceptor authInterceptor = new TokenInterceptor(context);
        TokenAuthenticator authAuthenticator = new TokenAuthenticator(context);
        httpClient.networkInterceptors().add(authInterceptor);
        httpClient.networkInterceptors().add(new StethoInterceptor());
        httpClient.authenticator(authAuthenticator);

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit;
    }
}
