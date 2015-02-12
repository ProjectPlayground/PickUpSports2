package pickupsports2.ridgewell.pickupsports2;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.ExecutionException;

import pickupsports2.ridgewell.pickupsports2.intents.IntentProtocol;
import ridgewell.pickupsports2.common.Event;

/**
 * Created by cameronridgewell on 2/9/15.
 */
public class EventFragment extends SwipeRefreshListFragment {

    final int CREATE_EVENT_CODE = 1;
    final int SUCCESS_CODE = 1;

    private List<Event> events;

    private SportingEventArrayAdapter sportingEventArrayAdapter;
    private ServerRequest svreq = new ServerRequest();

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            events = svreq.getAllEvents();
        } catch (InterruptedException e) {
            Log.e("Interrupted Exception", e.getMessage());
            e.printStackTrace();
        } catch (ExecutionException e) {
            Log.e("Execution Exception", e.getMessage());
            e.printStackTrace();
        }

        sportingEventArrayAdapter = new SportingEventArrayAdapter(this.getActivity(), events);

        this.setListAdapter(sportingEventArrayAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Event toOpen = events.get(position);
        IntentProtocol.viewEvent(this.getActivity(), toOpen);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CREATE_EVENT_CODE) {
            if (resultCode == SUCCESS_CODE) {
                try {
                    Log.v("Fetching All Events", "All Events Fetched");
                    this.events = svreq.getAllEvents();
                    //TODO figure out why data set is not updating when a new event is created
                    sportingEventArrayAdapter.notifyDataSetChanged();
                } catch (InterruptedException e1) {
                    Log.e("Server interrupt", e1.getMessage());
                    e1.printStackTrace();
                } catch (ExecutionException e2) {
                    Log.e("Execution Exception", e2.getMessage());
                    e2.printStackTrace();
                }
            } else {
                Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                        "An error occurred while creating your event", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    public void setEvents (List<Event> events) {
        this.events = events;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void addEvent(Event e) {
        events.add(e);
    }

    public SportingEventArrayAdapter getSportingEventArrayAdapter() {
        return sportingEventArrayAdapter;
    }
}