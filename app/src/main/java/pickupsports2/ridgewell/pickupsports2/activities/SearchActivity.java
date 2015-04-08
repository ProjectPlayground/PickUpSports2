package pickupsports2.ridgewell.pickupsports2.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pickupsports2.ridgewell.pickupsports2.R;
import pickupsports2.ridgewell.pickupsports2.elements.EditUserDialog;
import pickupsports2.ridgewell.pickupsports2.elements.SearchResultArrayAdapter;
import pickupsports2.ridgewell.pickupsports2.fragments.SearchResultsFragment;
import pickupsports2.ridgewell.pickupsports2.utilities.ServerRequest;
import ridgewell.pickupsports2.common.Event;
import ridgewell.pickupsports2.common.User;

public class SearchActivity extends ActionBarActivity {

    Spinner search_type;
    EditText input_value;

    ServerRequest svreq = ServerRequest.getInstance();

    ArrayList<User> users = null;
    ArrayList<Event> events = null;

    SearchResultsFragment myFragment = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        search_type = (Spinner) findViewById(R.id.search_type_select);
        ArrayAdapter<CharSequence> search_type_arr = ArrayAdapter.createFromResource(this,
                R.array.search_types, android.R.layout.simple_spinner_item);
        search_type.setAdapter(search_type_arr);

        input_value = (EditText) findViewById(R.id.search_bar);

        createOnSearchListener();

        myFragment = new SearchResultsFragment();

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.root_view, myFragment).commit();
    }

    public void createOnSearchListener() {
        final ImageButton search_button = (ImageButton) findViewById(R.id.search_button);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (search_type.getSelectedItem().equals("Event")) {
                        events = (ArrayList) svreq.searchEventByName(input_value.getText().toString());
                        myFragment.setEventsList(events);
                    } else if (search_type.getSelectedItem().equals("User")) {
                        users = (ArrayList) svreq.searchUserByName(input_value.getText().toString());
                        myFragment.setUsersList(users);
                    }
                } catch (NullPointerException e) {
                    Log.e("Search Error","Didn't return a useable array from Backend");
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "An error occured during you search", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
