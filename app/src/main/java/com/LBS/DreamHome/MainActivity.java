/**
 * Copyright Google Inc. All Rights Reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.LBS.DreamHome;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.firebase.ui.auth.AuthUI;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static final String ANONYMOUS = "anonymous";


    private static final int RC_SIGN_IN = 1;


    private DrawerLayout mDrawerLayout;
    private ProgressBar mProgressBar;
    private TextView textView;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.mipmap.menu1);
        textView = (TextView) findViewById(R.id.greet);
        // FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //String Owner = user.getDisplayName();
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("hh");
        SimpleDateFormat df1 = new SimpleDateFormat("a");
        String formattedDate = df.format(c);
        String formattedDate1 = df1.format(c);
        if (Integer.parseInt(formattedDate) < 12 && formattedDate1.equals("am")) {
            textView.setText("Good Morning, ");
        } else if ((Integer.parseInt(formattedDate) == 12 || Integer.parseInt(formattedDate) < 5) && formattedDate1.equals("pm")) {
            textView.setText("Good Afternoon, ");
        } else {
            textView.setText("Good Evening, ");
        }

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
                            Intent i = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("Have Home")) {
                            Intent i = new Intent(MainActivity.this, HaveHome.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("Need Home")) {
                            Intent i = new Intent(MainActivity.this, Filter.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("About us")) {
                            Intent i = new Intent(MainActivity.this, aboutus.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("Profile")) {
                            Intent i = new Intent(MainActivity.this, Profile.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("My preferences")) {
                            Intent i = new Intent(MainActivity.this, preferences.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("My shortlists")) {
                            Intent i = new Intent(MainActivity.this, Shortlist.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("My Houses")) {
                            Intent i = new Intent(MainActivity.this, Uploads.class);
                            startActivity(i);
                        }
                        return true;
                    }
                });

        // Initialize Firebase components

        mFirebaseAuth = FirebaseAuth.getInstance();


        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);


        mProgressBar.setVisibility(View.INVISIBLE);


        // Initialize progress bar
        mProgressBar.setVisibility(ProgressBar.INVISIBLE);

        // ImagePickerButton shows an image picker to upload a image for a message


        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //textView.append(user.getDisplayName());

                } else {
                    List<AuthUI.IdpConfig> providers = Arrays.asList(
                            new AuthUI.IdpConfig.EmailBuilder().build(),
                            new AuthUI.IdpConfig.GoogleBuilder().build(),
                            new AuthUI.IdpConfig.AnonymousBuilder().build()
                    );
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(providers)
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };
        int off = 0;
        try {
            off = Settings.Secure.getInt(getContentResolver(), Settings.Secure.LOCATION_MODE);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        if (off == 0) {
            Intent onGPS = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(onGPS);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                // Sign-in succeeded, set up the UI
                setuser();

                Toast.makeText(this, "Signed in!", Toast.LENGTH_SHORT).show();
            } else {
                // Sign in was canceled by the user, finish the activity
                Toast.makeText(this, "Sign in canceled", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
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
                Intent i = new Intent(MainActivity.this, ChatList.class);
                startActivity(i);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void showneedhome(View view) {
        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String Owner = user.getUid();
        DatabaseReference mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("Users").child(Owner);
        mMessagesDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("Preferences")) {
                    Intent intent = new Intent(MainActivity.this, Recommend.class);
                    startActivity(intent);
                } else {
                    Intent i = new Intent(MainActivity.this, Filter.class);
                    startActivity(i);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void showhavehome(View view) {
        Intent i = new Intent(MainActivity.this, HaveHome.class);
        startActivity(i);
    }

    public void setuser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String Owner = user.getUid();
        String Name = user.getDisplayName();
        String Email = user.getEmail();
        String Photo = String.valueOf(user.getPhotoUrl());
        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("Users").child(Owner).child("Profile");
        mMessagesDatabaseReference.child("Name").setValue(Name);
        mMessagesDatabaseReference.child("Email").setValue(Email);
        mMessagesDatabaseReference.child("Photo").setValue(Photo);


    }


}
