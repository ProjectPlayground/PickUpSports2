package pickupsports2.ridgewell.pickupsports2.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import pickupsports2.ridgewell.pickupsports2.R;
import pickupsports2.ridgewell.pickupsports2.elements.SearchResultArrayAdapter;
import pickupsports2.ridgewell.pickupsports2.intents.IntentProtocol;
import ridgewell.pickupsports2.common.Event;
import ridgewell.pickupsports2.common.User;

/**
 * Created by cameronridgewell on 4/7/15.
 */
public class SearchResultsFragment extends ListFragment {

    private List<String[]> titles = new ArrayList<>();
    private List<User> users = null;
    private List<Event> events = null;
    private String list_type;

    private SearchResultArrayAdapter searchResults;

    View rootView;

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        searchResults = new SearchResultArrayAdapter(this.getActivity(), titles);
        this.setListAdapter(searchResults);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_list_view, container, false);

        return rootView;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (list_type.equals("Event")) {
            Event toOpen = events.get(position);
            IntentProtocol.viewEvent(getActivity(), toOpen);
        } else if (list_type.equals("User")) {
            User toOpen = users.get(position);
            IntentProtocol.viewUser(getActivity(), toOpen);
        }
    }

    public void setUsersList(List<User> result_users) {
        list_type = "User";
        titles.clear();
        events = null;
        users = result_users;
        for (User user : users) {
            Log.v(user.getFirstname(),user.getLocationProperties().toString());
            String[] item = {user.getFirstname() + " " + user.getLastname(),
                    user.getLocationProperties().toString()};
            titles.add(item);
        }
        searchResults.notifyDataSetChanged();
    }

    public void setEventsList(List<Event> result_events) {
        list_type = "Event";
        titles.clear();
        users = null;
        events = result_events;
        for (Event event : events) {
            String[] item = {event.getName(), event.getLocation().toString()};
            titles.add(item);
        }
        searchResults.notifyDataSetChanged();
    }
}