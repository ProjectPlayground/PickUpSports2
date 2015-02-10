package pickupsports2.ridgewell.pickupsports2;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

import ridgewell.pickupsports2.common.User;

/**
 * Created by cameronridgewell on 2/9/15.
 */
public interface RequestLibrary {

    @GET("/?cmd=get&type=user")
    public Fooey getUser(@Query("username") String username);

    @POST("/?cmd=add&type=user")
    public void addUser(@Body Fooey user, Callback<Fooey> success);
}
