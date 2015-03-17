package pickupsports2.ridgewell.pickupsports2.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import pickupsports2.ridgewell.pickupsports2.R;
import pickupsports2.ridgewell.pickupsports2.intents.IntentProtocol;

public class LoginActivity extends Activity {
    // Create, automatically open (if applicable), save, and restore the
    // Active Session in a way that is similar to Android UI lifecycles.
    private UiLifecycleHelper uiHelper;
    private View otherView;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
                        String id = user.getId();
                        //finds user id and stores it to internal storage
                        try {
                            FileOutputStream fos = openFileOutput(
                                            getResources().getString(R.string.user_storage_file),
                                            Context.MODE_PRIVATE);
                            fos.write(id.getBytes());
                            fos.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            FileInputStream fis = openFileInput(
                                    getResources().getString(R.string.user_storage_file));
                            int ch;
                            StringBuffer fileContent = new StringBuffer("");
                            while( (ch = fis.read()) != -1) {
                                fileContent.append((char) ch);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        IntentProtocol.launchMain(LoginActivity.this);
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