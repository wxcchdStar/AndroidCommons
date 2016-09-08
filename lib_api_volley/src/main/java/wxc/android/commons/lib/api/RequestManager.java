package wxc.android.commons.lib.api;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;

public final class RequestManager {
    public static final String REQ_DEFAULT_TAG = VolleyLog.TAG;
    private static volatile RequestManager sInstance;

    private RequestQueue mRequestQueue;

    public static RequestManager getInstance(Context ctx) {
        if (sInstance == null) {
            synchronized (RequestManager.class) {
                if (sInstance == null) {
                    sInstance = new RequestManager(ctx);
                }
            }
        }
        return sInstance;
    }

    private RequestManager(Context ctx) {
        mRequestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? REQ_DEFAULT_TAG : tag);
        mRequestQueue.add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (tag != null && mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
