package pickupsports2.ridgewell.pickupsports2;

import java.util.ArrayList;
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
public class Main_View_ArrayAdapter extends ArrayAdapter<Event> {
    private ArrayList<Event> list = new ArrayList<Event>();
    private Context context;

    public Main_View_ArrayAdapter(Context context_, ArrayList<Event> list_) {
        super(context_, R.layout.event_view_item, list_);
        this.list = list_;
        this.context = context_;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public long getItemId(int pos) {
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.event_view_item, null);
        }

        //Handle TextView and display string from your list
        TextView listItemTextTitle = (TextView)view.findViewById(R.id.event_view_adapter_title);
        listItemTextTitle.setText(list.get(position).getName());

        TextView listItemTextSport = (TextView)view.findViewById(R.id.event_view_adapter_sport);
        listItemTextSport.setText(list.get(position).getSport().getSportName());

        TextView listItemTextLocation = (TextView)view.findViewById(R.id.event_view_adapter_location);
        listItemTextLocation.setText(list.get(position).getLocation().getLocation());

        TextView listItemTextDate = (TextView)view.findViewById(R.id.event_view_adapter_date);
        listItemTextDate.setText(list.get(position).getTime().toString());



        //Handle buttons and add onClickListeners
        Button view_button = (Button)view.findViewById(R.id.view_button);

        view_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                notifyDataSetChanged();
            }
        });

        return view;
    }
}
