package se.mah.k3.skaneAPI.xmlparser;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import se.mah.k3.skaneAPI.R;
import se.mah.k3.skaneAPI.model.Journey;
import se.mah.k3.skaneAPI.model.Station;


/**
 * Created by Philip on 2015-04-16.
 */
public class ExpandableAdapter extends BaseExpandableListAdapter {

        private ArrayList<Journey> myJourneyItems;
        private Context c;

    public ExpandableAdapter(Context c, ArrayList<Journey> myJourneyItems){

        this.c= c;
        this.myJourneyItems = myJourneyItems;

    }


    @Override
    public int getGroupCount() {
        return myJourneyItems.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Journey j  = myJourneyItems.get(groupPosition);

        if(convertView == null){
            LayoutInflater li = (LayoutInflater) this.c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.group_view, null);
        }

        TextView avgang = (TextView) convertView.findViewById(R.id.group_avg);
        Station startStationName = j.getStartStation();
        avgang.setText(startStationName.getStationName() + "             -");

        TextView destination = (TextView) convertView.findViewById(R.id.group_dest);
        Station endStationName = j.getEndStation();
        destination.setText(endStationName.getStationName());


        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            Journey j = myJourneyItems.get(groupPosition);

        if(convertView == null){
            LayoutInflater li = (LayoutInflater)this.c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.child_view,null);
        }

        TextView lineName = (TextView) convertView.findViewById(R.id.lineName);
        String ln = j.getLineTypeName();
        lineName.setText(ln);
        TextView timeToDeparture = (TextView) convertView.findViewById(R.id.timeToDeparture);
        String departure = j.getTimeToDeparture();

        ImageView varning = (ImageView) convertView.findViewById(R.id.varning);
        if (Integer.parseInt(j.getTimeToDeparture()) < 5) {
            Log.i("tiden", "fungerar");
            varning.setVisibility(View.VISIBLE);
        }else{
            varning.setVisibility(View.INVISIBLE);

        }
        timeToDeparture.setText(departure + " min");
        TextView travelMinutes = (TextView) convertView.findViewById(R.id.travelMinutes);
        String minutes = j.getTravelMinutes();
        travelMinutes.setText(minutes + " min");
        TextView noOfChanges = (TextView) convertView.findViewById(R.id.noOfChanges);
        String changes = j.getNoOfChanges();
        noOfChanges.setText(changes);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i2) {
        return false;
    }

    @Override
    public Object getGroup(int i) {
        return null;
    }

    @Override
    public Object getChild(int i, int i2) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i2) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}


