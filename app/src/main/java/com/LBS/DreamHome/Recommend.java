package com.LBS.DreamHome;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import com.google.android.material.navigation.NavigationView;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Recommend extends AppCompatActivity {
    private ListView mRecyclerView, listView;
    private LinearLayout linearLayout;
    private RecommentAdapter recommentAdapter;
    private ArrayList<String> plotNo = new ArrayList<>();
    private ArrayList<String> plotNos;
    private ArrayList<AdView> adViews;
    private ListView adlist;
    private ArrayAdapter<AdView> adViewArrayAdapter;
    private DrawerLayout mDrawerLayout;
    private AdView mAdView;
    private int backButtonCount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.mipmap.menu1);
        MobileAds.initialize(this, "ca-app-pub-5314021439844328~8679195677");
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView = (AdView) findViewById(R.id.adView1);
        adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView = (AdView) findViewById(R.id.adView1);
        adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView = (AdView) findViewById(R.id.adView2);
        adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView = (AdView) findViewById(R.id.adView3);
        adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView = (AdView) findViewById(R.id.adView4);
        adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView = (AdView) findViewById(R.id.adView5);
        adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView = (AdView) findViewById(R.id.adView6);
        adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView = (AdView) findViewById(R.id.adView7);
        adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView = (AdView) findViewById(R.id.adView8);
        adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView = (AdView) findViewById(R.id.adView9);
        adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView = (AdView) findViewById(R.id.adView10);
        adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

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
                            Intent i = new Intent(Recommend.this, MainActivity.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("Have Home")) {
                            Intent i = new Intent(Recommend.this, HaveHome.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("Need Home")) {
                            Intent i = new Intent(Recommend.this, Filter.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("About us")) {
                            Intent i = new Intent(Recommend.this, aboutus.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("Profile")) {
                            Intent i = new Intent(Recommend.this, Profile.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("My preferences")) {
                            Intent i = new Intent(Recommend.this, preferences.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("My shortlists")) {
                            Intent i = new Intent(Recommend.this, Shortlist.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("My Houses")) {
                            Intent i = new Intent(Recommend.this, Uploads.class);
                            startActivity(i);
                        }
                        return true;
                    }
                });

        linearLayout = (LinearLayout) findViewById(R.id.locals);
        List<String> localities = new ArrayList<>();
        final ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this, R.layout.item_recommend, localities);


        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String Owner = user.getUid();
        DatabaseReference mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("Users").child(Owner).child("Preferences").getRef();
        mMessagesDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot plots : dataSnapshot.getChildren()) {
                    String filter = plots.getKey();
                    filter = filter.replace("_", "/");
                    plotNo.add(filter);
                    final String[] add = filter.split("/");
                    final String ss = add[2];
                    final Button textView = new Button(Recommend.this);
                    textView.setText(ss + "\t+");
                    textView.setTextColor(getResources().getColor(R.color.buttonTextColor));
                    textView.setPadding(16, 8, 16, 8);
                    textView.setTextSize(20);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(8, 8, 8, 8);
                    textView.setLayoutParams(params);
                    textView.setGravity(Gravity.CENTER);
                    linearLayout.addView(textView);
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            int index = ((ViewGroup) textView.getParent()).indexOfChild(textView);
                            if (textView.getText().equals(ss + "\t+")) {
                                //Toast.makeText(Recommend.this,String.valueOf(index),Toast.LENGTH_LONG).show();
                                attachdatabaselistener(plotNo.get(index));
                                String s = (String) textView.getText();
                                s = s.replace("+", "-");
                                textView.setText(s);
                            } else {
                                mRecyclerView = (ListView) findViewById(R.id.recomview);
                                mRecyclerView.setVisibility(View.GONE);
                                String s = (String) textView.getText();
                                s = s.replace("-", "+");
                                textView.setText(s);

                            }
                        }
                    });


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mRecyclerView = (ListView) findViewById(R.id.recomview);
        mRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Recommend.this, HomeInfo.class);
                intent.putExtra("PlotFilter", String.valueOf(plotNos.get(i)));
                intent.putExtra("type", "shortlist");
                //Toast.makeText(Recommend.this, String.valueOf(plotNos.get(i)), Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });
    }

    public void attachdatabaselistener(final String filter) {
        //adViews = new ArrayList<>();
        //adlist = (ListView) findViewById(R.id.adview);
        //adViewArrayAdapter = new ArrayAdapter<>(this, R.layout.item_ad, adViews);
        List<FriendlyMessage> friendlyMessages = new ArrayList<>();
        recommentAdapter = new RecommentAdapter(Recommend.this, R.layout.item_home, friendlyMessages);
        mRecyclerView = (ListView) findViewById(R.id.recomview);
        mRecyclerView.setVisibility(View.VISIBLE);
        mRecyclerView.setAdapter(recommentAdapter);

        plotNos = new ArrayList<>();
        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("States").child(filter);
        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot plots : dataSnapshot.getChildren()) {
                    FriendlyMessage friendlyMessage = plots.getValue(FriendlyMessage.class);
                    if (friendlyMessage.getAvail().equals("True")) {
                        //Toast.makeText(Recommend.this,friendlyMessage.getPrice(),Toast.LENGTH_LONG).show();
                        recommentAdapter.add(friendlyMessage);
                        plotNos.add(filter + "/" + plots.getKey());
                       /* MobileAds.initialize(Recommend.this, "ca-app-pub-5314021439844328~8679195677");
                        AdView adView = new AdView(Recommend.this);
                        adView.setAdSize(AdSize.BANNER);
                        adView.setAdUnitId("ca-app-pub-5314021439844328/1950135794");
                        //adView = (AdView) findViewById(R.id.adView);
                        AdRequest adRequest = new AdRequest.Builder().build();
                        adView.loadAd(adRequest);
                        adViews.add(adView);
                        adlist.setAdapter(adViewArrayAdapter);*/

                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void hide(View view) {
        mRecyclerView.setVisibility(View.GONE);
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
                Intent i = new Intent(Recommend.this, ChatList.class);
                startActivity(i);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showneedhome(View view) {
        Intent i = new Intent(Recommend.this, Filter.class);
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
