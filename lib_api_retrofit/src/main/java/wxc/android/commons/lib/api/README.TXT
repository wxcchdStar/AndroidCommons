####################################################################################################
# 方案一
####################################################################################################
public class ApiTemplate {
    /**
     * The same as method name of {@link Api}
     */
    public static final String ACTION_TEST = "test";
    public static final String ACTION_UPLOAD = "upload";

    /**
     * @param action   method name
     * @param callback request callback
     * @param args     request params
     * @param <T>      response data type
     */
    @SuppressWarnings("unchecked")
    public static <T> void request(String action, ApiCallback<T> callback, Object... args) {
        if (callback instanceof ProgressApiCallback) {
            ((ProgressApiCallback) callback).showDialog();
        }
        Api api = ApiClient.getInstance().createApi(Api.class);
        Method[] methods = api.getClass().getDeclaredMethods();
        if (methods != null) {
            for (Method method : methods) {
                if (method.getName().equals(action)) {
                    try {
                        Call<ApiResult<T>> call;
                        Object[] newArgs = null;
                        if (args != null) {
                            newArgs = new Object[args.length];
                            for (int i = 0; i < args.length; i++) {
                                if (args[i] instanceof ApiParams) {
                                    newArgs[i] = ConvertUtils.convertToMap((ApiParams) args[i]);
                                } else if (args[1] instanceof File) {
                                    File file = (File) args[1];
                                    RequestBody requestBody = RequestBody.create(MediaType.parse("application/otcet-stream"), file);
                                    args[i] = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
                                } else {
                                    newArgs[i] = args[i];
                                }
                            }
                        }
                        call = (Call<ApiResult<T>>) method.invoke(api, newArgs);
                        call.enqueue(new InternalCallback<>(callback));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * request params, it will be converted to Map
     */
    public static class TestParams implements ApiParams {
        public String mParam1;
        public int mParam2;
        public String mParam3;
    }

    private interface Api {

        @POST("api/test")
        Call<ApiResult<Void>> test(@Query("token") String token, @FieldMap Map<String, Object> map);

        @POST("api/upload")
        @Multipart
        Call<ApiResult<Void>> upload(@Part MultipartBody.Part file);
    }
}

####################################################################################################
# 方案二
####################################################################################################
public class ApiTemplate {

    /**
     * request params, it will be converted to Map
     */
    public static class TestParams implements ApiParams {
        public String mParam1;
        public int mParam2;
        public String mParam3;
    }

    public interface InternalApi {

        @POST("api/test")
        Call<ApiResult<Void>> test(@Query("token") String token, @FieldMap Map<String, Object> map);

        @POST("api/upload")
        @Multipart
        Call<ApiResult<Void>> upload(@Part MultipartBody.Part file);
    }
}

Call<ApiResult<Void>> call = ApiClient.getApi(InternalApi.class)
                .test("token", ConvertUtils.convertToMap(new TestParams()));
ApiClient.execute(call, callback);

####################################################################################################
Input: API name, request type, request params, response data
Output: Java file
