package pickupsports2.ridgewell.pickupsports2.activites;

import android.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.concurrent.ExecutionException;

import pickupsports2.ridgewell.pickupsports2.R;
import pickupsports2.ridgewell.pickupsports2.elements.AddEventButton;
import pickupsports2.ridgewell.pickupsports2.utilities.ServerRequest;
import ridgewell.pickupsports2.common.Event;
import ridgewell.pickupsports2.common.Location;
import ridgewell.pickupsports2.common.Sport;
import ridgewell.pickupsports2.common.User;

public class MainActivity extends ActionBarActivity {
    final int CREATE_EVENT_CODE = 1;

    EventFragment eventList;

    AddEventButton create_event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Games");

        setContentView(R.layout.activity_main_view_screen);

        FragmentManager fm = getFragmentManager();

        if (fm.findFragmentById(R.id.event_list_fragment) == null) {
            eventList = new EventFragment();
            fm.beginTransaction().add(R.id.event_list_fragment, eventList).commit();
        }

        Runnable create_launcher = new Runnable() {
            @Override
            public void run() {
                Intent launch_new_event = new Intent(MainActivity.this, CreateEventActivity.class);
                startActivityForResult(launch_new_event, CREATE_EVENT_CODE);
            }
        };
        create_event = new AddEventButton(findViewById(R.id.create_event), create_launcher);
    }

    public void onResume() {
        super.onResume();
        eventList.refreshEvents();
        ServerRequest svreq = new ServerRequest();
        Event e = svreq.getEvent("Top Gun Volleyball");
        if (e != null) {
            Log.v("--------", e.getSport());
        } else {
            Log.v("error", "nothing returned");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_view_screen, menu);
        return true;
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
        return super.onOptionsItemSelected(item);
    }
}


