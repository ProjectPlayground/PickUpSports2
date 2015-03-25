package pickupsports2.ridgewell.pickupsports2.elements;

/**
 * Created by cameronridgewell on 3/18/15.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang.WordUtils;
import org.joda.time.MutableDateTime;

import java.lang.reflect.Array;
import java.util.Arrays;

import pickupsports2.ridgewell.pickupsports2.R;
import pickupsports2.ridgewell.pickupsports2.intents.IntentProtocol;
import pickupsports2.ridgewell.pickupsports2.utilities.TextValidator;
import pickupsports2.ridgewell.pickupsports2.utilities.UserData;
import ridgewell.pickupsports2.common.Event;
import ridgewell.pickupsports2.common.LocationProperties;
import ridgewell.pickupsports2.common.User;

public class EditEventDialog extends DialogFragment {
    private OnEditEventListener callback;

    private Event inputEvent = null;

    private Button post;
    private EditText event_name;
    private TextView displayDate;
    private TextView displayTime;
    private Button editDate;
    private Button editTime;
    private Spinner sportsSpinner;
    private Spinner costSpinner;
    private Spinner privacySpinner;
    private EditText location;
    private EditText maxAttendance;
    private EditText notes;

    public EditEventDialog() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.edit_event_dialog, null);

        inputEvent = getArguments().getParcelable("event");

        event_name = (EditText) view.findViewById(R.id.eventNameInput);
        event_name.setText(inputEvent.getName());

        sportsSpinner = (Spinner) view.findViewById(R.id.sport_spinner);
        ArrayAdapter<CharSequence> sportsAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.sports, android.R.layout.simple_spinner_item);
        sportsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sportsSpinner.setAdapter(sportsAdapter);
        sportsSpinner.setSelection(Arrays.asList(getResources().getStringArray(R.array.sports))
                .indexOf(inputEvent.getSport()));

        costSpinner = (Spinner) view.findViewById(R.id.cost_spinner);
        ArrayAdapter<CharSequence> costAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.cost_options, android.R.layout.simple_spinner_item);
        costAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        costSpinner.setAdapter(costAdapter);
        costSpinner.setSelection(inputEvent.getCost());

        privacySpinner = (Spinner) view.findViewById(R.id.privacy_spinner);
        ArrayAdapter<CharSequence> privacyAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.privacy_options, android.R.layout.simple_spinner_item);
        privacyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        privacySpinner.setAdapter(privacyAdapter);
        privacySpinner.setSelection(inputEvent.isPublic() ? 1 : 0);

        //location = (EditText) findViewById(R.id.eventLocationInput);

        maxAttendance = (EditText) view.findViewById(R.id.eventAttendanceInput);
        maxAttendance.setText(inputEvent.getMaxAttendance() + "");

        displayDate = (TextView) view.findViewById(R.id.selected_date);
        displayDate.setText(inputEvent.getTime().toString("MMMM d, yyyy"));

        displayTime = (TextView) view.findViewById(R.id.selected_time);
        displayTime.setText(inputEvent.getTime().toString("h:mm a"));

        notes = (EditText) view.findViewById(R.id.eventNotesInput);
        notes.setText(inputEvent.getNotes());

        post = (Button) view.findViewById(R.id.create_event_post_button);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}})
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.onEditEventListener(inputEvent);
                        dismiss();
                    }
                });

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
                public void onClick(View v) {
                    try {
                        if (inputEvent.getLocation() == null) {
                            throw new NullPointerException("Cannot have an event without location");
                        }
                        inputEvent.setName(event_name.getText().toString());
                        inputEvent.setSport(sportsSpinner.getSelectedItem().toString());
                        //inputEvent.setLocation();
                        inputEvent.setCost(costSpinner.getSelectedItemPosition());
                        inputEvent.setNotes(notes.getText().toString());
                        inputEvent.setPublic(privacySpinner.getSelectedItemPosition() == 1);
                        inputEvent.setMaxAttendance(Integer
                                .parseInt(maxAttendance.getText().toString()));
                        callback.onEditEventListener(inputEvent);
                        dismiss();
                    } catch (Exception e) {
                        Toast toast = Toast.makeText(getActivity(),
                                "One or more of your fields is incomplete", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            });
        }
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.callback = (OnEditEventListener)activity;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnEditUserListener");
        }
    }

    public interface OnEditEventListener {
        public void onEditEventListener(Event event);
    }
}
