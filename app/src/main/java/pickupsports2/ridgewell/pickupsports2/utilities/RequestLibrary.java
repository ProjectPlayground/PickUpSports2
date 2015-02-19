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

    /*User Commands*/
    @GET("/user/")
    public User getUser(@Query("username") String username);

    @POST("/user/")
    public void addUser(@Body User user, Callback<User> success);

    /*Event Commands*/
    @GET("/event/?filter=name")
    public Event getEvent(@Query("name") String name);

    @GET("/event/?filter=none")
    public List<Event> getAllEvents();

    @POST("/event/")
    public void addEvent(@Body Event event, Callback<Event> success);

    @DELETE("/event/")
    public void deleteEvent(@Query("name") String name, Callback<Event> success);
}
