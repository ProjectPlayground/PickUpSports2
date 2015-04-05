package pickupsports2.ridgewell.pickupsports2.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import pickupsports2.ridgewell.pickupsports2.R;
import pickupsports2.ridgewell.pickupsports2.elements.EditUserDialog;
import pickupsports2.ridgewell.pickupsports2.utilities.ServerRequest;

public class SearchActivity extends ActionBarActivity {

    Spinner search_type;
    EditText input_value;

    ServerRequest svreq = ServerRequest.getInstance();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        search_type = (Spinner) findViewById(R.id.search_type_select);
        ArrayAdapter<CharSequence> search_type_arr = ArrayAdapter.createFromResource(this,
                R.array.search_types, android.R.layout.simple_spinner_item);
        search_type.setAdapter(search_type_arr);

        input_value = (EditText) findViewById(R.id.search_bar);

        createOnSearchListener();

    }

    public void createOnSearchListener() {
        final ImageButton search_button = (ImageButton) findViewById(R.id.search_button);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (search_type.getSelectedItem().equals("Event")) {
                    svreq.searchEventByName(input_value.getText().toString());
                } else if (search_type.getSelectedItem().equals("User")) {
                    svreq.searchUserByName(input_value.getText().toString());
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
