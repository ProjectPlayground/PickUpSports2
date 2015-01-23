package pickupsports2.ridgewell.pickupsports2;

import android.app.Activity;
import android.content.Context;
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

    public View_Event_Screen(Context context, final Event event_) {
        this.myContext = context;
        this.event= event_;
        this.setContentView(view_event_screen);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_event_screen);
    }
}
