package pickupsports2.ridgewell.pickupsports2.activities;

import android.app.FragmentManager;
import android.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.Session;

import pickupsports2.ridgewell.pickupsports2.elements.EditUserDialog;
import pickupsports2.ridgewell.pickupsports2.elements.NavigationDrawerFragment;
import pickupsports2.ridgewell.pickupsports2.R;
import pickupsports2.ridgewell.pickupsports2.elements.AddEventButton;
import pickupsports2.ridgewell.pickupsports2.utilities.ServerRequest;
import ridgewell.pickupsports2.common.User;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        EditUserDialog.OnEditUserListener {
    final int CREATE_EVENT_CODE = 1;

    /**
     * FAB for creating new events
     */
    AddEventButton create_event;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    /**
     * String that contains the value of each options menu button for a given fragment
     */
    private String optionButton;

    private MainActivityFragment myFragment = null;

    private static ServerRequest svreq = ServerRequest.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_view_screen);

        /* Navigation Drawer */
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        //Create Runnable for launching the new activity from create event button
        Runnable create_launcher = new Runnable() {
            @Override
            public void run() {
                Intent launch_new_event = new Intent(MainActivity.this, CreateEventActivity.class);
                startActivityForResult(launch_new_event, CREATE_EVENT_CODE);
            }
        };

        //Add FAB
        create_event = new AddEventButton(findViewById(R.id.create_event), create_launcher);

        //Set start screen
        if (savedInstanceState == null) {
            onNavigationDrawerItemSelected(0);
        }
    }

    /**
     * Returns the action bar to previous state
     */
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
            menu.findItem(R.id.fragment_action).setTitle(optionButton);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    public void onNavigationDrawerItemSelected(int position) {
        switch (position) {
            case 0:
                myFragment = ProfileFragment.newInstance();
                optionButton = "Edit";
                //TODO: create edit action
                break;
            case 1:
                optionButton = "Text Here";
                //Launch to Teams View
                break;
            case 2:
                myFragment = new EventFragment();
                optionButton = "Edit";
                //TODO: create edit action
                break;
            case 3:
                //Launch to Invitations
                optionButton = "Text Here";
                break;
            case 4:
                //Launch to Schedule
                optionButton = "Text Here";
                break;
            default:
        }

        if (myFragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, (Fragment) myFragment).commit();
            mTitle = getResources().getStringArray(R.array.navigation_pane_titles)[position];
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
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
        else if (id == R.id.fragment_action) {
            myFragment.onActionButtonClick();
            return true;
        } else if (id == R.id.log_out) {
            if (Session.getActiveSession() != null) {
                Session.getActiveSession().closeAndClearTokenInformation();
            }
            Session.setActiveSession(null);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public abstract interface MainActivityFragment {
        public abstract void onActionButtonClick();
        public abstract void refreshFragment();
    }

    public void onEditUserListener(User user) {
        Log.v("Calling","EditUser");
        svreq.editUser(user);
        Log.v("Called","EditUser");
        myFragment.refreshFragment();
    }
}