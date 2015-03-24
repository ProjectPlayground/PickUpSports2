package pickupsports2.ridgewell.pickupsports2.utilities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by cameronridgewell on 3/23/15.
 */
public abstract class ActivityOnClickListener implements View.OnClickListener {
    public Activity currentActivity = null;
    private Bundle args = null;

    public ActivityOnClickListener(Activity activity, Bundle bundle) {
        this.currentActivity = activity;
        this.args = bundle;
    }

    public abstract void onClick(View v);

    public Activity getActivity() {
        return this.currentActivity;
    }

    public Bundle getArguments() {
        return this.args;
    }
}
