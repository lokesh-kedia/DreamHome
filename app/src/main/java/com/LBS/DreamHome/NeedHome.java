package com.LBS.DreamHome;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NeedHome extends AppCompatActivity {


    private static final String ANONYMOUS = "anonymous";

    private ListView mMessageListView;
    private MessageAdapter mMessageAdapter;


    private ArrayList<String> plotNo = new ArrayList<>();
    private String State;
    private String City;
    private String Area;
    private Query myTopPostsQuery;
    // Firebase instance variables
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;
    private ChildEventListener mChildEventListener;
    private DrawerLayout mDrawerLayout;
    private double lat, lng;
    private ProgressBar progressBar;
    private ValueEventListener valueEventListener;
    private ProgressDialog progressDialog;
private  int backButtonCount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        State = bundle.getString("StateFilter");
        City = bundle.getString("CityFilter");
        Area = bundle.getString("AreaFilter");


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need_home);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.mipmap.menu1);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here
                        if (menuItem.getTitle().equals("Dashboard")) {
                            Intent i = new Intent(NeedHome.this, MainActivity.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("Have Home")) {
                            Intent i = new Intent(NeedHome.this, HaveHome.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("Need Home")) {
                            Intent i = new Intent(NeedHome.this, Filter.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("About us")) {
                            Intent i = new Intent(NeedHome.this, aboutus.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("Profile")) {
                            Intent i = new Intent(NeedHome.this, Profile.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("My preferences")) {
                            Intent i = new Intent(NeedHome.this, preferences.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("My shortlists")) {
                            Intent i = new Intent(NeedHome.this, Shortlist.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("My Houses")) {
                            Intent i = new Intent(NeedHome.this, Uploads.class);
                            startActivity(i);
                        }
                        return true;
                    }
                });


        // Initialize Firebase components
        progressDialog = new ProgressDialog(NeedHome.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        Bundle bundle1 = getIntent().getExtras();
        String sort = bundle1.getString("sort");
        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("States").child(State).child(City).child(Area);
       // myTopPostsQuery = mMessagesDatabaseReference.child("BedRooms")
               // .equalTo("1");
        if (sort != null) {
           // mMessagesDatabaseReference = (DatabaseReference) mMessagesDatabaseReference.orderByChild("Price");


        }


        // Initialize references to views
        if(sort==null)
        attachDatabaseReadListener();
        mMessageListView = (ListView) findViewById(R.id.messageListView);

        List<FriendlyMessage> friendlyMessages = new ArrayList<>();
        mMessageAdapter = new MessageAdapter(this, R.layout.item_message, friendlyMessages);
        mMessageListView.setAdapter(mMessageAdapter);


        mMessageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(NeedHome.this, HomeInfo.class);
                intent.putExtra("StateFilter", State);
                intent.putExtra("CityFilter", City);
                intent.putExtra("AreaFilter", Area);
                intent.putExtra("PlotFilter", String.valueOf(plotNo.get(i)));
                //Toast.makeText(NeedHome.this, String.valueOf(plotNo.get(i)), Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });
        // Initialize progress bar
        //Toast.makeText(this, "yahan", Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        attachDatabaseReadListener();

    }

    @Override
    protected void onPause() {
        super.onPause();
        String s = String.valueOf(plotNo.size());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.sign_out_menu:
                FirebaseAuth.getInstance().signOut();
                return true;
            case R.id.action_item_two:
                Intent i = new Intent(NeedHome.this, ChatList.class);
                startActivity(i);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void attachDatabaseReadListener() {
        if (valueEventListener == null) {
            valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot plots : dataSnapshot.getChildren()) {
                        FriendlyMessage friendlyMessage = plots.getValue(FriendlyMessage.class);
                        friendlyMessage.setArea(Area);
                        if (friendlyMessage.getAvail().equals("True")) {
                            mMessageAdapter.add(friendlyMessage);
                            plotNo.add(plots.getKey());
                        }
                        progressDialog.dismiss();
                    }
                }

                public void onCancelled(DatabaseError databaseError) {
                }
            };
            mMessagesDatabaseReference.addListenerForSingleValueEvent(valueEventListener);
        }

    }

    public void phoneCall(String num) {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + num));

            this.startActivity(callIntent);
        } else {
            Toast.makeText(this, "You don't assign permission.", Toast.LENGTH_SHORT).show();
        }
    }

    public double distance(double plotlat, double plotlng) {

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location != null) {
                    lat = location.getLatitude();
                    lng = location.getLongitude();
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        LocationManager mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 50, 100, locationListener);

        Location startPoint = new Location("locationA");
        startPoint.setLatitude(lat);
        startPoint.setLongitude(lng);

        Location endPoint = new Location("locationB");
        endPoint.setLatitude(plotlat);
        endPoint.setLongitude(plotlng);

        double distance = startPoint.distanceTo(endPoint);
        return distance;

    }

    public void setfav(int position, View view) {
        String plot = String.valueOf(plotNo.get(position));
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String Owner = user.getUid();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        String key = State + "_" + City + "_" + Area + "_" + plot;
        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("Users").child(Owner).child("Shortlist").child(key);
        mMessagesDatabaseReference.setValue("0");
        Snackbar.make(view, "Set To Shortlists Successfully", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

    }


    public void filtered(View view) {
        Intent i = new Intent(NeedHome.this, FilterOptions.class);
        startActivity(i);


    }
    @Override
    public void onBackPressed()
    {
        if(backButtonCount >= 1)
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else
        {
            super.onBackPressed();
            Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }

    @Override
    protected void onStart() {
        backButtonCount=0;
        super.onStart();
    }
}