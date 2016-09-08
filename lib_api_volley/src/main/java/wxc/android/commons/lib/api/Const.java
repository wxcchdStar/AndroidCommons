package wxc.android.commons.lib.api;

/**
 * 常量表
 */
public final class Const {

    // api
    // 发布前需要删除log, 请执行logger.py文件
    private static final boolean API_TEST = false; // TODO: This is import!!!
    public static final int APP_ID = 1;
    public static final String APP_KEY = "1d92ccf0d57c9480bfebf06f52c9e9a5";
    private static final String HOST = "http://api.thekittyplay.com";
    private static final String HOST_SANDBOX = "http://tk.tshenbian.com";
    public static final String HOST_CURRENT = API_TEST ? HOST_SANDBOX : HOST;
}
