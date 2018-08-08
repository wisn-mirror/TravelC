package com.travel.library.net.Interceptor;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Http 请求公共参数
 */
public class CommonRequestInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        String token="";
        Request requestWithHeader = originalRequest.newBuilder()
//                .header("clientInfo", getValueEncoded(initBaseHeader()))
                .header("Cookie", "ut=" +token)
                .header("ut", token)
                .build();
        return chain.proceed(requestWithHeader);
    }

}
