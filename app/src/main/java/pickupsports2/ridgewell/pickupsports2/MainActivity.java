package pickupsports2.ridgewell.pickupsports2;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

import ridgewell.pickupsports2.common.*;

public class MainActivity extends ListActivity {
    final int CREATE_EVENT_CODE = 1;
    final int SUCCESS_CODE = 1;

    ArrayList<Event> arraylist;
    SportingEventArrayAdapter sportingEvent_arrayAdapter_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view_screen);

        arraylist = new ArrayList<Event>();
        Sport football = new Sport("Football");
        Sport soccer = new Sport("Soccer");
        Date date1 = new Date();
        Date date2 = new Date();
        date2.setHours(-72);
        Location loc1 = new Location("Nashville, TN");
        User user1 = new User("Cameron Ridgewell");
        Event event1 = new Event("Random Name 1", football, date1, loc1, 0, "", false, 30, user1);
        Event event2 = new Event("Random Name 2", soccer, date2, loc1, 3, "notes here", true, 20, user1);

        arraylist.add(event1);
        arraylist.add(event2);

        sportingEvent_arrayAdapter_ = new SportingEventArrayAdapter(this, arraylist);

        this.setListAdapter(sportingEvent_arrayAdapter_);
    }

    @Override
    protected void onListItemClick (ListView l, View v, int position, long id) {
        Intent intent = new Intent(MainActivity.this, ViewEventActivity.class);
        /*
        String[] event_data = { arraylist.get(position).getName(),
                arraylist.get(position).getCreator().getUsername(),
                arraylist.get(position).getSport().getSportName(),
                arraylist.get(position).getLocation().getLocation(),
                arraylist.get(position).getTime().toString(),
                arraylist.get(position).getDaysUntil() + ""};
        intent.putExtra("viewable_event", event_data);
        */
        intent.putExtra("viewable_event", arraylist.get(position));
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
                arraylist.add((Event) data.getExtras().getParcelable("created_event"));
                sportingEvent_arrayAdapter_.notifyDataSetChanged();
            } else {
                //TODO Toast created event failed
            }
        }
    }
}
