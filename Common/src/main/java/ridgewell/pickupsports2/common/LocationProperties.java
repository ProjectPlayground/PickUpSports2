package ridgewell.pickupsports2.common;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cameronridgewell on 1/16/15.
 */
public class LocationProperties implements Parcelable{
    private String location;
    private float latitude;
    private float longitude;

    public LocationProperties() {}

    public LocationProperties(String location) {
        this.location = location;
        this.latitude = 0;
        this.longitude = 0;
    }

    @Override
    public String toString() {
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
        out.writeFloat(latitude);
        out.writeFloat(longitude);
    }

    public static final Parcelable.Creator<LocationProperties> CREATOR
            = new Parcelable.Creator<LocationProperties>() {
        public LocationProperties createFromParcel(Parcel in) {
            return new LocationProperties(in);
        }

        public LocationProperties[] newArray(int size) {
            return new LocationProperties[size];
        }
    };

    public float getLatitude() {
        return this.latitude;
    }

    public float getLongitude() {
        return this.longitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    private LocationProperties(Parcel in) {
        location = in.readString();
        latitude = in.readFloat();
        longitude = in.readFloat();
    }
}
