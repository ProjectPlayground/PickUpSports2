package ridgewell.pickupsports2.common;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cameronridgewell on 1/16/15.
 */
public class Sport implements Parcelable {
    private String sportName;
    //picture for the sport

    public Sport(String name) {
        this.sportName = name;
    }

    /*
     * returns 0 for sports with the same name, -1 if parameter is greater, 1 if parameter is less
     */
    public int compareTo(Sport sport) {
        return this.sportName.compareToIgnoreCase(sport.sportName);
    }

    public String getSportName() {
        return sportName;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(sportName);
    }

    public static final Parcelable.Creator<Sport> CREATOR
            = new Parcelable.Creator<Sport>() {
        public Sport createFromParcel(Parcel in) {
            return new Sport(in);
        }

        public Sport[] newArray(int size) {
            return new Sport[size];
        }
    };

    private Sport(Parcel in) {
        sportName = in.readString();
    }
}
