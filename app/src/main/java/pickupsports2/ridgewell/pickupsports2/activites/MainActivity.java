package pickupsports2.ridgewell.pickupsports2.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;

import pickupsports2.ridgewell.pickupsports2.R;

public class MainActivity extends ActionBarActivity {
    // Create, automatically open (if applicable), save, and restore the
    // Active Session in a way that is similar to Android UI lifecycles.
    private UiLifecycleHelper uiHelper;
    private View otherView;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Set View that should be visible after log-in invisible initially
        otherView = (View) findViewById(R.id.other_views);
        otherView.setVisibility(View.GONE);
        // To maintain FB Login session
        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);
    }

    // Called when session changes
    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state,
                         Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };

    // When session is changed, this method is called from callback method
    private void onSessionStateChange(Session session, SessionState state,
                                      Exception exception) {
        final TextView name = (TextView) findViewById(R.id.name);
        final TextView gender = (TextView) findViewById(R.id.gender);
        final TextView location = (TextView) findViewById(R.id.location);
        // When Session is successfully opened (User logged-in)
        if (state.isOpened()) {
            Log.i(TAG, "Logged in...");
            // make request to the /me API to get Graph user
            Request.newMeRequest(session, new Request.GraphUserCallback() {

                // callback after Graph API response with user
                // object
                @Override
                public void onCompleted(GraphUser user, Response response) {
                    if (user != null) {
                        // Set view visibility to true
                        otherView.setVisibility(View.VISIBLE);
                        // Set User name
                        name.setText("Hello " + user.getName());
                        // Set Gender
                        gender.setText("Your Gender: "
                                + user.getProperty("gender").toString());
                        location.setText("Your Current Location: "
                                + user.getLocation().getProperty("name")
                                .toString());
                    }
                }
            }).executeAsync();
        } else if (state.isClosed()) {
            Log.i(TAG, "Logged out...");
            otherView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        uiHelper.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "OnActivityResult...");
    }

    @Override
    public void onResume() {
        super.onResume();
        uiHelper.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }
}