package pickupsports2.ridgewell.pickupsports2;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.PUT;
import retrofit.http.Query;

import ridgewell.pickupsports2.common.User;

/**
 * Created by cameronridgewell on 2/9/15.
 */
public interface RequestLibrary {

    @GET("/?cmd=get&type=user")
    public Fooey getUser(@Query("username") String username);

    @PUT("/?cmd=add&type=user")
    public Void addUser(@Body User user);
}
