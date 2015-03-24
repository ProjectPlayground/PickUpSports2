package pickupsports2.ridgewell.pickupsports2.utilities;

import android.app.Activity;
import android.util.Log;

import java.io.FileInputStream;

import pickupsports2.ridgewell.pickupsports2.R;
import pickupsports2.ridgewell.pickupsports2.activities.MainActivity;
import ridgewell.pickupsports2.common.User;

/**
 * Created by cameronridgewell on 3/23/15.
 */
public class UserData {
    public static UserData instance = null;
    private User this_user = null;
    private static ServerRequest svreq = ServerRequest.getInstance();
    private Activity last = null;

    private UserData() {
    }

    public static UserData getInstance() {
        if (instance == null) {
            return new UserData();
        } else {
            return instance;
        }
    }

    public User getThisUser(Activity context) {
        String user_id = "";
        try {
            FileInputStream fis = context.openFileInput(
                    context.getResources().getString(R.string.user_storage_file));
            int ch;
            StringBuffer fileContent = new StringBuffer("");
            while ((ch = fis.read()) != -1) {
                fileContent.append((char) ch);
            }
            user_id = fileContent.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (user_id.equals("")) {
            Log.e("Storage Error", "User id could not be read from internal storage");
        }
        this_user = svreq.getUser(user_id);
        return this_user;
    }
}
