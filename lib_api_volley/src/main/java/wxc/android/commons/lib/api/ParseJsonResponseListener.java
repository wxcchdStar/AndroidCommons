package wxc.android.commons.lib.api;


public class ParseJsonResponseListener {

    public interface ResultListener<R> {
        void onResult(R result);
    }
    
    public interface ApiErrorListener {
        void onError(String tag, ApiError error);
    }

}
