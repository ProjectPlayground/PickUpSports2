package pickupsports2.ridgewell.pickupsports2.utilities;

import java.util.List;
import java.util.concurrent.Callable;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

import ridgewell.pickupsports2.common.Event;
import ridgewell.pickupsports2.common.LocationProperties;
import ridgewell.pickupsports2.common.Sport;
import ridgewell.pickupsports2.common.User;

/**
 * Created by cameronridgewell on 2/9/15.
 */
public interface RequestLibrary {

    /*User Commands*/
    @GET("/user/?filter=id")
    public User getUser(@Query("id") String id, @Query("id_type") String id_type);

    @GET("/user/?filter=name")
    public List<User> getUserFromName(@Query("name") String name);

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
    public List<Event> getEventFromName(@Query("name") String name);

    @GET("/event/?filter=dateRange")
    public List<Event> getEventsInDateRange(@Query("date1") long date1,
                                            @Query("date2") long date2);

    @GET("/event/?filter=none")
    public List<Event> getAllEvents();

    @POST("/event/?type=new")
    public void addEvent(@Body Event event, Callback<Event> success);

    @DELETE("/event/")
    public void deleteEvent(@Query("id") String id, @Query("attendees") String attendees,
                            Callback<Event> success);

    @POST("/event/?type=existing")
    public void editEvent(@Body Event event, Callback<Event> success);

    /*Sport Commands*/
    @GET("/sport/")
    public Sport getSport(@Query("sport") String sport);

    @POST("/sport/")
    public void addSport(@Body Sport sport, Callback<Sport> success);

    @DELETE("/sport/")
    public void deleteSport(@Query("sportName") String sportName, Callback<Sport> success);

    /*Location Commands*/
    @GET("/location/")
    public LocationProperties getLocation(@Query("locationame") String location);

    @POST("/location/")
    public void addLocation(@Body LocationProperties locationProperties, Callback<LocationProperties> success);

    @DELETE("/location/")
    public void deleteLocation(@Query("location") String Location, Callback<LocationProperties> success);

    /* Action commands */
    @POST("/attendance/?type=add")
    public void attendEvent(@Query("event_id") String event_id,
                            @Query("user_id") String user_id, Callback<String> success);

    @POST("/attendance/?type=remove")
    public void leaveEvent(@Query("event_id") String event_id,
                            @Query("user_id") String user_id, Callback<String> success);

    @POST("/invitation/")
    public void invite(@Query("invitee_id") String invitee_id, @Query("event_id") String event_id,
                       @Query("inviter_id") String inviter_id, Callback<String> success);
}
