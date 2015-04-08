package pickupsports2.ridgewell.pickupsports2.elements;

/**
 * Created by cameronridgewell on 4/7/15.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pickupsports2.ridgewell.pickupsports2.R;
import pickupsports2.ridgewell.pickupsports2.utilities.ServerRequest;
import pickupsports2.ridgewell.pickupsports2.utilities.UserData;
import ridgewell.pickupsports2.common.Event;
import ridgewell.pickupsports2.common.User;

public class SearchResultArrayAdapter extends ArrayAdapter<String[]> {
    private List<String[]> list = new ArrayList<String[]>();
    private Activity context;
    private ServerRequest svreq = ServerRequest.getInstance();
    private Event event = null;
    private User user = null;
    private int position;

    public SearchResultArrayAdapter(Activity context, List<String[]> search_result_list) {
        super(context, R.layout.search_result_list_item, search_result_list);
        this.position = position;
        this.list = search_result_list;
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
            view = inflater.inflate(R.layout.search_result_list_item, null);
        }

        TextView name = (TextView) view.findViewById(R.id.search_adapter_title);
        name.setText(this.list.get(position)[0]);

        TextView location = (TextView) view.findViewById(R.id.search_adapter_location);
        location.setText(this.list.get(position)[1]);

        return view;
    }
}
