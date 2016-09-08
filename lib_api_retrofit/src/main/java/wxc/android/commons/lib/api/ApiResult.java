package wxc.android.commons.lib.api;

import com.google.gson.annotations.SerializedName;

public class ApiResult<T> {

    @SerializedName("code")
    public int mCode;

    @SerializedName("message")
    public String mMessage;

    @SerializedName("subjects")
    public T mData;
}
