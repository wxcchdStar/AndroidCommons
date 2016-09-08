package wxc.android.commons.lib.api;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

public class StringPostRequest extends StringRequest {
    private Map<String, String> mParams;

    public StringPostRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

    public StringPostRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        if (mParams == null) {
            throw new IllegalArgumentException("mParams can't be NULL!");
        }
        return mParams;
    }

    public void setParams(Map<String, String> params) {
        mParams = params;
    }
}
