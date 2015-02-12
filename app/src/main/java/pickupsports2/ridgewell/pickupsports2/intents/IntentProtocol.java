package pickupsports2.ridgewell.pickupsports2.intents;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import pickupsports2.ridgewell.pickupsports2.activites.MainActivity;
import pickupsports2.ridgewell.pickupsports2.activites.ViewEventActivity;
import pickupsports2.ridgewell.pickupsports2.activites.ViewUserActivity;
import ridgewell.pickupsports2.common.Event;
import ridgewell.pickupsports2.common.User;

/**
 * Created by jules on 2/2/15.
 */
public class IntentProtocol {

    public static final String VIEWABLE_EVENT = "viewable_event";
    public static final String USER_VIEW = "user_view";
    public static final String CREATED_EVENT = "created_event";

    public static void viewEvent(Activity context, Event event){
        Intent intent = new Intent(context, ViewEventActivity.class);
        intent.putExtra(VIEWABLE_EVENT, event);
        context.startActivity(intent);
    }

    public static Event getEvent(Activity context){
        Intent intent = context.getIntent();
        Event event = intent.getExtras().getParcelable(VIEWABLE_EVENT);
        return event;
    }

    public static void createEvent(Activity context, Event event) {
        //TODO make this assignment somewhere more global
        int SUCCESS_CODE = 1;
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

}
