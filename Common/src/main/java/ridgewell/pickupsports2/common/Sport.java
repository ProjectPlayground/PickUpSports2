package ridgewell.pickupsports2;
import java.lang.String;

/**
 * Created by cameronridgewell on 1/16/15.
 */
public class Sport implements Comparable<Sport> {
    private String sportName;
    //picture for the sport

    public Sport(String name_) {
        sportName = name_;
    }

    /*
     * returns 0 for sports with the same name, -1 if parameter is greater, 1 if parameter is less
     */
    public int compareTo(Sport sport) {
        return this.sportName.compareToIgnoreCase(sport.sportName);
    }
}
