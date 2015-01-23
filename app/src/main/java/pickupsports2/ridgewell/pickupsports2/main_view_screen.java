package pickupsports2.ridgewell.pickupsports2;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

import ridgewell.pickupsports2.common.*;


public class Main_View_Screen extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view_screen);
        ArrayList<Event> arraylist = new ArrayList<Event>();
        Sport football = new Sport("Football");
        Sport soccer = new Sport("Soccer");
        Date date1 = new Date();
        Location loc1 = new Location("Nashville, TN");
        Event event1 = new Event("Random Name 1", football, date1, loc1, 0, "", false, 30);
        Event event2 = new Event("Random Name 2", soccer, date1, loc1, 3, "notes here", true, 20);
        for (int i = 0; i < 6; i++) {
            arraylist.add(event1);
            arraylist.add(event2);
        }

        Main_View_ArrayAdapter main_view_arrayAdapter = new Main_View_ArrayAdapter(this, arraylist);

        this.setListAdapter(main_view_arrayAdapter);
    }

    public void onListItemClick(ListView lv ,View view,int position) {

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
