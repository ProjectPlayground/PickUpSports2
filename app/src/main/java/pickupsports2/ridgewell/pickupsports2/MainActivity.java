package pickupsports2.ridgewell.pickupsports2;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import pickupsports2.ridgewell.pickupsports2.data.DummyEventSource;
import pickupsports2.ridgewell.pickupsports2.data.EventSource;
import pickupsports2.ridgewell.pickupsports2.intents.IntentProtocol;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import ridgewell.pickupsports2.common.Event;
import ridgewell.pickupsports2.common.Location;
import ridgewell.pickupsports2.common.User;

public class MainActivity extends ListActivity {
    final int CREATE_EVENT_CODE = 1;
    final int SUCCESS_CODE = 1;

    private List<Event> events = new ArrayList<Event>();
    private SportingEventArrayAdapter sportingEventArrayAdapter;

    private ServerRequest svreq = new ServerRequest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view_screen);

        // Fetch all events that are occurring today
        DateTime today = DateTime.now();
        DateTime tomorrow = DateTime.now().plusDays(1);
        //this.events = this.eventSource.getEventsInDateRange(today, tomorrow);

        try {
            events = svreq.getAllEvents();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        sportingEventArrayAdapter = new SportingEventArrayAdapter(this, this.events);

        this.setListAdapter(sportingEventArrayAdapter);
    }

        @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Event toOpen = events.get(position);
        IntentProtocol.viewEvent(this, toOpen);
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
                try {
                    Log.v("Fetching All Events", "All Events Fetched");
                    this.events = svreq.getAllEvents();
                    //TODO figure out why data set is not updating when a new event is created
                    this.sportingEventArrayAdapter.notifyDataSetChanged();
                } catch (InterruptedException e1) {
                    Log.e("Server interrupt", e1.getMessage());
                    e1.printStackTrace();
                } catch (ExecutionException e2) {
                    Log.e("Execution Exception", e2.getMessage());
                    e2.printStackTrace();
                }
            } else {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "An error occurred while creating your event", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
}
