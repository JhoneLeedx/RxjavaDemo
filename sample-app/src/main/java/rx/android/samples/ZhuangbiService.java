package rx.android.samples;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import rx.Observer;

/**
 * Created by xin.deng on 2016/6/23.
 */
public interface ZhuangbiService {

    @GET("search")
    Observable<List<ZhuangbiImage>> getSearch(@Query("q") String query);
}
