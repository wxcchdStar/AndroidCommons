package wxc.android.commons.lib.rxapi;

public class RxApiException extends RuntimeException {

    public int mCode;
    public String mMessage;

    public RxApiException(int code, String message) {
        mCode = code;
        mMessage = message;
    }
}
