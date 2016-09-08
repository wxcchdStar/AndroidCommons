package wxc.android.commons.lib.api;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

public class HandleErrorHelper {

    public static ApiError newApiError(int errorCode, String errorMessage) {
        return new ApiError(errorCode, errorMessage);
    }

    // 不需要错误信息
    public static ApiError handleVolleyError(Context context, VolleyError error) {
        if (error instanceof TimeoutError) {
            return new ApiError(ApiError.ERROR_CONNECTION_TIMEOUT, "");
        } else if (isServerProblem(error)) {
            return new ApiError(ApiError.ERROR_SERVER, "");
        } else if (isNetworkProblem(error)) {
            return new ApiError(ApiError.ERROR_NETWORK, "");
        }
        return new ApiError(ApiError.ERROR_UNKNOWN, "");
    }

    private static boolean isNetworkProblem(VolleyError error) {
        return (error instanceof NoConnectionError) || (error instanceof NetworkError);
    }

    private static boolean isServerProblem(VolleyError error) {
        return (error instanceof ServerError) || (error instanceof AuthFailureError);
    }
}
