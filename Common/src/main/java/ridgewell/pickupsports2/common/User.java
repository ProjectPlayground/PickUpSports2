package ridgewell.pickupsports2.common;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by cameronridgewell on 1/16/15.
 */
public class User implements Parcelable{

    private String username;
    private String nickname;
    private Location location;
    private Date joinTime;

    private List<Sport> favoriteSports = new ArrayList<Sport>();
    private List<Event> attendedEvents = new ArrayList<Event>();
    private List<Event> createdEvents = new ArrayList<Event>();
    private List<Badge> badges = new ArrayList<Badge>();

    public User(String username) {
        this.username = username;
        this.joinTime = new Date();
        //Fetch from phone or have user input
        this.location = new Location("fetch current position");
        this.nickname = "";
    }

    /*
     * returns the String username
     */
    public String getUsername() {
        return username;
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
    public Date getJoinTime() {
        return joinTime;
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

    public List<Sport> getFavoriteSports() {
        return favoriteSports;
    }

    /*
         * if the sport is not already in favorites it is added, else nothing
         */
    public void addToFavorites(Sport sport) {
        int i = 0;
        for (; i < this.favoriteSports.size()
                && sport.compareTo(this.favoriteSports.get(i)) > 0; i++) {}
        if (sport.compareTo(this.favoriteSports.get(i)) != 0) {
            this.favoriteSports.add(sport);
        }
    }

    /*
     * if the sport is in favorites, it is removed and true is returned, else false
     */
    public boolean removeFromFavorites(Sport sport) {
        return favoriteSports.remove(sport);
    }

    public List<Badge> getBadges() {
        return badges;
    }

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
        out.writeString(username);
        out.writeString(nickname);
        out.writeParcelable(location, 0);
        out.writeLong(joinTime.getTime());
        out.writeTypedList(favoriteSports);
        out.writeTypedList(attendedEvents);
        out.writeTypedList(createdEvents);
        out.writeTypedList(badges);
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
        username = in.readString();
        nickname = in.readString();
        location = in.readParcelable(Location.class.getClassLoader());
        joinTime = new Date(in.readLong());
        favoriteSports = in.createTypedArrayList(Sport.CREATOR);
        attendedEvents = in.createTypedArrayList(Event.CREATOR);
        createdEvents = in.createTypedArrayList(Event.CREATOR);
        badges = in.createTypedArrayList(Badge.CREATOR);
    }
}
