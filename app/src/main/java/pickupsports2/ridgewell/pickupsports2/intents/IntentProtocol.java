package pickupsports2.ridgewell.pickupsports2.intents;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import pickupsports2.ridgewell.pickupsports2.R;
import pickupsports2.ridgewell.pickupsports2.activities.CreateEventActivity;
import pickupsports2.ridgewell.pickupsports2.activities.LocationPickerActivity;
import pickupsports2.ridgewell.pickupsports2.activities.LoginActivity;
import pickupsports2.ridgewell.pickupsports2.activities.MainActivity;
import pickupsports2.ridgewell.pickupsports2.activities.ViewEventActivity;
import pickupsports2.ridgewell.pickupsports2.activities.ViewUserActivity;
import pickupsports2.ridgewell.pickupsports2.utilities.ServerRequest;
import ridgewell.pickupsports2.common.Event;
import ridgewell.pickupsports2.common.LocationProperties;
import ridgewell.pickupsports2.common.User;

/**
 * Created by jules on 2/2/15.
 */
public class IntentProtocol {
    public static final ServerRequest svreq = ServerRequest.getInstance();

    public static final String VIEWABLE_EVENT = "viewable_event";
    public static final String USER_VIEW = "user_view";
    public static final String CREATED_EVENT = "created_event";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE= "longitude";
    public static final String LOCATION_NAME = "location_name";


    public static final int SUCCESS_CODE = 1;

    public static final int CREATE_EVENT_CODE = 100;
    public static final int LOCATION_PICKER_CODE = 101;

    public static void launchLogin(Activity context){
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public static void launchMain(Activity context){
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    public static void viewEvent(Activity context, Event event){
        Intent intent = new Intent(context, ViewEventActivity.class);
        intent.putExtra(VIEWABLE_EVENT, event);
        context.startActivity(intent);
    }

    public static Event getEvent(Activity context){
        Bundle extras = context.getIntent().getExtras();
        Event event = extras.getParcelable(VIEWABLE_EVENT);
        return event;
    }

    public static void createEvent(Activity context, Event event) {
        Intent intent_return = new Intent(context, MainActivity.class);
        intent_return.putExtra("created_event", event);
        context.setResult(SUCCESS_CODE, intent_return);
    }

    public static void viewUser(Activity context, User user){
        Intent intent = new Intent(context, ViewUserActivity.class);
        intent.putExtra(USER_VIEW, user);
        context.startActivity(intent);
    }

    public static User getUser(Activity context){
        Bundle extras = context.getIntent().getExtras();
        User user = extras.getParcelable(USER_VIEW);
        return user;
    }

    public static void launchCreateEvent(Activity context) {
        Intent launch_new_event = new Intent(context, CreateEventActivity.class);
        context.startActivityForResult(launch_new_event, CREATE_EVENT_CODE);
    }

    public static void launchLocationPicker(Activity context, String initial_location) {
        Intent launch_location_picker = new Intent(context, LocationPickerActivity.class);
        launch_location_picker.putExtra("initial_location",initial_location);
        context.startActivityForResult(launch_location_picker, LOCATION_PICKER_CODE);
    }

    public static void returnLocationPicker(Activity context, Class returnTo, float latitude,
                                            float longitude, String locationName) {
        Intent intent_return = new Intent(context, returnTo);
        LocationProperties loc1 = new LocationProperties(locationName);
        loc1.setLongitude(latitude);
        loc1.setLongitude(longitude);
        intent_return.putExtra("location_properties", loc1);
        context.setResult(Activity.RESULT_OK, intent_return);
    }

    public static LocationProperties getLocationPickerResult(int requestCode, int resultCode,
                                                             Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == LOCATION_PICKER_CODE) {
            return data.getExtras().getParcelable("location_properties");
        }
        return null;
    }
}
