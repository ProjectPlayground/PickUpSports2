package pickupsports2.ridgewell.pickupsports2;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import org.joda.time.DateTime;

import java.util.List;

import pickupsports2.ridgewell.pickupsports2.data.DummyEventSource;
import pickupsports2.ridgewell.pickupsports2.data.EventSource;
import ridgewell.pickupsports2.common.Event;

public class MainActivity extends ListActivity {
    final int CREATE_EVENT_CODE = 1;
    final int SUCCESS_CODE = 1;

    private final EventSource eventSource = new DummyEventSource();

    private List<Event> events;
    private SportingEventArrayAdapter sportingEventArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view_screen);

        // Fetch all events that are occurring today
        DateTime today = DateTime.now();
        DateTime tomorrow = DateTime.now().plusDays(1);
        this.events = this.eventSource.getEventsInDateRange(today, tomorrow);

        sportingEventArrayAdapter = new SportingEventArrayAdapter(this, this.events);

        this.setListAdapter(sportingEventArrayAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(MainActivity.this, ViewEventActivity.class);
        /*
        String[] event_data = { events.get(position).getName(),
                events.get(position).getCreator().getUsername(),
                events.get(position).getSport().getSportName(),
                events.get(position).getLocation().getLocation(),
                events.get(position).getTime().toString(),
                events.get(position).getDaysUntil() + ""};
        intent.putExtra("viewable_event", event_data);
        */
        intent.putExtra("viewable_event", events.get(position));
        startActivity(intent);
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
        if (id == R.id.create_new_event) {
            Intent launch_new_event = new Intent(MainActivity.this, CreateEventActivity.class);
            startActivityForResult(launch_new_event, CREATE_EVENT_CODE);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CREATE_EVENT_CODE) {
            if (resultCode == SUCCESS_CODE) {
                //TODO push to server
                events.add((Event) data.getExtras().getParcelable("created_event"));
                sportingEventArrayAdapter.notifyDataSetChanged();
            } else {
                //TODO Toast created event failed
            }
        }
    }
}
