package wxc.android.commons.lib.api;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;

import org.apache.http.impl.auth.UnsupportedDigestAlgorithmException;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public abstract class BaseApiRequest<P, R> implements Listener<String>, ErrorListener {
    public static final String TAG = "ApiRequest";
    public static final int TIME_OUT = 30 * 1000;

    public static String sAppVersion;

    protected WeakReference<ParseJsonResponseListener.ResultListener<R>> mListenerRef;
    protected WeakReference<ParseJsonResponseListener.ApiErrorListener> mErrorListenerRef;

    protected Context mContext;
    protected StringPostRequest mRequest;

    protected P mParams;

    protected boolean mIsSig = false;
    protected String mApiName = "";

    protected String mTag;
    protected String mSession = "";

    public BaseApiRequest(Context ctx) {
        this(ctx, null, null);
    }

    public BaseApiRequest(Context ctx, ParseJsonResponseListener.ResultListener<R> listener,
                          ParseJsonResponseListener.ApiErrorListener errorListener) {
        init(ctx, Const.HOST_CURRENT, listener, errorListener);
    }

    private void init(Context ctx, String host, ParseJsonResponseListener.ResultListener<R> listener,
                      ParseJsonResponseListener.ApiErrorListener errorListener) {
        mContext = ctx.getApplicationContext();
        mRequest = new StringPostRequest(Method.POST, host, this, this);
        mRequest.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mListenerRef = new WeakReference<ParseJsonResponseListener.ResultListener<R>>(listener);
        mErrorListenerRef = new WeakReference<ParseJsonResponseListener.ApiErrorListener>(errorListener);
//        mSession = AccountManager.getInstance().getSession(ctx);
        mSession = String.valueOf(System.currentTimeMillis());

        // init api mName and tag
        initApiNameAndTag();
        if (TextUtils.isEmpty(mApiName)) {
            throw new IllegalArgumentException("mApiName can't be empty!");
        }
        if (TextUtils.isEmpty(mTag)) {
            mTag = mApiName;
        }

        // init version
        initAppVersion(ctx);
    }

    static void initAppVersion(Context context) {
        if (TextUtils.isEmpty(sAppVersion) || "9.99".equals(sAppVersion)) {
            sAppVersion = getAppVersion(context);
        }
    }

    protected abstract void initApiNameAndTag();

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.w(TAG, mApiName + " error is >>>>>" + error);
        handleErrorResponse(HandleErrorHelper.handleVolleyError(mContext, error));
    }

    @Override
    public void onResponse(String response) {
        Log.d(TAG, mApiName + " response is >>>>>" + response);
        try {
            JSONObject jsonResponse = new JSONObject(response);
            int code = jsonResponse.optInt("code", -1);
            if (code == 0) {
                handleCorrectResponse(jsonResponse);
            } else {
                String errorMessage = jsonResponse.optString("msg");
                if (TextUtils.isEmpty(errorMessage)) {
//                    errorMessage = mActivity.getResources().getString(R.string.error_domain_id);
                }
                handleErrorResponse(HandleErrorHelper.newApiError(code, errorMessage));
            }
        } catch (JSONException e) {
//            handleErrorResponse(HandleErrorHelper.newApiError(2005,
//                    mActivity.getString(R.string.error_json_exception)));
            e.printStackTrace();
        }
    }

    protected void handleCorrectResponse(JSONObject jsonResponse) {
        R result = parseJsonToResult(jsonResponse);
        ParseJsonResponseListener.ResultListener<R> listener = mListenerRef.get();
        Log.d(TAG, "request api handle response: " + mApiName + ", " + (listener != null));
        if (listener != null) {
            listener.onResult(result);
        }
    }

    protected abstract void fillRequestParams(JSONObject jsonParams) throws JSONException;

    protected abstract R parseJsonToResult(JSONObject jsonResponse);

    protected void handleErrorResponse(ApiError error) {
        Log.d(TAG, "request api handle error: " + mApiName + ", " + (mErrorListenerRef.get() != null));
        if (mErrorListenerRef.get() != null) {
            mErrorListenerRef.get().onError(mTag, error);
        }
    }

    public String getRequestTag() {
        Object tag = mRequest.getTag();
        return tag == null ? null : String.valueOf(tag);
    }

    public void execute() {
        Log.d(TAG, "request api >>> " + mApiName);
        mRequest.setParams(getParams());
        RequestManager.getInstance(mContext).addToRequestQueue(mRequest, mTag);
    }

    public BaseApiRequest<P, R> setRequestParams(P params) {
        mParams = params;
        return this;
    }

    protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("app_version", sAppVersion);
            jsonParams.put("app_id", Const.APP_ID);
            jsonParams.put("location", getLocationReqParam());
            fillRequestParams(jsonParams);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        params.put("request", mSession);
        params.put("action", mApiName);
        params.put("param", jsonParams.toString());
        if (mIsSig) {
            try {
                params.put("sig", generateSig(mSession, jsonParams));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, mApiName + " params is " + params.toString());
        return params;
    }

    public static String generateSig(String session, JSONObject params) throws NoSuchAlgorithmException {
        StringBuilder builder = new StringBuilder();
        builder.append(Const.APP_KEY);
        builder.append(session.trim());
        builder.append(params.toString().trim());
        return md5Encode(builder.toString());
    }

    public static String md5Encode(String source) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new UnsupportedDigestAlgorithmException("MD5", e);
        }

        byte[] bytes = source.getBytes();
        byte[] encodedBytes = digest.digest(bytes);

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < encodedBytes.length; i++) {
            int val = ((int) encodedBytes[i]) & 0xff;
            if (val < 16) {
                builder.append("0");
            }
            builder.append(Integer.toHexString(val));
        }
        return builder.toString();
    }

    public static String getLocationReqParam() {
        StringBuilder result = new StringBuilder();
        Locale curLocale = Locale.getDefault();
        result.append(curLocale.getCountry().toLowerCase(Locale.ENGLISH));
        result.append("_");
        result.append(curLocale.getLanguage().toLowerCase(Locale.ENGLISH));
        return result.toString();
    }

    public static String getAppVersion(Context context) {
        String appVersion = null;
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            appVersion = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(appVersion)) {
            appVersion = "9.99";
        }
        return appVersion;
    }
}
