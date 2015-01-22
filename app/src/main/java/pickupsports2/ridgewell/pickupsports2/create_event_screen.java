package pickupsports2.ridgewell.pickupsports2;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.view.View.OnClickListener;

import static pickupsports2.ridgewell.pickupsports2.R.layout.activity_create_event_screen;

/**
 * Created by cameronridgewell on 1/21/15.
 */
public class create_event_screen extends ListActivity implements OnClickListener {
    public create_event_screen(Context context) {
        this.setContentView(activity_create_event_screen);
        this.setTitle(context.getResources().getString(R.string.create_event_title));

        final Spinner sportsSpinner = (Spinner) findViewById(R.id.sport_spinner);
        final Spinner costSpinner = (Spinner) findViewById(R.id.cost_spinner);
        final Spinner privacySpinner = (Spinner) findViewById(R.id.privacy_spinner);

        ArrayAdapter<CharSequence> sportsAdapter = ArrayAdapter.createFromResource(context,
                R.array.sports, android.R.layout.simple_spinner_item);
        sportsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sportsSpinner.setAdapter(sportsAdapter);

        ArrayAdapter<CharSequence> costAdapter = ArrayAdapter.createFromResource(context,
                R.array.cost_options, android.R.layout.simple_spinner_item);
        sportsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        costSpinner.setAdapter(costAdapter);

        ArrayAdapter<CharSequence> privacyAdapter = ArrayAdapter.createFromResource(context,
                R.array.privacy_options, android.R.layout.simple_spinner_item);
        sportsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        privacySpinner.setAdapter(privacyAdapter);
    }

    Button invite_friends;
    Button post_event;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event_screen);
        invite_friends=(Button)findViewById(R.id.create_event_invite_button);
        invite_friends.setOnClickListener(this);
        post_event=(Button)findViewById(R.id.create_event_post_button);
        post_event.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        /*Intent myTriggerActivityIntent=new Intent(this,SecondActivity.class);
        startActivity(myTriggerActivityIntent);*/
    }
}