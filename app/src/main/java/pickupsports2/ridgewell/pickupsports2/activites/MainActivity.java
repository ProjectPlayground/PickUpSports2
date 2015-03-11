package pickupsports2.ridgewell.pickupsports2.activites;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import pickupsports2.ridgewell.pickupsports2.NavigationDrawerFragment;
import pickupsports2.ridgewell.pickupsports2.R;
import pickupsports2.ridgewell.pickupsports2.elements.AddEventButton;
import pickupsports2.ridgewell.pickupsports2.utilities.ServerRequest;
import ridgewell.pickupsports2.common.Event;
import org.joda.time.DateTime;

public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    final int CREATE_EVENT_CODE = 1;

    EventFragment eventList;

    AddEventButton create_event;
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_view_screen);

        if (savedInstanceState == null) {
            displayView(1);
        }

        /* Navigation Drawer */
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));


        /* Main View List */
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
        mTitle = "Games";
    }

    private void displayView(final int position) {
        Log.d("here","1");
        Fragment fragment = null;
        switch (position) {
            case 1:
                Log.d("here","2");
                fragment = Profile.newInstance();
                //Launch to Profile
                break;
            case 2:
                mTitle = getResources().getStringArray(R.array.navigation_pane_titles)[1];
                //Launch to Teams View
                break;
            case 3:
                mTitle = getResources().getStringArray(R.array.navigation_pane_titles)[2];
                //Launch to Events
                break;
            case 4:
                mTitle = getResources().getStringArray(R.array.navigation_pane_titles)[3];
                //Launch to Invitations
                break;
            case 5:
                mTitle = getResources().getStringArray(R.array.navigation_pane_titles)[4];
                //Launch to Calendar
                break;
            default:
        }

        if (fragment != null) {
            Log.v("fragment loading","");
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment).commit();
            Log.d("Fragment Loaded","");
            setTitle(getResources().getStringArray(R.array.navigation_pane_titles)[position-1]);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.navigation_pane, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        displayView(position);
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

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getResources().getStringArray(R.array.navigation_pane_titles)[0];
                //Launch to Profile
                break;
            case 2:
                mTitle = getResources().getStringArray(R.array.navigation_pane_titles)[1];
                //Launch to Teams View
                break;
            case 3:
                mTitle = getResources().getStringArray(R.array.navigation_pane_titles)[2];
                //Launch to Events
                break;
            case 4:
                mTitle = getResources().getStringArray(R.array.navigation_pane_titles)[3];
                //Launch to Invitations
                break;
            case 5:
                mTitle = getResources().getStringArray(R.array.navigation_pane_titles)[4];
                //Launch to Calendar
                break;
            default:
                break;
        }
    }

    public void OnFragmentInteractionListener(String string) {
        Log.v(string, string);
    }
}


