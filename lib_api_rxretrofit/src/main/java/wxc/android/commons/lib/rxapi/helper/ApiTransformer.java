package wxc.android.commons.lib.rxapi.helper;

import android.text.TextUtils;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import wxc.android.commons.lib.rxapi.ApiResult;

public class ApiTransformer<T> implements Observable.Transformer<ApiResult<T>, T> {

    @SuppressWarnings("unchecked")
    @Override
    public Observable<T> call(Observable<ApiResult<T>> observable) {
        return observable
                .map(new Func1<ApiResult<T>, T>() {
                    @Override
                    public T call(ApiResult<T> apiResult) {
                        if (apiResult.isOk() || apiResult.isIgnore()) {
                            return apiResult.mData;
                        }
                        if (TextUtils.isEmpty(apiResult.mMessage) && apiResult.mData instanceof String) {
                            apiResult.mMessage = (String) apiResult.mData;
                        }
                        throw new ApiResultException(apiResult.mCode, apiResult.mMessage);
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(new Func1<Throwable, T>() {
                    @Override
                    public T call(Throwable throwable) {
//                        L.e(throwable);

                        if (throwable instanceof Exception) {
                            throw new RuntimeException(throwable);
                        }
                        return null;
                    }
                });
    }
}
