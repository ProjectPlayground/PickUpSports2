package pickupsports2.ridgewell.pickupsports2.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import pickupsports2.ridgewell.pickupsports2.R;
import pickupsports2.ridgewell.pickupsports2.activities.MainActivity;
import pickupsports2.ridgewell.pickupsports2.utilities.SwipeRefreshListFragment;

/**
 * Created by cameronridgewell on 2/9/15.
 */
public class TeamListFragment extends SwipeRefreshListFragment implements MainActivity.MainActivityFragment {

    View rootView;

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_event_list_view, container, false);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshFragment();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    public void onActionButtonClick() {

    }

    public void refreshFragment() {

    }
}