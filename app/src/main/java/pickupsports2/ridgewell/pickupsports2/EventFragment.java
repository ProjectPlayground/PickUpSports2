package pickupsports2.ridgewell.pickupsports2;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import org.joda.time.DateTime;

import java.util.List;

import pickupsports2.ridgewell.pickupsports2.data.DummyEventSource;
import pickupsports2.ridgewell.pickupsports2.data.EventSource;
import pickupsports2.ridgewell.pickupsports2.intents.IntentProtocol;
import ridgewell.pickupsports2.common.Event;

/**
 * Created by cameronridgewell on 2/9/15.
 */
public class EventFragment extends ListFragment {

    final int CREATE_EVENT_CODE = 1;
    final int SUCCESS_CODE = 1;

    private List<Event> events;

    private SportingEventArrayAdapter sportingEventArrayAdapter;

    private final EventSource eventSource = new DummyEventSource();

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DateTime today = DateTime.now();
        events = this.eventSource.getEventsInDateRange(today, DateTime.now().plusDays(9));

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
        System.out.println("here");
        if (requestCode == CREATE_EVENT_CODE) {
            if (resultCode == SUCCESS_CODE) {
                //TODO push to server
                events.add(IntentProtocol.getEvent(this.getActivity()));
                sportingEventArrayAdapter.notifyDataSetChanged();
            } else {
                //TODO Toast created event failed
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