package wxc.android.commons.lib.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static int sCodeOk = 200;
    protected static String sBaseUrl;

    private static volatile ApiClient sInstance;

    protected Retrofit mRetrofit;

    protected ApiClient() {
        if (sBaseUrl == null) {
            throw new IllegalArgumentException("The sBaseUrl is NULL!");
        }

        mRetrofit = createBuilder().build();
    }

    public static ApiClient getInstance() {
        if (sInstance == null) {
            synchronized (ApiClient.class) {
                if (sInstance == null) {
                    sInstance = new ApiClient();
                }
            }
        }
        return sInstance;
    }

    protected Retrofit.Builder createBuilder() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
            logger.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logger);
        }
        builder.retryOnConnectionFailure(true);
        builder.connectTimeout(15, TimeUnit.SECONDS); // 默认10s

        return new Retrofit.Builder()
                .baseUrl(sBaseUrl)
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create());
    }

    public static void setBaseUrl(String url) {
        sBaseUrl = url;
    }

    public <T> T createApi(Class<T> clazz) {
        return mRetrofit.create(clazz);
    }

}
