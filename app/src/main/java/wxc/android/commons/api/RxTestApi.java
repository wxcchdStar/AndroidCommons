package wxc.android.commons.api;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import rx.Subscriber;
import wxc.android.commons.api.model.MovieEntity;
import wxc.android.commons.lib.api.ApiResult;
import wxc.android.commons.lib.rxapi.RxApiClient;

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
