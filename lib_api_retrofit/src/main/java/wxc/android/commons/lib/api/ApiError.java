package wxc.android.commons.lib.api;


public class ApiError {
    public static final int ERROR_TIMEOUT = 2001;
    public static final int ERROR_SERVER  = 2002;
    public static final int ERROR_NETWORK = 2003;
    public static final int ERROR_UNKNOWN = 2004;

    public int mErrorCode;
    public String mErrorMsg;
    
    public ApiError(int errorCode, String errorMsg) {
        mErrorCode = errorCode;
        mErrorMsg = errorMsg;
    }
}
