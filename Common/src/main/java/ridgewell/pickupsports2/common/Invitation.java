package ridgewell.pickupsports2.common;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cameronridgewell on 4/4/15.
 */
public class Invitation implements Parcelable{
    private String _id;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    private String inviter_id;
    private String invitee_id;
    private String event_id;

    public Invitation(String event_id, String invitee_id, String inviter_id) {
        this.event_id = event_id;
        this.invitee_id = invitee_id;
        this.inviter_id = inviter_id;
    }

    public String getInviter_id() {
        return inviter_id;
    }

    public String getInvitee_id() {
        return invitee_id;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setInviter_id(String inviter_id) {
        this.inviter_id = inviter_id;
    }

    public void setInvitee_id(String invitee_id) {
        this.invitee_id = invitee_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(inviter_id);
        out.writeString(invitee_id);
        out.writeString(event_id);
    }

    public static final Parcelable.Creator<Invitation> CREATOR
            = new Parcelable.Creator<Invitation>() {
        public Invitation createFromParcel(Parcel in) {
            return new Invitation(in);
        }

        public Invitation[] newArray(int size) {
            return new Invitation[size];
        }
    };

    private Invitation(Parcel in) {
        inviter_id = in.readString();
        invitee_id = in.readString();
        event_id = in.readString();
    }
}
