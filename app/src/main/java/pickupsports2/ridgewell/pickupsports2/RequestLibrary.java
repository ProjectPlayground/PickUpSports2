package pickupsports2.ridgewell.pickupsports2;

import retrofit.http.GET;
import retrofit.http.Query;
import ridgewell.pickupsports2.common.Event;

/**
 * Created by cameronridgewell on 2/9/15.
 */
public interface RequestLibrary {

    @GET("/")
    public Event foo(@Query("bar") String foo_param);
}
