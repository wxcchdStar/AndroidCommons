package wxc.android.commons.lib.rxapi;

import com.google.gson.annotations.SerializedName;

public class ApiResult<T> {
    public static final String CODE_OK = "200";
    public static final String CODE_IGNORE = "99999";

    @SerializedName("code")
    public String mCode;

    @SerializedName("message")
    public String mMessage;

    @SerializedName("data")
    public T mData;

    public boolean isOk() {
        return CODE_OK.equals(mCode);
    }

    public boolean isIgnore() {
        return CODE_IGNORE.equalsIgnoreCase(mCode);
    }
}
