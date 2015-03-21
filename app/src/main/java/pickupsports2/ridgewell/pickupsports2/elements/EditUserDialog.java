package pickupsports2.ridgewell.pickupsports2.elements;

/**
 * Created by cameronridgewell on 3/18/15.
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.DialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang.WordUtils;

import pickupsports2.ridgewell.pickupsports2.R;
import pickupsports2.ridgewell.pickupsports2.utilities.TextValidator;
import ridgewell.pickupsports2.common.Location;
import ridgewell.pickupsports2.common.User;

public class EditUserDialog extends DialogFragment {
    private OnEditUserListener callback;

    User inputUser = null;

    private EditText firstname;
    private EditText lastname;
    private EditText cityname;
    private EditText statename;

    public EditUserDialog() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.edit_user_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        inputUser = null;
                        callback.onEditUserListener(inputUser);
                        dismiss();
                    }
                });

        inputUser = getArguments().getParcelable("user");

        firstname = (EditText) view.findViewById(R.id.firstNameText);
        firstname.setText(inputUser.getFirstname());

        lastname = (EditText) view.findViewById(R.id.lastNameText);
        lastname.setText(inputUser.getLastname());

        cityname = (EditText) view.findViewById(R.id.cityText);
        cityname.addTextChangedListener(new TextValidator(cityname) {
            @Override
            public void validate(TextView textview, String text) {
                String outputText = "";
                int removedcount = 0;
                for (int i = 0; i < text.length(); ++i) {
                    if (Character.isLetter(text.charAt(i))
                            || Character.isSpaceChar(text.charAt(i))
                            || text.charAt(i) == '-'
                            || text.charAt(i) == '.') {
                        outputText += text.charAt(i);
                    } else {
                        removedcount++;
                    }
                }
                int position = text.length() - removedcount;
                textview.setText(outputText);
                cityname.setSelection(position);
            }
        });

        statename = (EditText) view.findViewById(R.id.stateText);
        statename.addTextChangedListener(new TextValidator(statename) {
            @Override
            public void validate(TextView textview, String text) {
                String outputText = "";
                int word_length = text.length() < 2 ? text.length() : 2;
                int removedcount = 0;
                for (int i = 0; i < text.length(); ++i) {
                    if (Character.isLetter(text.charAt(i))) {
                        outputText += Character.toUpperCase(text.charAt(i));
                    } else {
                        removedcount++;
                    }
                }
                int position = text.length() - removedcount;
                textview.setText(outputText);
                statename.setSelection(position);
            }
        });

        String userLocation = inputUser.getLocation().getLocation();
        if (! userLocation.equals("")) {
            String[] citystate = userLocation.split(", ");
            cityname.setText(citystate[0]);
            statename.setText(citystate[1]);
        }
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
                    if (firstname.getText().toString().trim().equals("")
                            || lastname.getText().toString().trim().equals("")
                            || cityname.getText().toString().trim().equals("")
                            || statename.getText().toString().trim().equals(""))
                    {
                        Toast unfilled_field = Toast.makeText(getActivity().getApplicationContext(),
                            "All fields are required", Toast.LENGTH_SHORT);
                        unfilled_field.show();
                    } else {
                        char[] delim = {' ', '-'};
                        inputUser.setFirstname(WordUtils.capitalize(firstname.getText()
                                .toString().trim(), delim));
                        inputUser.setLastname(WordUtils.capitalize(lastname.getText()
                                .toString().trim(), delim));
                        inputUser.setLocation(new Location(WordUtils.capitalize(cityname.getText()
                                .toString().trim(), delim) + ", "
                                + statename.getText().toString().trim()));
                        callback.onEditUserListener(inputUser);
                        dismiss();
                    }
                }
            });
        }
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.callback = (OnEditUserListener)activity;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnEditUserListener");
        }
    }

    public interface OnEditUserListener {
        public void onEditUserListener(User user);
    }
}
