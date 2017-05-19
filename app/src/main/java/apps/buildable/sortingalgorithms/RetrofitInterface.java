package apps.buildable.sortingalgorithms;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by SamerGigaByte on 5/19/2017.
 */

public interface RetrofitInterface {
        @POST("{SortAlgorithm}")
        Call<List<String>> sort(@Path("SortAlgorithm")String algorithm,@Body String[] names);
}
