package pickupsports2.ridgewell.pickupsports2.elements;

/**
 * Created by cameronridgewell on 3/18/15.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang.WordUtils;

import java.util.ArrayList;
import java.util.List;

import pickupsports2.ridgewell.pickupsports2.R;
import pickupsports2.ridgewell.pickupsports2.utilities.ServerRequest;
import pickupsports2.ridgewell.pickupsports2.utilities.TextValidator;
import pickupsports2.ridgewell.pickupsports2.utilities.UserData;
import ridgewell.pickupsports2.common.Event;
import ridgewell.pickupsports2.common.LocationProperties;
import ridgewell.pickupsports2.common.User;

public class AttendedEventsDialog extends DialogFragment {

    private User invitee = null;
    private User inviter = null;
    private ArrayList<Event> invited_events = new ArrayList<>();
    private ServerRequest svreq = ServerRequest.getInstance();
    private ArrayList<Event> events = new ArrayList<>();

    private InvitationEventsArrayAdapter eventsArrayAdapter = null;

    public AttendedEventsDialog() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_attended_events, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view)
                .setPositiveButton("Invite", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });

        inviter = UserData.getInstance().getThisUser(getActivity());
        invitee = getArguments().getParcelable("invitee");

        List<String> event_strings = inviter.getAttendedEvents();

        for (String event_id : event_strings) {
            events.add(svreq.getEvent(event_id));
        }

        eventsArrayAdapter = new InvitationEventsArrayAdapter(getActivity(), events);
        ListView myList = (ListView) view.findViewById(R.id.event_list);
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                eventsArrayAdapter.toggleCheck(position);
            }
        });
        myList.setAdapter(eventsArrayAdapter);

        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        AlertDialog d = (AlertDialog)getDialog();
        if(d != null)
        {
            Button positiveButton = (Button) d.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    for (int i : eventsArrayAdapter.getCheckedPositions()) {
                        invited_events.add(events.get(i));
                    }
                    if (invited_events.size() > 0) {
                        AlertDialog.Builder confirmation = new AlertDialog.Builder(getActivity());
                        confirmation.setMessage("Send Invitation to " + invitee.getFirstname() + "?")
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        invited_events.clear();
                                        dialog.cancel();
                                    }
                                })
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Log.v("starting","");
                                        for (Event event : invited_events) {
                                            svreq.invite(invitee, event, inviter);
                                            Log.v("inviting " + invitee.getFirstname(), "to " + event.getName());
                                        }

                                        dialog.dismiss();
                                        dismiss();
                                    }
                                });
                        confirmation.show();
                    } else {
                        dismiss();
                    }
                }
            });
        }
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
}
