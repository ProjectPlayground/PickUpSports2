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
public class Create_Event_Screen extends ListActivity implements OnClickListener {
    public Create_Event_Screen() {}

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_create_event_screen);

        final Spinner sportsSpinner = (Spinner) findViewById(R.id.sport_spinner);
        final Spinner costSpinner = (Spinner) findViewById(R.id.cost_spinner);
        final Spinner privacySpinner = (Spinner) findViewById(R.id.privacy_spinner);

        ArrayAdapter<CharSequence> sportsAdapter = ArrayAdapter.createFromResource(this,
                R.array.sports, android.R.layout.simple_spinner_item);
        sportsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sportsSpinner.setAdapter(sportsAdapter);

        ArrayAdapter<CharSequence> costAdapter = ArrayAdapter.createFromResource(this,
                R.array.cost_options, android.R.layout.simple_spinner_item);
        costAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        costSpinner.setAdapter(costAdapter);

        ArrayAdapter<CharSequence> privacyAdapter = ArrayAdapter.createFromResource(this,
                R.array.privacy_options, android.R.layout.simple_spinner_item);
        privacyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        privacySpinner.setAdapter(privacyAdapter);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        /*Intent myTriggerActivityIntent=new Intent(this,SecondActivity.class);
        startActivity(myTriggerActivityIntent);*/
    }
}