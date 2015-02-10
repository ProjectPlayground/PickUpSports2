package pickupsports2.ridgewell.pickupsports2;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.joda.time.DateTime;

import java.util.List;

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

        Runnable r = new Runnable() {
            @Override
            public void run() {
                RequestLibrary svc = new RestAdapter.Builder()
                        .setEndpoint("http://192.168.56.1:8080")
                        .build().create(RequestLibrary.class);
                Log.e("Current Time",DateTime.now().toString());
                /*try {
                    Fooey user = svc.getUser("Cameron Ridgewell");
                    Log.e("Username Tag:", "############" + user.getUsername() + "############");
                } catch (NullPointerException e) {
                    Log.e("null object detection: ", "null object was returned by service");
                }*/


                Location location = new Location("Nashville, TN");
                Fooey user = new Fooey();
                user.setUsername("Cameron Ridgewell");
                user.setNickname("Cam");
                user.setJoinTime("abcd");
                //user.setLocation(location);

                svc.addUser(user, new Callback<Fooey>() {
                    @Override
                    public void success(Fooey fooey, Response response) {
                        Log.e("Retrofit Success", "Fooey response");
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("Retrofit Error", "addUser Failed");
                    }
                });

            }
        };
        Thread t = new Thread(r);
        t.start();


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
                //TODO push to server
                events.add((Event) data.getExtras().getParcelable("created_event"));
                sportingEventArrayAdapter.notifyDataSetChanged();
            } else {
                //TODO Toast created event failed
            }
        }
    }
}
