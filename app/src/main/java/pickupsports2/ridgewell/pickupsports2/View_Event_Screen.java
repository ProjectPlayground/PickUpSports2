package pickupsports2.ridgewell.pickupsports2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Date;

import ridgewell.pickupsports2.common.*;

/**
 * Created by cameronridgewell on 1/22/15.
 */
public class View_Event_Screen extends Activity {
    Event event;

    public View_Event_Screen() {}

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event_screen);

        Bundle extras = getIntent().getExtras();
        event = extras.getParcelable("viewable_event");

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
                Intent intent = new Intent(View_Event_Screen.this, View_User_Screen.class);
                intent.putExtra("user_view", event.getCreator());
                startActivity(intent);
            }
        });
    }
}
