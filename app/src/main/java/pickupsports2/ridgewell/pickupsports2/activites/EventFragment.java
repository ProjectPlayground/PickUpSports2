package pickupsports2.ridgewell.pickupsports2.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import pickupsports2.ridgewell.pickupsports2.utilities.ServerRequest;
import pickupsports2.ridgewell.pickupsports2.utilities.SwipeRefreshListFragment;
import pickupsports2.ridgewell.pickupsports2.intents.IntentProtocol;
import ridgewell.pickupsports2.common.Event;

/**
 * Created by cameronridgewell on 2/9/15.
 */
public class EventFragment extends SwipeRefreshListFragment {

    final int CREATE_EVENT_CODE = 1;
    final int SUCCESS_CODE = 1;

    private List<Event> events = new ArrayList<Event>();

    private SportingEventArrayAdapter sportingEventArrayAdapter;
    private ServerRequest svreq = new ServerRequest();

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        sportingEventArrayAdapter = new SportingEventArrayAdapter(this.getActivity(), events);
        this.setListAdapter(sportingEventArrayAdapter);

        refreshEvents();

        final SwipeRefreshLayout swipeRefresh = this.getmSwipeRefreshLayout();
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshEvents();
                swipeRefresh.setRefreshing(false);
            }
        });
        this.setmSwipeRefreshLayout(swipeRefresh);
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
                refreshEvents();
            } else {
                Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                        "An error occurred while creating your event", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    public void refreshEvents() {
        try {
            Log.v("Attempting", "Event Refresh");
            events = svreq.getAllEvents();
            sportingEventArrayAdapter.refreshItems(events);
            Log.v("Completed","EventRefresh");
        } catch (InterruptedException e) {
            Log.e("Interrupted Exception", e.getMessage());
            e.printStackTrace();
        } catch (ExecutionException e) {
            Log.e("Execution Exception", e.getMessage());
            e.printStackTrace();
        }
    }
}