package pickupsports2.ridgewell.pickupsports2.elements;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import pickupsports2.ridgewell.pickupsports2.R;
import pickupsports2.ridgewell.pickupsports2.utilities.ActivityOnClickListener;
import pickupsports2.ridgewell.pickupsports2.utilities.ServerRequest;
import pickupsports2.ridgewell.pickupsports2.utilities.UserData;
import ridgewell.pickupsports2.common.Event;
import ridgewell.pickupsports2.common.User;

/**
 * Created by cameronridgewell on 1/21/15.
 */
public class SportingEventArrayAdapter extends ArrayAdapter<Event> {
    private List<Event> list = new ArrayList<Event>();
    private Activity context;
    private ServerRequest svreq = ServerRequest.getInstance();
    private Event event = null;
    private User user = null;
    private int position;

    public SportingEventArrayAdapter(Activity context, List<Event> event_list) {
        super(context, R.layout.event_list_item, event_list);
        this.position = position;
        this.list = event_list;
        this.context = context;
        this.user = UserData.getInstance().getThisUser(context);
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.event_list_item, null);
        }

        this.event = this.list.get(position);

        //Handle TextView and display string from your list
        TextView listItemTextTitle = (TextView)view.findViewById(R.id.event_view_adapter_title);
        listItemTextTitle.setText(event.getName());

        TextView listItemTextSport = (TextView)view.findViewById(R.id.event_view_adapter_sport);
        listItemTextSport.setText(event.getSport());

        TextView listItemTextLocation = (TextView)view.findViewById(R.id.event_view_adapter_location);
        listItemTextLocation.setText(event.getLocation().toString());

        TextView listItemTextDate = (TextView)view.findViewById(R.id.event_view_adapter_date);
        listItemTextDate.setText(event.getTime().toString("MMMM d, yyyy"));

        //Handle buttons and add onClickListeners
        Button attendButton = (Button)view.findViewById(R.id.attendButton);
        Bundle bundle = new Bundle();
        bundle.putParcelable("user", this.user);
        bundle.putParcelable("event",this.event);

        if (!list.get(position).getAttendees().contains(this.user.get_id())) {
            attendButton.setText(context.getResources().getString(R.string.attendButton));
            attendButton.setOnClickListener(new ActivityOnClickListener(context, bundle){
                public void onClick(View v) {
                    User user = getArguments().getParcelable("user");
                    Event event = getArguments().getParcelable("event");
                    Log.v("User", user.get_id() + " Attends Event: " + event.get_id());
                    Log.v(user.get_id(), event.getAttendees().toString());
                    svreq.attendEvent(event, user);
                    event.addAttendee(user);
                    user.addEvent(event);
                    notifyDataSetChanged();
                }
            });
        } else {
            attendButton.setText(context.getResources().getString(R.string.leaveButton));
            attendButton.setOnClickListener(new ActivityOnClickListener(context, bundle){
                public void onClick(View v) {
                    User user = getArguments().getParcelable("user");
                    Event event = getArguments().getParcelable("event");
                    Log.v("User", user.get_id() + " Leaves Event: " + event.get_id());
                    svreq.leaveEvent(event, user);
                    event.removeAttendee(user);
                    user.removeEvent(event);
                    notifyDataSetChanged();
                }
            });
        }
        return view;
    }

    public void refreshItems(List<Event> events) {
        this.list = events;
        notifyDataSetChanged();
    }
}
