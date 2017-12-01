package org.ekylibre.testfragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.ekylibre.testfragments.database.AppDatabase;
import org.ekylibre.testfragments.database.DatabaseInitializer;
import org.ekylibre.testfragments.database.User;

import java.util.ArrayList;
import java.util.List;


public class Tab2Fragment extends Fragment {

    private static final String TAG = Tab2Fragment.class.getSimpleName(); // and not "TasksSample"

    AppDatabase db;

    private OnTab2FragmentInteractionListener mListener;

    public Tab2Fragment() {
        // Required empty public constructor
    }

    public static Tab2Fragment newInstance() {
        return new Tab2Fragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Prepares inflating Tab2 fragment
        View fragmentInflatedView = inflater.inflate(R.layout.fragment_tab2, container, false);

        // Sets label for list in a TextView
        TextView list_label = fragmentInflatedView.findViewById(R.id.tab2_param1);
        list_label.setText(R.string.user_list_label);

        // Hard coded array for testing ListView
//        String[] prenoms = new String[]{"Antoine", "Benoit", "Cyril", "David", "Eloise", "Florent"};

        // Get ListView widget from layout
        ListView userListView = fragmentInflatedView.findViewById(R.id.tab2_users_list);

        db = AppDatabase.getDatabase(getActivity());

        List<User> userList = db.userDao().getAll();

        List<String> databaseUserList = new ArrayList<String>();

        for ( User uSer : userList ) {
            databaseUserList.add(uSer.getUid() + " | " + uSer.getFirstName() + " " + uSer.getLastName());
        }

        ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.users_listview, databaseUserList);
        userListView.setAdapter(adapter);

        return fragmentInflatedView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTab2FragmentInteractionListener) {
            mListener = (OnTab2FragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnTab2FragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnTab2FragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
