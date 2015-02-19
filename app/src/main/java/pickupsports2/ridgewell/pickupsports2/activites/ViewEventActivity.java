package pickupsports2.ridgewell.pickupsports2.activites;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import pickupsports2.ridgewell.pickupsports2.R;
import pickupsports2.ridgewell.pickupsports2.intents.IntentProtocol;
import pickupsports2.ridgewell.pickupsports2.utilities.ServerRequest;
import ridgewell.pickupsports2.common.*;

/**
 * Created by cameronridgewell on 1/22/15.
 */
public class ViewEventActivity extends ActionBarActivity {

    private Event event;

    private ServerRequest svreq = new ServerRequest();

    public ViewEventActivity() {}

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(pickupsports2.ridgewell.pickupsports2.R.layout.activity_view_event_screen);

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

        TextView viewItemTextDate = (TextView) findViewById(R.id.event_date_text);
        viewItemTextDate.setText(event.getTime().toString("MMMM d, yyyy")
                + " " + event.getDaysUntil());

        TextView viewItemTextTime = (TextView) findViewById(R.id.event_time_text);
        viewItemTextTime.setText(event.getTime().toString("h:mm a"));


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
        /*
        TextView viewRemainingAttendance = (TextView) findViewById(R.id.event_attendance_text);
        viewRemainingAttendance.setText((event.getMaxAttendance()-event.getAttendeeCount())
                + " out of " + event.getMaxAttendance() + " spots remaining");
        */
        viewItemTextCreator.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                User user = event.getCreator();
                IntentProtocol.viewUser(ViewEventActivity.this, user);
            }
        });

        startDeleteClickListener();
    }

    public void startDeleteClickListener() {
        Button delete_event = (Button) findViewById(R.id.delete_event);
        delete_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewEventActivity.this);
                builder
                        .setMessage("Are you sure you want to delete " + event.getName() + "?")
                        .setTitle("Confirm Delete")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                svreq.deleteEvent(event);
                                finish();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do nothing? go back somehow?
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
}