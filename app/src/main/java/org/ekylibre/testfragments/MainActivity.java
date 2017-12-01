package org.ekylibre.testfragments;


import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import org.ekylibre.testfragments.database.AppDatabase;
import org.ekylibre.testfragments.database.DatabaseInitializer;
import org.ekylibre.testfragments.database.User;


public class MainActivity extends AppCompatActivity implements
        Tab1Fragment.OnTab1FragmentInteractionListener,
        Tab2Fragment.OnTab2FragmentInteractionListener,
        Tab3Fragment.OnTab3FragmentInteractionListener {

    private AppDatabase db;

    private static User addUser(final AppDatabase db, User user) {
        db.userDao().insertAll(user);
        return user;
    }

    private static void populateWithTestData(AppDatabase db) {
        User user = new User();
        user.setFirstName("Ajay");
        user.setLastName("Saini");
        addUser(db, user);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    pushFragment(Tab1Fragment.newInstance("abc", "def"));
                    return true;
                case R.id.navigation_dashboard:
                    pushFragment(Tab2Fragment.newInstance());
                    return true;
                case R.id.navigation_notifications:
                    pushFragment(Tab3Fragment.newInstance("mno", "pqr"));
                    return true;
            }
            return false;
        }
    };

    protected void pushFragment(Fragment currentFragment) {
        if (currentFragment == null)
            return;

        FragmentManager fm = getFragmentManager();
        if (fm != null) {
            FragmentTransaction ft = fm.beginTransaction();
            if (ft != null) {
                ft.replace(R.id.fragment_container, currentFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Set navigation default to 'home'
        pushFragment(Tab1Fragment.newInstance("abc", "def"));

        db = AppDatabase.getDatabase(getApplicationContext());
//
//        populateWithTestData(db);

        DatabaseInitializer.populateAsync(AppDatabase.getDatabase(this));

        // new DatabaseAsync().execute();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

//    @SuppressLint("StaticFieldLeak")  // Disables warning
//    private class DatabaseAsync extends AsyncTask<Void, Void, Void> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//            // Perform pre-adding operation here.
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            User user = new User();
//            user.setFirstName("Rémi");
//            user.setLastName("de Chazelles");
//
//            db.userDao().insertAll(user);
//
//            return null;
//        }
//    }
}