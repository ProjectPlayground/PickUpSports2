package pickupsports2.ridgewell.pickupsports2;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

import pickupsports2.ridgewell.pickupsports2.intents.IntentProtocol;
import ridgewell.pickupsports2.common.*;

/**
 * Created by cameronridgewell on 1/22/15.
 */
public class ViewEventActivity extends ActionBarActivity {

    private Event event;

    public ViewEventActivity() {}

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event_screen);

        this.event = IntentProtocol.getEvent(this);

        setTitle(event.getName());

        TextView viewItemTextTitle = (TextView) findViewById(R.id.view_event_title);
        viewItemTextTitle.setText(event.getName());

        TextView viewItemTextCreator = (TextView) findViewById(R.id.view_event_creator);
        viewItemTextCreator.setText(event.getCreator().getUsername());

        TextView viewItemTextSport = (TextView) findViewById(R.id.event_sport_text);
        viewItemTextSport.setText(event.getSport().getSportName());

        TextView viewItemTextLocation = (TextView) findViewById(R.id.event_location_text);
        viewItemTextLocation.setText(event.getLocation().getLocation());

        TextView viewItemTextTime = (TextView) findViewById(R.id.event_time_text);
        viewItemTextTime.setText(event.getTime().toString() + " " + event.getDaysUntil());

        TextView viewItemTextCost = (TextView) findViewById(R.id.event_cost_text);
        switch (event.getCost()) {
            case 0:
                viewItemTextCost.setText("Free");
                break;
            case 1:
                viewItemTextCost.setText("$");
                break;
            case 2:
                viewItemTextCost.setText("$$");
                break;
            case 3:
                viewItemTextCost.setText("$$$");
                break;
            default:
                viewItemTextCost.setText("Error");
                break;
        }

        TextView viewItemNotes = (TextView) findViewById(R.id.event_notes_text);
        viewItemNotes.setText(event.getNotes());

        TextView viewRemainingAttendance = (TextView) findViewById(R.id.event_attendance_text);
        viewRemainingAttendance.setText((event.getMaxAttendance()-event.getAttendeeCount())
                + " out of " + event.getMaxAttendance() + " spots remaining");

        viewItemTextCreator.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                User user = event.getCreator();
                IntentProtocol.viewUser(ViewEventActivity.this, user);
            }
        });
    }
}
