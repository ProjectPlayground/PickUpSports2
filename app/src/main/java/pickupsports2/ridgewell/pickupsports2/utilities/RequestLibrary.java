package pickupsports2.ridgewell.pickupsports2.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

import ridgewell.pickupsports2.common.Event;
import ridgewell.pickupsports2.common.Location;
import ridgewell.pickupsports2.common.Sport;
import ridgewell.pickupsports2.common.User;

/**
 * Created by cameronridgewell on 2/9/15.
 */
public interface RequestLibrary {

    /*User Commands*/
    @GET("/user/")
    public User getUser(@Query("id") String id, @Query("id_type") String id_type);

    @POST("/user/?type=new")
    public void addUser(@Body User user, Callback<User> success);

    @POST("/user/?type=existing")
    public void editUser(@Body User user, Callback<User> success);

    @DELETE("/user/")
    public void deleteUser(@Query("username") String username, Callback<User> success);

    /*Event Commands*/
    @GET("/event/?filter=id")
    public Event getEvent(@Query("id") String id);

    @GET("/event/?filter=name")
    public Event getEventFromName(@Query("name") String name);

    @GET("/event/?filter=dateRange")
    public List<Event> getEventsInDateRange(@Query("date1") long date1,
                                            @Query("date2") long date2);

    @GET("/event/?filter=none")
    public List<Event> getAllEvents();

    @POST("/event/")
    public void addEvent(@Body Event event, Callback<Event> success);

    @DELETE("/event/")
    public void deleteEvent(@Query("id") String id, Callback<Event> success);

    /*Sport Commands*/
    @GET("/sport/")
    public Sport getSport(@Query("sport") String sport);

    @POST("/sport/")
    public void addSport(@Body Sport sport, Callback<Sport> success);

    @DELETE("/sport/")
    public void deleteSport(@Query("sportName") String sportName, Callback<Sport> success);

    /*Location Commands*/
    @GET("/location/")
    public Location getLocation(@Query("locationame") String location);

    @POST("/location/")
    public void addLocation(@Body Location location, Callback<Location> success);

    @DELETE("/location/")
    public void deleteLocation(@Query("location") String Location, Callback<Location> success);
}
