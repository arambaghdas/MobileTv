package train.apitrainclient.networks.retrofit;

import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import train.apitrainclient.utils.UserPrefManager;

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
        UserPrefManager userPrefManager = new UserPrefManager(context);
        Request originalRequest = chain.request();
        String accessToken = "Bearer "+ userPrefManager.getAccessToken();
        Request.Builder builder = originalRequest.newBuilder().header("x-api-key", accessToken).
                method(originalRequest.method(), originalRequest.body());
        return chain.proceed(builder.build());
    }
}