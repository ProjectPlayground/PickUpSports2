package pickupsports2.ridgewell.pickupsports2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import ridgewell.pickupsports2.common.*;

import static pickupsports2.ridgewell.pickupsports2.R.layout.view_event_screen;

/**
 * Created by cameronridgewell on 1/22/15.
 */
public class View_Event_Screen extends Activity {
    Event event;
    Context myContext;

    public View_Event_Screen() {}

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_event_screen);

        Intent intent = getIntent();
        event = (Event) intent.getSerializableExtra("viewable_event");


    }
}
