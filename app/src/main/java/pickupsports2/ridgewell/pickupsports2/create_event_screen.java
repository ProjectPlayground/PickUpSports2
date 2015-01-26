package pickupsports2.ridgewell.pickupsports2;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.view.View.OnClickListener;

import java.util.Date;

import ridgewell.pickupsports2.common.Event;
import ridgewell.pickupsports2.common.Location;
import ridgewell.pickupsports2.common.Sport;
import ridgewell.pickupsports2.common.User;

import static pickupsports2.ridgewell.pickupsports2.R.layout.activity_create_event_screen;

/**
 * Created by cameronridgewell on 1/21/15.
 */
public class Create_Event_Screen extends Activity implements OnClickListener {
    public Create_Event_Screen() {}

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_create_event_screen);

        final EditText event_name = (EditText) findViewById(R.id.eventNameInput);

        final Spinner sportsSpinner = (Spinner) findViewById(R.id.sport_spinner);
        ArrayAdapter<CharSequence> sportsAdapter = ArrayAdapter.createFromResource(this,
                R.array.sports, android.R.layout.simple_spinner_item);
        sportsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sportsSpinner.setAdapter(sportsAdapter);

        final Spinner costSpinner = (Spinner) findViewById(R.id.cost_spinner);
        ArrayAdapter<CharSequence> costAdapter = ArrayAdapter.createFromResource(this,
                R.array.cost_options, android.R.layout.simple_spinner_item);
        costAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        costSpinner.setAdapter(costAdapter);


        final Spinner privacySpinner = (Spinner) findViewById(R.id.privacy_spinner);
        ArrayAdapter<CharSequence> privacyAdapter = ArrayAdapter.createFromResource(this,
                R.array.privacy_options, android.R.layout.simple_spinner_item);
        privacyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        privacySpinner.setAdapter(privacyAdapter);


        final EditText location = (EditText) findViewById(R.id.eventLocationInput);

        final EditText maxAttendance = (EditText) findViewById(R.id.eventAttendanceInput);

        final Button post = (Button) findViewById(R.id.create_event_post_button);
        post.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Sport, Location, and User passing not implemented yet. Creating new objects for now
                //TODO no notes
                //TODO grab creating user
                Event event = new Event(event_name.getText().toString(),
                        new Sport(sportsSpinner.getSelectedItem().toString()),
                        new Date(),
                        new Location(location.getText().toString()),
                        costSpinner.getSelectedItemPosition(), "",
                        privacySpinner.getSelectedItemPosition() == 1,
                        Integer.parseInt(maxAttendance.getText().toString()),
                        new User("Creator User"));
                //TODO push object to the server
                System.out.println("Event " + event.getName() + " created");
            }
        });
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        /*Intent myTriggerActivityIntent=new Intent(this,SecondActivity.class);
        startActivity(myTriggerActivityIntent);*/
    }
}