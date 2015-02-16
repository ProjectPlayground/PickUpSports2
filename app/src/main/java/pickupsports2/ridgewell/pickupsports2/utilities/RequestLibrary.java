package pickupsports2.ridgewell.pickupsports2.utilities;

import java.util.ArrayList;
import java.util.List;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
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
    public void addUser(@Body User user, Callback<User> success);

    @GET("/?cmd=get&type=event&filter=name")
    public Event getEvent(@Query("name") String name);

    @GET("/?cmd=get&type=event&filter=none")
    public List<Event> getAllEvents();

    @POST("/?cmd=add&type=event")
    public void addEvent(@Body Event event, Callback<Event> success);

    @DELETE("/?cmd=delete&type=event")
    public void deleteEvent(@Query("name") String name, Callback<Event> success);
}
