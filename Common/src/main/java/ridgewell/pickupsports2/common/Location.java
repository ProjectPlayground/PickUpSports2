package ridgewell.pickupsports2.common;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cameronridgewell on 1/16/15.
 */
public class Location implements Parcelable{
    private String location;

    public Location() {}

    public Location(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location_) {
        location = location_;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(location);
    }

    public static final Parcelable.Creator<Location> CREATOR
            = new Parcelable.Creator<Location>() {
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        public Location[] newArray(int size) {
            return new Location[size];
        }
    };

    private Location(Parcel in) {
        location = in.readString();
    }
}
