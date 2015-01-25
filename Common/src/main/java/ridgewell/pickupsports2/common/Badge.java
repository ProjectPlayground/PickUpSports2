package ridgewell.pickupsports2.common;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cameronridgewell on 1/16/15.
 * Badge class to support user upgrades
 */
public class Badge implements Parcelable {
    private String name;
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

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
    }

    public static final Parcelable.Creator<Badge> CREATOR
        = new Parcelable.Creator<Badge>() {
        public Badge createFromParcel(Parcel in) {
            return new Badge(in);
        }

        public Badge[] newArray(int size) {
            return new Badge[size];
        }
    };

    private Badge(Parcel in) {
        name = in.readString();
    }
}
