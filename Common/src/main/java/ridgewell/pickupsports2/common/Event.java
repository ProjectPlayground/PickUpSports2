package ridgewell.pickupsports2.common;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by cameronridgewell on 1/16/15.
 */
public class Event {
    private String name;
    private Sport sport;
    private Date time;
    private Location location;
    private int cost;
    private String notes;
    private boolean isPublic;
    private List<User> attendees;

    public Event(String name_, Sport sport_, Date time_, Location location_, int cost_,
                 String notes_, boolean isPublic_) {
        this.name = name_;
        this.sport = sport_;
        this.time = time_;
        this.location = location_;
        this.cost = cost_;
        this.notes = notes_;
        this.isPublic = isPublic_;
        this.attendees = new ArrayList<User>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public List<User> getAttendees() {
        return attendees;
    }

    public int getAttendeeCount() {
        return attendees.size();
    }

    public void addAttendee(User user) {
        this.attendees.add(user);
    }

    public void removeAttendee(User user) {
        this.attendees.remove(user);
    }

}
