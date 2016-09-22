package wxc.android.commons.lib.api;

import android.util.Log;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InternalCallback<T> implements Callback<ApiResult<T>> {
    private WeakReference<ApiCallback<T>> mCallbackRef;

    public InternalCallback(ApiCallback<T> callback) {
        mCallbackRef = new WeakReference<>(callback);
    }

    @Override
    public void onResponse(Call<ApiResult<T>> call, Response<ApiResult<T>> response) {
        ApiCallback<T> callback = mCallbackRef.get();
        if (callback != null) {
            if (response.isSuccessful()) { // 请求成功
                ApiResult<T> result = response.body();
                if (result.mCode == ApiClient.sCodeOk) { // 请求数据正确
                    callback.onApiResult(result.mData);
                } else { // 请求数据错误
                    callback.onApiError(new ApiError(result.mCode, result.mMessage));
                }
            } else { // 请求失败
                int code = response.code();
                String message = response.message();
                callback.onApiError(new ApiError(code, message));
            }
        }
    }

    @Override
    public void onFailure(Call<ApiResult<T>> call, Throwable t) {
        Log.w("InternalCallback", "onFailure", t);
        // 网络请求失败, 请检查网络
        ApiCallback<T> callback = mCallbackRef.get();
        if (callback != null) {
            callback.onApiError(new ApiError(ApiError.ERROR_NETWORK, null));
        }
    }

}