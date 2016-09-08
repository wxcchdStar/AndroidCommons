package wxc.android.commons.api;

import android.app.Activity;
import android.app.ProgressDialog;

import wxc.android.commons.lib.api.ApiCallback;

public abstract class ProgressApiCallback<T> implements ApiCallback<T> {

    private Activity mActivity;
    private String mMessage;
    private ProgressDialog mProgressDialog;

    public ProgressApiCallback(Activity activity, int resId) {
        this(activity, activity.getString(resId));
    }

    public ProgressApiCallback(Activity activity, String message) {
        mActivity = activity;
        mMessage = message;
    }

    public void showDialog() {
//        mProgressDialog = ProgressDialogUtils.show(mActivity, mProgressDialog, mMessage);
    }
//
//    @Override
//    public void onApiResult(T t) {
//        ProgressDialogUtils.dismiss(mProgressDialog);
//    }
//
//    @Override
//    public void onApiError(ApiError error) {
//        ProgressDialogUtils.dismiss(mProgressDialog);
//        ApiUtils.toastError(mActivity, error);
//    }
}

