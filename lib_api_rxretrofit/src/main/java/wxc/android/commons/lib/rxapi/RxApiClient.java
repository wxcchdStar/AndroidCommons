package wxc.android.commons.lib.rxapi;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import wxc.android.commons.lib.api.*;

public class RxApiClient {

    private static volatile RxApiClient sInstance;

    private static String sBaseUrl;

    private Retrofit mRetrofit;

    private RxApiClient() {
        if (sBaseUrl == null) {
            throw new IllegalArgumentException("The sBaseUrl is NULL!");
        }

        mRetrofit = createBuilder().build();
    }

    public static RxApiClient getInstance() {
        if (sInstance == null) {
            synchronized (RxApiClient.class) {
                if (sInstance == null) {
                    sInstance = new RxApiClient();
                }
            }
        }
        return sInstance;
    }

    private Retrofit.Builder createBuilder() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (wxc.android.commons.lib.api.BuildConfig.DEBUG) {
            HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
            logger.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logger);
        }
        builder.retryOnConnectionFailure(true);
        builder.connectTimeout(15, TimeUnit.SECONDS); // 默认10s

        return new Retrofit.Builder()
                .baseUrl(sBaseUrl)
                .client(builder.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());
    }

    public <T> T createApi(Class<T> clazz) {
        return mRetrofit.create(clazz);
    }

    public static <T> T getApi(Class<T> apiClass) {
        return getInstance().createApi(apiClass);
    }

    public static void setBaseUrl(String url) {
        sBaseUrl = url;
    }

    public static <T> void toSubscribe(Observable<ApiResult<T>> o, Subscriber<T> s) {
        o.map(new ApiResultFilter<T>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    private static class ApiResultFilter<T> implements Func1<ApiResult<T>, T> {

        @Override
        public T call(ApiResult<T> apiResult) {
            if (apiResult.mCode != ApiClient.sCodeOk) {
                throw new RxApiException(apiResult.mCode, apiResult.mMessage);
            }
            return apiResult.mData;
        }
    }
}
