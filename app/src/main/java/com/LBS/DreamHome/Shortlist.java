package com.LBS.DreamHome;

import android.app.ProgressDialog;
import android.content.Intent;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Shortlist extends AppCompatActivity {
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;
    private FirebaseUser user;
    private ListView mMessageListView;
    private ArrayAdapter mMessageAdapter;
    private String State;
    private String City;
    private DrawerLayout mDrawerLayout;
    private String Area;
    private ValueEventListener valueEventListener;
    private ArrayList<String> plotNo = new ArrayList<>();
    private ProgressDialog progressDialog;
private int backButtonCount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploads);
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
                            Intent i = new Intent(Shortlist.this, MainActivity.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("Have Home")) {
                            Intent i = new Intent(Shortlist.this, HaveHome.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("Need Home")) {
                            Intent i = new Intent(Shortlist.this, Filter.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("About us")) {
                            Intent i = new Intent(Shortlist.this, aboutus.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("Profile")) {
                            Intent i = new Intent(Shortlist.this, Profile.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("My preferences")) {
                            Intent i = new Intent(Shortlist.this, preferences.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("My shortlists")) {
                            Intent i = new Intent(Shortlist.this, Shortlist.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("My Houses")) {
                            Intent i = new Intent(Shortlist.this, Uploads.class);
                            startActivity(i);
                        }
                        return true;
                    }
                });
        progressDialog = new ProgressDialog(Shortlist.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        String Owner = user.getUid();
        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("Users").child(Owner).child("Shortlist");
        attachDatabaseReadListener();
        mMessageListView = (ListView) findViewById(R.id.messageListView);

        List<String> friendlyMessages = new ArrayList<>();
        mMessageAdapter = new ArrayAdapter<String>(this, R.layout.item_message1, friendlyMessages);
        mMessageListView.setAdapter(mMessageAdapter);


        mMessageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Shortlist.this, HomeInfo.class);
                intent.putExtra("PlotFilter", String.valueOf(plotNo.get(i)));
                intent.putExtra("type", "shortlist");
                //Toast.makeText(NeedHome.this, String.valueOf(plotNo.get(i)), Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });
    }

    public void attachDatabaseReadListener() {
        if (valueEventListener == null) {
            valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot plots : dataSnapshot.getChildren()) {
                        String friendlyMessage = plots.getKey();
                        plotNo.add(friendlyMessage);
                        mMessageAdapter.add(friendlyMessage);
                        progressDialog.dismiss();
                    }
                }

                public void onCancelled(DatabaseError databaseError) {
                }
            };
            mMessagesDatabaseReference.addListenerForSingleValueEvent(valueEventListener);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        attachDatabaseReadListener();
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
                Intent i = new Intent(Shortlist.this, ChatList.class);
                startActivity(i);
                return true;
        }
        return super.onOptionsItemSelected(item);
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
