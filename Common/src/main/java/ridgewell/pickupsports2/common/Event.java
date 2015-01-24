package ridgewell.pickupsports2.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private int maxAttendance;
    private List<User> waitlist; //queue
    private User creator;

    //largest number of users allowed on the waitlist
    public final int WAITLISTMAX = 10;

    public Event(String name_, Sport sport_, Date time_, Location location_, int cost_,
                 String notes_, boolean isPublic_, int maxAttendance_, User creator_) {
        this.name = name_;
        this.sport = sport_;
        this.time = time_;
        this.location = location_;
        this.cost = cost_;
        this.notes = notes_;
        this.isPublic = isPublic_;
        this.maxAttendance = maxAttendance_;
        this.attendees = new ArrayList<User>();
        this.waitlist = new ArrayList<User>();
        this.creator = creator_;
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

    public int getMaxAttendance() {
        return maxAttendance;
    }

    public void setMaxAttendance(int maxAttendance) {
        this.maxAttendance = maxAttendance;
    }

    public List<User> getWaitlist() {
        return waitlist;
    }

    public void setWaitlist(List<User> waitlist) {
        this.waitlist = waitlist;
    }

    public User waitlistTop() {
        return waitlist.get(0);
    }

    //no checking for empty waitlist
    public User waitlistDequeue() {
        return waitlist.remove(0);
    }

    public boolean waitlistEnqueue(User user) {
        if (this.waitlistSize() < WAITLISTMAX) {
            waitlist.add(user);
            return true;
        }
        return false;
    }

    public boolean waitlistIsEmpty() {
        return waitlist.size() == 0;
    }

    public int waitlistSize() {
        return waitlist.size();
    }

    public User getCreator() {
        return creator;
    }
}
