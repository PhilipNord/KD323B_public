package se.mah.k3.skaneAPI;


import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Spinner;

import java.util.ArrayList;

import se.mah.k3.skaneAPI.control.Constants;
import se.mah.k3.skaneAPI.model.Journey;
import se.mah.k3.skaneAPI.model.Journeys;
import se.mah.k3.skaneAPI.xmlparser.ExpandableAdapter;
import se.mah.k3.skaneAPI.xmlparser.Parser;

/**
 * A simple {@link android.app.Fragment} subclass.
 */
public class SearchFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private ArrayList<Journey> myJourneyItems = new ArrayList<Journey>();
    private ExpandableAdapter ea;
    private Spinner fromSpinner;
    private Spinner toSpinner;
    ExpandableListView ev;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


    }
    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        ///Do whatever
        ev = (ExpandableListView) v.findViewById(R.id.possibleJourneyList);
        ea = new ExpandableAdapter(getActivity(),myJourneyItems);


        fromSpinner = (Spinner)v.findViewById(R.id.fromSpinner);
        fromSpinner.setOnItemSelectedListener(this);
        toSpinner = (Spinner)v.findViewById(R.id.toSpinner);
        toSpinner.setOnItemSelectedListener(this);


//        //Uppkopplings Check
//        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo ni = cm.getActiveNetworkInfo();
//        if(ni == null || !ni.isConnected()) {
//            Toast.makeText(getActivity(), "No Internet Conncetion", Toast.LENGTH_LONG).show();
//        } else {
//            Toast.makeText(getActivity(), "Internet Accsses!", Toast.LENGTH_LONG).show();
//        }


        return v;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Log.i("Fragment", "MenuSelection. " + id);
        if (id == R.id.refresh_btn) {
            int fromNmbr = fromSpinner.getSelectedItemPosition();
            int toNmbr = toSpinner.getSelectedItemPosition();
            String[] adventures = getActivity().getResources().getStringArray(R.array.stnNmbr);
            String searchURL = Constants.getURL(adventures[fromNmbr], adventures[toNmbr], 10);

            new MyAsyncTask().execute(searchURL);

            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    //Listens to the spinner

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int fromNmbr = fromSpinner.getSelectedItemPosition();
        int toNmbr = toSpinner.getSelectedItemPosition();
        String[] courses = getActivity().getResources().getStringArray(R.array.stnNmbr);
        String searchURL = Constants.getURL(courses[fromNmbr], courses[toNmbr], 10);
        Log.d("From Number", courses[fromNmbr]);

        //Log.i("ExpFragment", "Course. " + course);
        new MyAsyncTask().execute(searchURL);
    }

    public void onNothingSelected(AdapterView<?> parent) {
    }


    //And the thread
    private class MyAsyncTask extends AsyncTask<String,Void,Void> {
        @Override
        protected Void doInBackground(String... params) {
            Journeys journeys = Parser.getJourneys(params[0]);
            myJourneyItems.clear();
            myJourneyItems.addAll(journeys.getJourneys());


            Log.d("Skane", journeys.getJourneys().toString());

            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ev.setAdapter(ea);
        }




    }


}
