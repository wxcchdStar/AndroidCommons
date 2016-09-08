package wxc.android.commons.lib.rxapi;

import retrofit2.http.GET;
import retrofit2.http.Query;

public class RxTestApi {

    public static void request(Subscriber<List<MovieEntity>> subscriber) {
        RxApiClient.toSubscribe(
                RxApiClient.get(Api.class).getTopMovie(0, 1),
                subscriber);

        // 还可以取消
        subscriber.unsubscribe();
    }

    private interface Api {

        @GET("top250")
        Observable<ApiResult<List<MovieEntity>>> getTopMovie(@Query("start") int start, @Query("count") int count);
    }
}