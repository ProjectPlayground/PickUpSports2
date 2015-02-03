package pickupsports2.ridgewell.pickupsports2;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import ridgewell.pickupsports2.common.Event;

/**
 * Created by cameronridgewell on 1/21/15.
 */
public class SportingEventArrayAdapter extends ArrayAdapter<Event> {
    private List<Event> list = new ArrayList<Event>();
    private Context context;

    public SportingEventArrayAdapter(Context context, List<Event> list) {
        super(context, R.layout.event_view_item, list);
        this.list = list;
        this.context = context;
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
            view = inflater.inflate(R.layout.event_view_item, null);
        }

        Event event = this.list.get(position);

        //Handle TextView and display string from your list
        TextView listItemTextTitle = (TextView)view.findViewById(R.id.event_view_adapter_title);
        listItemTextTitle.setText(event.getName());

        TextView listItemTextSport = (TextView)view.findViewById(R.id.event_view_adapter_sport);
        listItemTextSport.setText(event.getSport().getSportName());

        TextView listItemTextLocation = (TextView)view.findViewById(R.id.event_view_adapter_location);
        listItemTextLocation.setText(event.getLocation().getLocation());

        TextView listItemTextDate = (TextView)view.findViewById(R.id.event_view_adapter_date);
        listItemTextDate.setText(event.getTime().toString());


        //Handle buttons and add onClickListeners
        Button viewButton = (Button)view.findViewById(R.id.attendButton);

        viewButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                notifyDataSetChanged();
            }
        });

        return view;
    }
}
