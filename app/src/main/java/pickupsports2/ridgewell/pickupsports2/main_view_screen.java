package pickupsports2.ridgewell.pickupsports2;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import ridgewell.pickupsports2.common.*;


public class Main_View_Screen extends ListActivity {

    ArrayList<Event> arraylist;
    ListView main_event_list;

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
