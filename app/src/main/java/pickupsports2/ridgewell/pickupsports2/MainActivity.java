package pickupsports2.ridgewell.pickupsports2;

import android.app.FragmentManager;
import android.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
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
import pickupsports2.ridgewell.pickupsports2.intents.IntentProtocol;
import ridgewell.pickupsports2.common.Event;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Games");
        setContentView(R.layout.activity_main_view_screen);

        FragmentManager fm = getFragmentManager();

        if (fm.findFragmentById(android.R.id.content) == null) {
            EventFragment list = new EventFragment();
            fm.beginTransaction().add(android.R.id.content, list).commit();
        }
    }

    public static class EventFragment extends ListFragment {

        final int CREATE_EVENT_CODE = 1;
        final int SUCCESS_CODE = 1;

        private final EventSource eventSource = new DummyEventSource();

        private List<Event> events;
        private SportingEventArrayAdapter sportingEventArrayAdapter;


        public void onActivityCreated(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            DateTime today = DateTime.now();
            this.events = this.eventSource.getEventsInDateRange(today, DateTime.now().plusDays(9));

            sportingEventArrayAdapter = new SportingEventArrayAdapter(this.getActivity(), this.events);

            this.setListAdapter(sportingEventArrayAdapter);
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            Event toOpen = events.get(position);
            IntentProtocol.viewEvent(this.getActivity(), toOpen);
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
        } /*
        if (id == R.id.create_new_event) {
            Intent launch_new_event = new Intent(MainActivity.this, CreateEventActivity.class);
            startActivityForResult(launch_new_event, CREATE_EVENT_CODE);
        } */
        return super.onOptionsItemSelected(item);
    }
}
