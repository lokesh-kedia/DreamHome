package com.LBS.DreamHome;

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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class preferences extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
private int backButtonCount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
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
                            Intent i = new Intent(preferences.this, MainActivity.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("Have Home")) {
                            Intent i = new Intent(preferences.this, HaveHome.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("Need Home")) {
                            Intent i = new Intent(preferences.this, Filter.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("About us")) {
                            Intent i = new Intent(preferences.this, aboutus.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("Profile")) {
                            Intent i = new Intent(preferences.this, Profile.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("My preferences")) {
                            Intent i = new Intent(preferences.this, preferences.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("My shortlists")) {
                            Intent i = new Intent(preferences.this, Shortlist.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("My Houses")) {
                            Intent i = new Intent(preferences.this, Uploads.class);
                            startActivity(i);
                        }
                        return true;
                    }
                });

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
                Intent i = new Intent(preferences.this, ChatList.class);
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
