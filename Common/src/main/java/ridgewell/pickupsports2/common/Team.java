package ridgewell.pickupsports2.common;

import android.os.Parcel;
import android.os.Parcelable;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cameronridgewell on 1/16/15.
 */
public class Team implements Parcelable {
    private String teamName;
    //picture for the sport

    public List<String> sports = new ArrayList<String>();
    public List<String> members = new ArrayList<String>();

    public long createTimeLong;

    public Team(String name)
    {
        this.teamName = name;
        this.createTimeLong = DateTime.now().getMillis();
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<String> getSports() {
        return sports;
    }

    public void setSports(List<String> sports) {
        this.sports = sports;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public long getCreateTimeLong() {
        return createTimeLong;
    }

    public void setCreateTimeLong(long createTimeLong) {
        this.createTimeLong = createTimeLong;
    }

    public DateTime getCreateTime() {
        return new DateTime(createTimeLong);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(teamName);
        out.writeStringList(sports);
        out.writeStringList(members);
        out.writeLong(createTimeLong);
    }

    public static final Parcelable.Creator<Team> CREATOR
            = new Parcelable.Creator<Team>() {
        public Team createFromParcel(Parcel in) {
            return new Team(in);
        }

        public Team[] newArray(int size) {
            return new Team[size];
        }
    };

    private Team(Parcel in) {
        teamName = in.readString();
        sports = in.createStringArrayList();
        members = in.createStringArrayList();
        createTimeLong = in.readLong();
    }
}
