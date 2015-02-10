package pickupsports2.ridgewell.pickupsports2;

import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;
import ridgewell.pickupsports2.common.Event;
import ridgewell.pickupsports2.common.User;

/**
 * Created by cameronridgewell on 2/9/15.
 */
public interface RequestLibrary {

    @GET("/?cmd=get&type=user")
    public User getUser(@Query("username") String username);

    @POST("/?cmd=add&type=user")
    public Void addUser(@Query("user") User user);
}
