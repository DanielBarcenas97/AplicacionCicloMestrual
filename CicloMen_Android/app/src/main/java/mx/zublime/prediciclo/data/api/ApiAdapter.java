package mx.zublime.prediciclo.data.api;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import mx.zublime.prediciclo.Prediciclo;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiAdapter
{
    private static WebServices API_SERVICE;
    private static final String USERNAME_AUTENTICATION = "ck_5ecb44828673b02bbfaa886ad152e43f923c81f1";
    private static final String PASSWORD_AUTENTICATION = "cs_e3fb7fad4e633978005d39099d89c8118803e187";

    public static WebServices getApiService()
    {

        // Creating the interceptor, and setting the log level
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.level(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add logging as last interceptor
        httpClient.connectTimeout(45, TimeUnit.SECONDS);
        httpClient.readTimeout(45, TimeUnit.SECONDS);
        httpClient.writeTimeout(45, TimeUnit.SECONDS);
        httpClient.addInterceptor(logging);  // <-- this is the important line!
        httpClient.addInterceptor(new BasicAuthInterceptor());
        if (API_SERVICE == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Prediciclo.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    // <-- using the log level
                    .build();
            API_SERVICE = retrofit.create(WebServices.class);
        }

        return API_SERVICE;
    }

    public static class BasicAuthInterceptor implements Interceptor {

        private String credentials;

        public BasicAuthInterceptor() {
            this.credentials = Credentials.basic(USERNAME_AUTENTICATION, PASSWORD_AUTENTICATION);
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Request authenticatedRequest = request.newBuilder()
                    .header("Authorization", credentials).build();
            return chain.proceed(authenticatedRequest);
        }

    }
}
