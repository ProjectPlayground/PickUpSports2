package ridgewell.pickupsports2.common;

import android.os.Parcel;
import android.os.Parcelable;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;

/**
 * Created by cameronridgewell on 1/16/15.
 */
public class User implements Parcelable{

    private String _id;
    private String firstname;
    private String lastname;
    private String nickname;
    private Location location;
    private long joiningTime;
    private String fb_id;

    //private List<Sport> favoriteSports = new ArrayList<Sport>();
    private List<String> attendedEvents = new ArrayList<String>();
    //private List<Event> createdEvents = new ArrayList<Event>();
    //private List<Badge> badges = new ArrayList<Badge>();

    public User() {}

    public User(String firstname, String lastname, Location location) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.joiningTime = DateTime.now().getMillis();
        //Fetch from phone or have user input
        this.location = location;
        this.nickname = "";
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<String> getAttendedEvents() {
        return attendedEvents;
    }

    public void setAttendedEvents(List<String> attendedEvents) {
        this.attendedEvents = attendedEvents;
    }

    /*
                 * returns the String nickname
                 */
    public String getNickname() {
        return nickname;
    }

    /*
     * sets the String nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /*
     * returns the user's join Date
     */

    public long getJoiningTime() {
        return joiningTime;
    }

    public void setJoiningTime(String joinTime) {
        this.joiningTime = joiningTime;
    }

    public DateTime getJoinTime() {
        return new DateTime(joiningTime);
    }

    /*
     * returns the user's location
     */
    public Location getLocation() {
        return location;
    }

    /*
     * sets the user's location
     */
    public void setLocation(Location location) {
        this.location = location;
    }
/*
    public List<Sport> getFavoriteSports() {
        return favoriteSports;
    }
*/
    /*
         * if the sport is not already in favorites it is added, else nothing
         */
    /*
    public void addToFavorites(Sport sport) {
        int i = 0;
        for (; i < this.favoriteSports.size()
                && sport.compareTo(this.favoriteSports.get(i)) > 0; i++) {}
        if (sport.compareTo(this.favoriteSports.get(i)) != 0) {
            this.favoriteSports.add(sport);
        }
    }
*/
    /*
     * if the sport is in favorites, it is removed and true is returned, else false
     */
    /*
    public boolean removeFromFavorites(Sport sport) {
        return favoriteSports.remove(sport);
    }
    */
    /*public List<Badge> getBadges() {
        return badges;
    }
    */

    /*
            * returns true if a new badge has been earned
            */
    public boolean refreshBadges() {
        //check full badge list for qualifies and add qualifiers to badges
        return false;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(_id);
        out.writeString(firstname);
        out.writeString(lastname);
        out.writeString(nickname);
        out.writeParcelable(location, 0);
        out.writeLong(joiningTime);
        out.writeString(fb_id);
        //out.writeTypedList(favoriteSports);
        out.writeStringList(attendedEvents);
        //out.writeTypedList(createdEvents);
        //out.writeTypedList(badges);
    }

    public static final Parcelable.Creator<User> CREATOR
            = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    private User(Parcel in) {
        _id = in.readString();
        firstname = in.readString();
        lastname = in.readString();
        nickname = in.readString();
        location = in.readParcelable(Location.class.getClassLoader());
        joiningTime = in.readLong();
        fb_id = in.readString();
        //favoriteSports = in.createTypedArrayList(Sport.CREATOR);
        in.readStringList(attendedEvents);
        //createdEvents = in.createTypedArrayList(Event.CREATOR);
        //badges = in.createTypedArrayList(Badge.CREATOR);
    }
}
