package pickupsports2.ridgewell.pickupsports2;

import android.app.ListActivity;
import android.os.Parcel;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import ridgewell.pickupsports2.common.*;


public class Main_View_Screen extends ListActivity {

    ArrayList<Event> arraylist;
    ListView main_event_listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view_screen);

        arraylist = new ArrayList<Event>();
        Sport football = new Sport("Football");
        Sport soccer = new Sport("Soccer");
        Date date1 = new Date();
        Location loc1 = new Location("Nashville, TN");
        User user1 = new User("Cameron Ridgewell");
        Event event1 = new Event("Random Name 1", football, date1, loc1, 0, "", false, 30, user1);
        Event event2 = new Event("Random Name 2", soccer, date1, loc1, 3, "notes here", true, 20, user1);

        arraylist.add(event1);
        arraylist.add(event2);

        Main_View_ArrayAdapter main_view_arrayAdapter = new Main_View_ArrayAdapter(this, arraylist);

        this.setListAdapter(main_view_arrayAdapter);
    }

    @Override
    protected void onListItemClick (ListView l, View v, int position, long id) {
        Intent intent = new Intent(Main_View_Screen.this, View_Event_Screen.class);
        String[] event_data = { arraylist.get(position).getName(),
                arraylist.get(position).getCreator().getUsername(),
                arraylist.get(position).getSport().getSportName(),
                arraylist.get(position).getLocation().getLocation()};
        intent.putExtra("viewable_event", event_data);
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
            Intent launch_new_event = new Intent(Main_View_Screen.this, Create_Event_Screen.class);
            startActivity(launch_new_event);
        }
        return super.onOptionsItemSelected(item);
    }
}
