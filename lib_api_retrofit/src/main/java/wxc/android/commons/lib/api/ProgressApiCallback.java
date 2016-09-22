package wxc.android.commons.lib.api;

import android.app.Activity;

public abstract class ProgressApiCallback<T> implements ApiCallback<T> {

    private Activity mActivity;
    private String mMessage;

    public ProgressApiCallback(Activity activity, int resId) {
        this(activity, activity.getString(resId));
    }

    public ProgressApiCallback(Activity activity, String message) {
        mActivity = activity;
        mMessage = message;
    }

    public abstract void showDialog();

    public abstract void dismissDialog();

    @Override
    public void onApiResult(T t) {
        dismissDialog();
    }

    @Override
    public void onApiError(ApiError error) {
        dismissDialog();
    }
}

