package pickupsports2.ridgewell.pickupsports2.utilities;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by cameronridgewell on 3/18/15.
 */
public abstract class TextValidator implements TextWatcher {
    private final TextView textView;

    private String lastText = "";

    public TextValidator(TextView textView) {
        this.textView = textView;
    }

    public abstract void validate(TextView textView, String text);

    @Override
    final public void afterTextChanged(Editable s) {
        if (!textView.getText().toString().equals(lastText)) {
            lastText = textView.getText().toString();
            validate(textView, lastText);
        }
    }

    @Override
    final public void beforeTextChanged(CharSequence s, int start, int count, int after) { /* Don't care */ }

    @Override
    final public void onTextChanged(CharSequence s, int start, int before, int count) { /* Don't care */ }
}
