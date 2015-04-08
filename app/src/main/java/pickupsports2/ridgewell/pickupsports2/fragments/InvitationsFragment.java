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

import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.List;

import pickupsports2.ridgewell.pickupsports2.R;
import pickupsports2.ridgewell.pickupsports2.activities.MainActivity;
import pickupsports2.ridgewell.pickupsports2.elements.InvitationArrayAdapter;
import pickupsports2.ridgewell.pickupsports2.intents.IntentProtocol;
import pickupsports2.ridgewell.pickupsports2.utilities.ServerRequest;
import pickupsports2.ridgewell.pickupsports2.utilities.SwipeRefreshListFragment;
import pickupsports2.ridgewell.pickupsports2.utilities.UserData;
import ridgewell.pickupsports2.common.Event;
import ridgewell.pickupsports2.common.Invitation;
import ridgewell.pickupsports2.common.User;

/**
 * Created by cameronridgewell on 2/9/15.
 */
public class InvitationsFragment extends SwipeRefreshListFragment implements MainActivity.MainActivityFragment {

    private List<Invitation> invitations = new ArrayList<Invitation>();

    private InvitationArrayAdapter invitationArrayAdapter;
    private ServerRequest svreq = ServerRequest.getInstance();
    private User thisuser = null;
    View rootView;

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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

        thisuser = UserData.getInstance().getThisUser(getActivity());

        invitations = svreq.getUserInvitations(thisuser);

        invitationArrayAdapter = new InvitationArrayAdapter(this.getActivity(), invitations);
        this.setListAdapter(invitationArrayAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_list_view_refresh, container, false);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshFragment();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Event toOpen = svreq.getEvent(invitations.get(position).getEvent_id());
        IntentProtocol.viewEvent(this.getActivity(), toOpen);
    }

    public void onActionButtonClick() {

    }

    public void refreshFragment() {
        try{
            Thread.currentThread().sleep(250);
        }catch(Exception e){}
        Log.v("Attempting", "Event Refresh");
        invitations = svreq.getUserInvitations(thisuser);
        invitationArrayAdapter.notifyDataSetChanged();
        if (invitations != null) {
            Log.v("Completed", "EventRefresh");
        } else {
            Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                    "PickUpSports was unable to connect to server", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}