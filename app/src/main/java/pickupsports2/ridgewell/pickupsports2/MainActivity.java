package pickupsports2.ridgewell.pickupsports2;

import android.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.widget.Toast;
import java.util.concurrent.ExecutionException;

public class MainActivity extends ActionBarActivity {
    final int CREATE_EVENT_CODE = 1;
    final int SUCCESS_CODE = 1;

    EventFragment eventList;

    private ServerRequest svreq = new ServerRequest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Games");
        setContentView(R.layout.activity_main_view_screen);

        FragmentManager fm = getFragmentManager();

        if (fm.findFragmentById(android.R.id.content) == null) {
            eventList = new EventFragment();
            fm.beginTransaction().add(android.R.id.content, eventList).commit();
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
        if (id == R.id.create_new_event) {
            Intent launch_new_event = new Intent(MainActivity.this, CreateEventActivity.class);
            startActivityForResult(launch_new_event, CREATE_EVENT_CODE);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CREATE_EVENT_CODE) {
            if (resultCode == SUCCESS_CODE) {
                try {
                    Log.v("Fetching All Events", "All Events Fetched");
                    this.eventList.setEvents(svreq.getAllEvents());
                    //TODO figure out why data set is not updating when a new event is created
                    this.eventList.getSportingEventArrayAdapter().notifyDataSetChanged();
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
