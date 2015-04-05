package pickupsports2.ridgewell.pickupsports2.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pickupsports2.ridgewell.pickupsports2.R;
import pickupsports2.ridgewell.pickupsports2.activities.MainActivity;
import pickupsports2.ridgewell.pickupsports2.elements.InvitationArrayAdapter;
import pickupsports2.ridgewell.pickupsports2.elements.SportingEventArrayAdapter;
import pickupsports2.ridgewell.pickupsports2.intents.IntentProtocol;
import pickupsports2.ridgewell.pickupsports2.utilities.ServerRequest;
import pickupsports2.ridgewell.pickupsports2.utilities.SwipeRefreshListFragment;
import ridgewell.pickupsports2.common.Event;
import ridgewell.pickupsports2.common.Invitation;

/**
 * Created by cameronridgewell on 2/9/15.
 */
public class InvitationsFragment extends SwipeRefreshListFragment implements MainActivity.MainActivityFragment {

    private List<Invitation> invitations = new ArrayList<Invitation>();

    private InvitationArrayAdapter invitationArrayAdapter;
    private ServerRequest svreq = ServerRequest.getInstance();

    View rootView;

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        invitations.add(new Invitation("551f7abb8e185ef01634f2df","duck","5513180950cc7ca2d0622111"));
        invitations.add(new Invitation("551f7a378e185ef01634f2de","wrong","5513180950cc7ca2d0622111"));

        invitationArrayAdapter = new InvitationArrayAdapter(this.getActivity(), invitations);
        this.setListAdapter(invitationArrayAdapter);

        final SwipeRefreshLayout swipeRefresh =
                (SwipeRefreshLayout) rootView.findViewById(R.id.event_list_fragment);

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFragment();
                swipeRefresh.setRefreshing(false);
            }
        });

        this.setmSwipeRefreshLayout(swipeRefresh);
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
    public void onResume() {
        super.onResume();
        refreshFragment();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Invitation toOpen = invitations.get(position);
        //IntentProtocol.viewInvitation(this.getActivity(), toOpen);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*
        if (requestCode == CREATE_EVENT_CODE) {
            if (resultCode == SUCCESS_CODE) {
                refreshFragment();
            } else {
                Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                        "An error occurred while creating your event", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        */
    }

    public void onActionButtonClick() {

    }

    public void refreshFragment() {
        /*
        try{

            Thread.currentThread().sleep(250);
        }catch(Exception e){}
        Log.v("Attempting", "Event Refresh");
        events = svreq.getAllEvents();
        if (events != null) {
            sportingEventArrayAdapter.refreshItems(events);
            Log.v("Completed", "EventRefresh");
        } else {
            Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                    "PickUpSports was unable to connect to server", Toast.LENGTH_SHORT);
            toast.show();
        }
        */
    }
}