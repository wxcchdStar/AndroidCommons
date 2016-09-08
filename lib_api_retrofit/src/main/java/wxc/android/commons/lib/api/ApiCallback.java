package wxc.android.commons.lib.api;

public interface ApiCallback<T> {
    void onApiResult(T t);

    void onApiError(ApiError error);
}

