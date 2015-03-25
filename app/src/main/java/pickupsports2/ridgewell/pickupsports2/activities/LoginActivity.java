package pickupsports2.ridgewell.pickupsports2.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;

import java.io.FileOutputStream;

import pickupsports2.ridgewell.pickupsports2.R;
import pickupsports2.ridgewell.pickupsports2.elements.EditUserDialog;
import pickupsports2.ridgewell.pickupsports2.intents.IntentProtocol;
import pickupsports2.ridgewell.pickupsports2.utilities.ServerRequest;
import ridgewell.pickupsports2.common.LocationProperties;
import ridgewell.pickupsports2.common.User;

public class LoginActivity extends FragmentActivity implements EditUserDialog.OnEditUserListener{
    // Create, automatically open (if applicable), save, and restore the
    // Active Session in a way that is similar to Android UI lifecycles.
    private UiLifecycleHelper uiHelper;
    private View otherView;
    private static final String TAG = "LoginActivity";

    private final int LOGIN_REQUEST_CODE = 555;

    private static ServerRequest svreq = ServerRequest.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
                        String fbid = user.getId();
                        User userreq = svreq.isExistingUser(fbid, "fb");

                        if (userreq == null) {
                            User user_expected = new User(user.getFirstName(),
                                    user.getLastName(), new LocationProperties(""));
                            user_expected.setFb_id(fbid);
                            showCreateDialog(user_expected);
                        } else {
                            successfulLogin(userreq);
                        }
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

    private void showCreateDialog(User user_expected) {
        EditUserDialog createUser = new EditUserDialog();
        Bundle args = new Bundle();
        args.putParcelable("user",user_expected);
        createUser.setArguments(args);
        createUser.show(getFragmentManager(),"creating user at login");
    }

    public void onEditUserListener(User user) {
        if (user == null) {
            Session.setActiveSession(null);
        } else {
            svreq.addUser(user);
            successfulLogin(user);
        }
    }

    /*
     * Writes the user's id to main memory and launches main activity
     */
    private void successfulLogin(User user) {
        try {
            FileOutputStream fos = openFileOutput(
                    getResources().getString(R.string.user_storage_file),
                    Context.MODE_PRIVATE);
            fos.write(user.get_id().getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        IntentProtocol.launchMain(LoginActivity.this);
        finish();
    }
}