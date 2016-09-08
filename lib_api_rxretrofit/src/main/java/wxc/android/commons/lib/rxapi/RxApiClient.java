package wxc.android.commons.lib.rxapi;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import wxc.android.commons.lib.api.ApiClient;
import wxc.android.commons.lib.api.ApiResult;

public class RxApiClient extends ApiClient {

    private static volatile RxApiClient sInstance;

    private RxApiClient() {
        super();
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

    @Override
    protected Retrofit.Builder createBuilder() {
        Retrofit.Builder builder = super.createBuilder();
        builder.addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        return builder;
    }

    public static <T> T get(Class<T> clazz) {
        return getInstance().createApi(clazz);
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
