package pickupsports2.ridgewell.pickupsports2.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Session;

import pickupsports2.ridgewell.pickupsports2.R;
import pickupsports2.ridgewell.pickupsports2.elements.AttendedEventsDialog;
import pickupsports2.ridgewell.pickupsports2.elements.EditEventDialog;
import pickupsports2.ridgewell.pickupsports2.intents.IntentProtocol;
import pickupsports2.ridgewell.pickupsports2.utilities.ActivityOnClickListener;
import pickupsports2.ridgewell.pickupsports2.utilities.ServerRequest;
import pickupsports2.ridgewell.pickupsports2.utilities.UserData;
import ridgewell.pickupsports2.common.*;

/**
 * Created by cameronridgewell on 1/22/15.
 */
public class ViewEventActivity extends ActionBarActivity
        implements EditEventDialog.OnEditEventListener{

    private Event event;

    private ServerRequest svreq = ServerRequest.getInstance();
    
    private User creator = null;

    static final int DATE_DIALOG_ID = 999;
    static final int TIME_DIALOG_ID = 888;

    EditEventDialog editEventDialog = null;
    AttendedEventsDialog attendedEventsDialog = null;

    public ViewEventActivity() {}

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.event = IntentProtocol.getEvent(this);

        this.creator = svreq.getUser(event.getCreator_id());

        this.setContentView(R.layout.activity_view_event);

        setTexts();

        startDeleteClickListener();
    }

    public void startDeleteClickListener() {
        Button delete_event = (Button) findViewById(R.id.delete_event);
        if (!event.isCreator(UserData.getInstance().getThisUser(ViewEventActivity.this))) {
            delete_event.setVisibility(View.GONE);
        }
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
                                Log.v("attempt to delete",event.getName());
                                Log.v("Event ID to be deleted", event.get_id());
                                svreq.deleteEvent(event);
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {}
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
    
    private void setTexts() {
        TextView viewItemTextTitle = (TextView) findViewById(R.id.view_event_title);
        viewItemTextTitle.setText(event.getName());

        TextView viewItemTextCreator = (TextView) findViewById(R.id.view_event_creator);
        viewItemTextCreator.setText(creator.getFirstname() + " " + creator.getLastname());

        TextView viewItemTextSport = (TextView) findViewById(R.id.event_sport_text);
        viewItemTextSport.setText(event.getSport());

        TextView viewItemTextLocation = (TextView) findViewById(R.id.event_location_text);
        viewItemTextLocation.setText(event.getLocation().toString());

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

        TextView spotsRemaining = (TextView) findViewById(R.id.event_attendance_text);
        spotsRemaining.setText((this.event.getMaxAttendance() - this.event.getAttendeeCount()) +
                " of " + this.event.getMaxAttendance() + " spots remaining");

        viewItemTextCreator.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                IntentProtocol.viewUser(ViewEventActivity.this, creator);
            }
        });

        updateAttendanceActions();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_pane, menu);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(android.support.v7.app.ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        menu.findItem(R.id.fragment_action).setTitle("Edit");
        menu.findItem(R.id.action_search).setVisible(false);
        if (!event.isCreator(UserData.getInstance().getThisUser(ViewEventActivity.this))) {
            menu.findItem(R.id.fragment_action).setVisible(false);
        } else {
            menu.findItem(R.id.fragment_action).setVisible(true);
        }
        return super.onCreateOptionsMenu(menu);
    }

    private void updateAttendanceActions() {
        TextView spotsRemaining = (TextView) findViewById(R.id.event_attendance_text);
        spotsRemaining.setText((this.event.getMaxAttendance() - this.event.getAttendeeCount()) +
                " of " + this.event.getMaxAttendance() + " spots remaining");
        User user = UserData.getInstance().getThisUser(ViewEventActivity.this);

        Button attendButton = (Button) findViewById(R.id.attendButton);

        Bundle bundle = new Bundle();
        bundle.putParcelable("user", user);
        bundle.putParcelable("event",this.event);

        if (!event.getAttendees().contains(user.get_id())) {
            attendButton.setText(getResources().getString(R.string.attendButton));
            attendButton.setOnClickListener(new ActivityOnClickListener(ViewEventActivity.this, bundle){
                public void onClick(View v) {
                    User user = getArguments().getParcelable("user");
                    Event event = getArguments().getParcelable("event");
                    Log.v("User", user.get_id() + " Attends Event: " + event.get_id());
                    Log.v(user.get_id(), event.getAttendees().toString());
                    svreq.attendEvent(event, user);
                    event.addAttendee(user);
                    user.addEvent(event);
                    updateAttendanceActions();
                }
            });
        } else {
            attendButton.setText(getResources().getString(R.string.leaveButton));
            attendButton.setOnClickListener(new ActivityOnClickListener(ViewEventActivity.this, bundle){
                public void onClick(View v) {
                    User user = getArguments().getParcelable("user");
                    Event event = getArguments().getParcelable("event");
                    Log.v("User", user.get_id() + " Leaves Event: " + event.get_id());
                    svreq.leaveEvent(event, user);
                    event.removeAttendee(user);
                    user.removeEvent(event);
                    updateAttendanceActions();
                }
            });
        }
    }

    public void onEditEventListener(Event event) {
        Log.v("Calling","EditEvent");
        svreq.editEvent(event);
        refreshActivity();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        else if (id == R.id.fragment_action) {
            this.onActionButtonClick();
            return true;
        } else if (id == R.id.log_out) {
            if (Session.getActiveSession() != null) {
                Session.getActiveSession().closeAndClearTokenInformation();
            }
            Session.setActiveSession(null);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void onActionButtonClick() {
        editEventDialog = new EditEventDialog();
        Bundle args = new Bundle();
        args.putParcelable("event",event);
        editEventDialog.setArguments(args);
        editEventDialog.show(getFragmentManager(), "edit event");
    }

    @Override
    public Dialog onCreateDialog(int id) {
        if (id == DATE_DIALOG_ID || id == TIME_DIALOG_ID) {
            return editEventDialog.createDialog(id);
        } else {
            return super.onCreateDialog(id);
        }
    }

    public void refreshActivity() {
        try{
            Thread.currentThread().sleep(250);
        }catch(Exception e){}
        event = svreq.getEvent(event.get_id());
        setTexts();
    }
}
