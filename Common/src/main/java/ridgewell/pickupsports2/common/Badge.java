package ridgewell.pickupsports2.common;

import java.lang.String;

/**
 * Created by cameronridgewell on 1/16/15.
 * Badge class to support user upgrades
 */
public class Badge {
    private String name = new String();
    //icon of some kind

    public Badge(String name_) {
        name = name_;
    }

    public String getName() {
        return name;
    }

    public boolean qualifies(User user) {
        //change to accept different qualifying criteria
        return false;
    }
}
