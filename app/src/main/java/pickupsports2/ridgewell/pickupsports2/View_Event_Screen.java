package pickupsports2.ridgewell.pickupsports2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ridgewell.pickupsports2.common.*;

import static pickupsports2.ridgewell.pickupsports2.R.layout.view_event_screen;

/**
 * Created by cameronridgewell on 1/22/15.
 */
public class View_Event_Screen extends Activity {
    Event event;
    Context myContext;

    public View_Event_Screen() {}

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_event_screen);

        Intent intent = getIntent();
        String[] event_data = intent.getStringArrayExtra("viewable_event");

        TextView viewItemTextTitle = (TextView) findViewById(R.id.view_event_title);
        viewItemTextTitle.setText(event_data[0]);

        TextView viewItemTextCreator = (TextView) findViewById(R.id.view_event_creator);
        viewItemTextCreator.setText("Created by " + event_data[1]);

        TextView viewItemTextSport = (TextView) findViewById(R.id.event_sport_heading);
        viewItemTextSport.setText(event_data[2]);

        TextView viewItemTextLocation = (TextView) findViewById(R.id.event_location_heading);
        viewItemTextLocation.setText(event_data[3]);

    }
}
