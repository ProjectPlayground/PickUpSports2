package pickupsports2.ridgewell.pickupsports2.elements;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pickupsports2.ridgewell.pickupsports2.R;
import pickupsports2.ridgewell.pickupsports2.utilities.ServerRequest;
import pickupsports2.ridgewell.pickupsports2.utilities.UserData;
import ridgewell.pickupsports2.common.Event;
import ridgewell.pickupsports2.common.Invitation;
import ridgewell.pickupsports2.common.User;

/**
 * Created by cameronridgewell on 1/21/15.
 */
public class InvitationEventsArrayAdapter extends ArrayAdapter<Event> {
    private List<Event> list = new ArrayList<>();
    private Activity context;
    private Event event = null;
    private int position;
    private View view;
    private List<Integer> checked_items = new ArrayList<>();
    private CheckBox checkBox = null;
    private TextView title;

    private ArrayList<View> item_views = new ArrayList<>();

    public InvitationEventsArrayAdapter(Activity context, List<Event> events_list) {
        super(context, R.layout.event_name_check_item, events_list);
        this.list = events_list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.event_name_check_item, null);
        }
        this.event = this.list.get(position);

        title = (TextView) view.findViewById(R.id.event_adapter_title);
        title.setText(event.getName());

        checkBox = (CheckBox) view.findViewById(R.id.checkBox);
        if (checked_items.contains(position)) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }
        item_views.add(view);
        return view;
    }

    public void toggleCheck(final int position) {
        if (checked_items.contains(position)) {
            Log.v("remove",""+position);
            checked_items.remove((Integer) position);
            notifyDataSetChanged();
        } else {
            Log.v("add", "" + position);
            checked_items.add(position);
            notifyDataSetChanged();
        }
    }

    public List<Integer> getCheckedPositions() {
        return checked_items;
    }
}
