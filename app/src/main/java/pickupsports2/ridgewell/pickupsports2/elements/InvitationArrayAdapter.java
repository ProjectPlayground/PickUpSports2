package pickupsports2.ridgewell.pickupsports2.elements;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pickupsports2.ridgewell.pickupsports2.R;
import pickupsports2.ridgewell.pickupsports2.utilities.ServerRequest;
import pickupsports2.ridgewell.pickupsports2.utilities.UserData;
import ridgewell.pickupsports2.common.Event;
import ridgewell.pickupsports2.common.Invitation;
import ridgewell.pickupsports2.common.User;

/**
 * Created by cameronridgewell on 1/21/15.
 */
public class InvitationArrayAdapter extends ArrayAdapter<Invitation> {
    private List<Invitation> list = new ArrayList<Invitation>();
    private Activity context;
    private ServerRequest svreq = ServerRequest.getInstance();
    private Event event = null;
    private User user = null;
    private int position;

    public InvitationArrayAdapter(Activity context, List<Invitation> invitation_list) {
        super(context, R.layout.invitation_list_item, invitation_list);
        this.position = position;
        this.list = invitation_list;
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
            view = inflater.inflate(R.layout.invitation_list_item, null);
        }

        event = svreq.getEvent(list.get(position).getEvent_id());
        user = svreq.getUser(list.get(position).getInviter_id());
        TextView title = (TextView) view.findViewById(R.id.invitation_adapter_title);
        title.setText(event.getName());

        TextView inviter = (TextView) view.findViewById(R.id.invitation_adapter_inviter);
        inviter.setText(user.getFirstname() + " " + user.getLastname() + " invited you!");

        Button dismiss = (Button) view.findViewById(R.id.dismiss_invite_button);
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                svreq.deleteInvitation(list.get(position));
                list.remove(position);
                notifyDataSetChanged();
            }
        });
        return view;
    }

    public void refreshItems(List<Invitation> invitations) {
        this.list = invitations;
        notifyDataSetChanged();
    }
}
