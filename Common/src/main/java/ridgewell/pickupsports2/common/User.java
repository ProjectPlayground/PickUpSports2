package ridgewell.pickupsports2.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by cameronridgewell on 1/16/15.
 */
public class User {

    private String username;
    private String nickname;
    private Location location;
    private Date joinTime;

    private List<Sport> favoriteSports = new ArrayList<Sport>();
    private List<Event> attendedEvents = new ArrayList<Event>();
    private List<Event> createdEvents = new ArrayList<Event>();
    private List<Badge> badges = new ArrayList<Badge>();

    public User(String username_) {
        username = username_;
        joinTime = new Date();
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
    public void setNickname(String nickname_) {
        nickname = nickname_;
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
    public void setLocation(String location_) {
        location = new Location(location_);
    }

    /*
     * if the sport is not already in favorites it is added, else nothing
     */
    public void addToFavorites(Sport sport) {
        int i = 0;
        for (; i < favoriteSports.size() && sport.compareTo(favoriteSports.get(i)) > 0; i++) {}
        if (sport.compareTo(favoriteSports.get(i)) != 0) {
            favoriteSports.add(sport);
        }
    }

    /*
     * if the sport is in favorites, it is removed and true is returned, else false
     */
    public boolean removeFromFavorites(Sport sport) {
        return favoriteSports.remove(sport);
    }

    /*
    * returns true if a new badge has been earned
    */
    public boolean refreshBadges() {
        //check full badge list for qualifies and add qualifiers to badges
        return false;
    }
}
