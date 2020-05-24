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
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class FilterOptions extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_options);
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
                            Intent i = new Intent(FilterOptions.this, MainActivity.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("Have Home")) {
                            Intent i = new Intent(FilterOptions.this, HaveHome.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("Need Home")) {
                            Intent i = new Intent(FilterOptions.this, Filter.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("About us")) {
                            Intent i = new Intent(FilterOptions.this, aboutus.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("Profile")) {
                            Intent i = new Intent(FilterOptions.this, Profile.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("My preferences")) {
                            Intent i = new Intent(FilterOptions.this, preferences.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("My shortlists")) {
                            Intent i = new Intent(FilterOptions.this, Shortlist.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("My Houses")) {
                            Intent i = new Intent(FilterOptions.this, Uploads.class);
                            startActivity(i);
                        }
                        return true;
                    }
                });
       /* final TextView textView = (TextView) findViewById(R.id.sortfilteroptions);
        TextView textView1 = (TextView) findViewById(R.id.pricefilteroptions);
        TextView textView2= (TextView) findViewById(R.id.availablefilteroptions);
        TextView textView3 = (TextView) findViewById(R.id.localityfilteroptions);
        TextView textView4 = (TextView) findViewById(R.id.postedfilteroptions);
        TextView textView5 = (TextView) findViewById(R.id.furnishingfilteroptions);
        TextView textView6 = (TextView) findViewById(R.id.bedroomfilteroptions);

        TextView textView8 = (TextView) findViewById(R.id.amenitiesfilteroptions);
        textView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b==false)
                    textView.setBackgroundColor(getResources().getColor(R.color.primaryDarkColor));
                else
                    textView.setBackgroundColor(getResources().getColor(R.color.primaryDarkColor));
            }
        });*/

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
                Intent i = new Intent(FilterOptions.this, ChatList.class);
                startActivity(i);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void amenitiesfilteroptions(View view) {
        TextView textView = (TextView) findViewById(R.id.sortfilteroptions);
        TextView textView1 = (TextView) findViewById(R.id.pricefilteroptions);
        TextView textView2= (TextView) findViewById(R.id.availablefilteroptions);
        TextView textView3 = (TextView) findViewById(R.id.localityfilteroptions);
        TextView textView4 = (TextView) findViewById(R.id.postedfilteroptions);
        TextView textView5 = (TextView) findViewById(R.id.furnishingfilteroptions);
        TextView textView6 = (TextView) findViewById(R.id.bedroomfilteroptions);
        TextView textView7 = (TextView) findViewById(R.id.bathroomfilteroptions);
        TextView textView8 = (TextView) findViewById(R.id.amenitiesfilteroptions);
        textView.setBackgroundColor(getResources().getColor(R.color.back));
        textView1.setBackgroundColor(getResources().getColor(R.color.back));
        textView2.setBackgroundColor(getResources().getColor(R.color.back));
        textView3.setBackgroundColor(getResources().getColor(R.color.back));
        textView4.setBackgroundColor(getResources().getColor(R.color.back));
        textView5.setBackgroundColor(getResources().getColor(R.color.back));
        textView6.setBackgroundColor(getResources().getColor(R.color.back));
        textView7.setBackgroundColor(getResources().getColor(R.color.back));
        textView8.setBackgroundColor(getResources().getColor(R.color.primaryDarkColor));

    }

    public void bathroomfilteroptions(View view) {
        TextView textView = (TextView) findViewById(R.id.sortfilteroptions);
        TextView textView1 = (TextView) findViewById(R.id.pricefilteroptions);
        TextView textView2= (TextView) findViewById(R.id.availablefilteroptions);
        TextView textView3 = (TextView) findViewById(R.id.localityfilteroptions);
        TextView textView4 = (TextView) findViewById(R.id.postedfilteroptions);
        TextView textView5 = (TextView) findViewById(R.id.furnishingfilteroptions);
        TextView textView6 = (TextView) findViewById(R.id.bedroomfilteroptions);
        TextView textView7 = (TextView) findViewById(R.id.bathroomfilteroptions);
        TextView textView8 = (TextView) findViewById(R.id.amenitiesfilteroptions);
        textView.setBackgroundColor(getResources().getColor(R.color.back));
        textView1.setBackgroundColor(getResources().getColor(R.color.back));
        textView2.setBackgroundColor(getResources().getColor(R.color.back));
        textView3.setBackgroundColor(getResources().getColor(R.color.back));
        textView4.setBackgroundColor(getResources().getColor(R.color.back));
        textView5.setBackgroundColor(getResources().getColor(R.color.back));
        textView6.setBackgroundColor(getResources().getColor(R.color.back));
        textView7.setBackgroundColor(getResources().getColor(R.color.primaryDarkColor));
        textView8.setBackgroundColor(getResources().getColor(R.color.back));
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.bedrooms);
        linearLayout.setVisibility(View.GONE);
        LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.furnishing);
        linearLayout1.setVisibility(View.GONE);
        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.postedsince);
        linearLayout2.setVisibility(View.GONE);
        LinearLayout linearLayout3 = (LinearLayout) findViewById(R.id.availablefor);
        linearLayout3.setVisibility(View.GONE);
        LinearLayout linearLayout4 = (LinearLayout) findViewById(R.id.price);
        linearLayout4.setVisibility(View.GONE);
        LinearLayout linearLayout5 = (LinearLayout) findViewById(R.id.sortby);
        linearLayout5.setVisibility(View.GONE);
        LinearLayout linearLayout6 = (LinearLayout) findViewById(R.id.bathrooms);
        linearLayout6.setVisibility(View.VISIBLE);
        LinearLayout linearLayout7 = (LinearLayout) findViewById(R.id.locality);
        linearLayout7.setVisibility(View.GONE);
    }

    public void bedroomfilteroptions(View view) {
        TextView textView = (TextView) findViewById(R.id.sortfilteroptions);
        TextView textView1 = (TextView) findViewById(R.id.pricefilteroptions);
        TextView textView2= (TextView) findViewById(R.id.availablefilteroptions);
        TextView textView3 = (TextView) findViewById(R.id.localityfilteroptions);
        TextView textView4 = (TextView) findViewById(R.id.postedfilteroptions);
        TextView textView5 = (TextView) findViewById(R.id.furnishingfilteroptions);
        TextView textView6 = (TextView) findViewById(R.id.bedroomfilteroptions);
        TextView textView7 = (TextView) findViewById(R.id.bathroomfilteroptions);
        TextView textView8 = (TextView) findViewById(R.id.amenitiesfilteroptions);
        textView.setBackgroundColor(getResources().getColor(R.color.back));
        textView1.setBackgroundColor(getResources().getColor(R.color.back));
        textView2.setBackgroundColor(getResources().getColor(R.color.back));
        textView3.setBackgroundColor(getResources().getColor(R.color.back));
        textView4.setBackgroundColor(getResources().getColor(R.color.back));
        textView5.setBackgroundColor(getResources().getColor(R.color.back));
        textView6.setBackgroundColor(getResources().getColor(R.color.primaryDarkColor));
        textView7.setBackgroundColor(getResources().getColor(R.color.back));
        textView8.setBackgroundColor(getResources().getColor(R.color.back));
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.bedrooms);
        linearLayout.setVisibility(View.VISIBLE);
        LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.furnishing);
        linearLayout1.setVisibility(View.GONE);
        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.postedsince);
        linearLayout2.setVisibility(View.GONE);
        LinearLayout linearLayout3 = (LinearLayout) findViewById(R.id.availablefor);
        linearLayout3.setVisibility(View.GONE);
        LinearLayout linearLayout4 = (LinearLayout) findViewById(R.id.price);
        linearLayout4.setVisibility(View.GONE);
        LinearLayout linearLayout5 = (LinearLayout) findViewById(R.id.sortby);
        linearLayout5.setVisibility(View.GONE);
        LinearLayout linearLayout6 = (LinearLayout) findViewById(R.id.bathrooms);
        linearLayout6.setVisibility(View.GONE);
        LinearLayout linearLayout7 = (LinearLayout) findViewById(R.id.locality);
        linearLayout7.setVisibility(View.GONE);
    }

    public void furnishingfilteroptions(View view) {
        TextView textView = (TextView) findViewById(R.id.sortfilteroptions);
        TextView textView1 = (TextView) findViewById(R.id.pricefilteroptions);
        TextView textView2= (TextView) findViewById(R.id.availablefilteroptions);
        TextView textView3 = (TextView) findViewById(R.id.localityfilteroptions);
        TextView textView4 = (TextView) findViewById(R.id.postedfilteroptions);
        TextView textView5 = (TextView) findViewById(R.id.furnishingfilteroptions);
        TextView textView6 = (TextView) findViewById(R.id.bedroomfilteroptions);
        TextView textView7 = (TextView) findViewById(R.id.bathroomfilteroptions);
        TextView textView8 = (TextView) findViewById(R.id.amenitiesfilteroptions);
        textView.setBackgroundColor(getResources().getColor(R.color.back));
        textView1.setBackgroundColor(getResources().getColor(R.color.back));
        textView2.setBackgroundColor(getResources().getColor(R.color.back));
        textView3.setBackgroundColor(getResources().getColor(R.color.back));
        textView4.setBackgroundColor(getResources().getColor(R.color.back));
        textView5.setBackgroundColor(getResources().getColor(R.color.primaryDarkColor));
        textView6.setBackgroundColor(getResources().getColor(R.color.back));
        textView7.setBackgroundColor(getResources().getColor(R.color.back));
        textView8.setBackgroundColor(getResources().getColor(R.color.back));
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.bedrooms);
        linearLayout.setVisibility(View.GONE);
        LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.furnishing);
        linearLayout1.setVisibility(View.VISIBLE);
        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.postedsince);
        linearLayout2.setVisibility(View.GONE);
        LinearLayout linearLayout3 = (LinearLayout) findViewById(R.id.availablefor);
        linearLayout3.setVisibility(View.GONE);
        LinearLayout linearLayout4 = (LinearLayout) findViewById(R.id.price);
        linearLayout4.setVisibility(View.GONE);
        LinearLayout linearLayout5 = (LinearLayout) findViewById(R.id.sortby);
        linearLayout5.setVisibility(View.GONE);
        LinearLayout linearLayout6 = (LinearLayout) findViewById(R.id.bathrooms);
        linearLayout6.setVisibility(View.GONE);
        LinearLayout linearLayout7 = (LinearLayout) findViewById(R.id.locality);
        linearLayout7.setVisibility(View.GONE);
    }

    public void postedfilteroptions(View view) {
        TextView textView = (TextView) findViewById(R.id.sortfilteroptions);
        TextView textView1 = (TextView) findViewById(R.id.pricefilteroptions);
        TextView textView2= (TextView) findViewById(R.id.availablefilteroptions);
        TextView textView3 = (TextView) findViewById(R.id.localityfilteroptions);
        TextView textView4 = (TextView) findViewById(R.id.postedfilteroptions);
        TextView textView5 = (TextView) findViewById(R.id.furnishingfilteroptions);
        TextView textView6 = (TextView) findViewById(R.id.bedroomfilteroptions);
        TextView textView7 = (TextView) findViewById(R.id.bathroomfilteroptions);
        TextView textView8 = (TextView) findViewById(R.id.amenitiesfilteroptions);
        textView.setBackgroundColor(getResources().getColor(R.color.back));
        textView1.setBackgroundColor(getResources().getColor(R.color.back));
        textView2.setBackgroundColor(getResources().getColor(R.color.back));
        textView3.setBackgroundColor(getResources().getColor(R.color.back));
        textView4.setBackgroundColor(getResources().getColor(R.color.primaryDarkColor));
        textView5.setBackgroundColor(getResources().getColor(R.color.back));
        textView6.setBackgroundColor(getResources().getColor(R.color.back));
        textView7.setBackgroundColor(getResources().getColor(R.color.back));
        textView8.setBackgroundColor(getResources().getColor(R.color.back));
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.bedrooms);
        linearLayout.setVisibility(View.GONE);
        LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.furnishing);
        linearLayout1.setVisibility(View.GONE);
        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.postedsince);
        linearLayout2.setVisibility(View.VISIBLE);
        LinearLayout linearLayout3 = (LinearLayout) findViewById(R.id.availablefor);
        linearLayout3.setVisibility(View.GONE);
        LinearLayout linearLayout4 = (LinearLayout) findViewById(R.id.price);
        linearLayout4.setVisibility(View.GONE);
        LinearLayout linearLayout5 = (LinearLayout) findViewById(R.id.sortby);
        linearLayout5.setVisibility(View.GONE);
        LinearLayout linearLayout6 = (LinearLayout) findViewById(R.id.bathrooms);
        linearLayout6.setVisibility(View.GONE);
        LinearLayout linearLayout7 = (LinearLayout) findViewById(R.id.locality);
        linearLayout7.setVisibility(View.GONE);
    }

    public void localityfilteroptions(View view) {
        TextView textView = (TextView) findViewById(R.id.sortfilteroptions);
        TextView textView1 = (TextView) findViewById(R.id.pricefilteroptions);
        TextView textView2= (TextView) findViewById(R.id.availablefilteroptions);
        TextView textView3 = (TextView) findViewById(R.id.localityfilteroptions);
        TextView textView4 = (TextView) findViewById(R.id.postedfilteroptions);
        TextView textView5 = (TextView) findViewById(R.id.furnishingfilteroptions);
        TextView textView6 = (TextView) findViewById(R.id.bedroomfilteroptions);
        TextView textView7 = (TextView) findViewById(R.id.bathroomfilteroptions);
        TextView textView8 = (TextView) findViewById(R.id.amenitiesfilteroptions);
        textView.setBackgroundColor(getResources().getColor(R.color.back));
        textView1.setBackgroundColor(getResources().getColor(R.color.back));
        textView2.setBackgroundColor(getResources().getColor(R.color.back));
        textView3.setBackgroundColor(getResources().getColor(R.color.primaryDarkColor));
        textView4.setBackgroundColor(getResources().getColor(R.color.back));
        textView5.setBackgroundColor(getResources().getColor(R.color.back));
        textView6.setBackgroundColor(getResources().getColor(R.color.back));
        textView7.setBackgroundColor(getResources().getColor(R.color.back));
        textView8.setBackgroundColor(getResources().getColor(R.color.back));
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.bedrooms);
        linearLayout.setVisibility(View.GONE);
        LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.furnishing);
        linearLayout1.setVisibility(View.GONE);
        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.postedsince);
        linearLayout2.setVisibility(View.GONE);
        LinearLayout linearLayout3 = (LinearLayout) findViewById(R.id.availablefor);
        linearLayout3.setVisibility(View.GONE);
        LinearLayout linearLayout4 = (LinearLayout) findViewById(R.id.price);
        linearLayout4.setVisibility(View.GONE);
        LinearLayout linearLayout5 = (LinearLayout) findViewById(R.id.sortby);
        linearLayout5.setVisibility(View.GONE);
        LinearLayout linearLayout6 = (LinearLayout) findViewById(R.id.bathrooms);
        linearLayout6.setVisibility(View.GONE);
        LinearLayout linearLayout7 = (LinearLayout) findViewById(R.id.locality);
        linearLayout7.setVisibility(View.VISIBLE);

    }

    public void availablefilteroptions(View view) {
        TextView textView = (TextView) findViewById(R.id.sortfilteroptions);
        TextView textView1 = (TextView) findViewById(R.id.pricefilteroptions);
        TextView textView2= (TextView) findViewById(R.id.availablefilteroptions);
        TextView textView3 = (TextView) findViewById(R.id.localityfilteroptions);
        TextView textView4 = (TextView) findViewById(R.id.postedfilteroptions);
        TextView textView5 = (TextView) findViewById(R.id.furnishingfilteroptions);
        TextView textView6 = (TextView) findViewById(R.id.bedroomfilteroptions);
        TextView textView7 = (TextView) findViewById(R.id.bathroomfilteroptions);
        TextView textView8 = (TextView) findViewById(R.id.amenitiesfilteroptions);
        textView.setBackgroundColor(getResources().getColor(R.color.back));
        textView1.setBackgroundColor(getResources().getColor(R.color.back));
        textView2.setBackgroundColor(getResources().getColor(R.color.primaryDarkColor));
        textView3.setBackgroundColor(getResources().getColor(R.color.back));
        textView4.setBackgroundColor(getResources().getColor(R.color.back));
        textView5.setBackgroundColor(getResources().getColor(R.color.back));
        textView6.setBackgroundColor(getResources().getColor(R.color.back));
        textView7.setBackgroundColor(getResources().getColor(R.color.back));
        textView8.setBackgroundColor(getResources().getColor(R.color.back));
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.bedrooms);
        linearLayout.setVisibility(View.GONE);
        LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.furnishing);
        linearLayout1.setVisibility(View.GONE);
        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.postedsince);
        linearLayout2.setVisibility(View.GONE);
        LinearLayout linearLayout3 = (LinearLayout) findViewById(R.id.availablefor);
        linearLayout3.setVisibility(View.VISIBLE);
        LinearLayout linearLayout4 = (LinearLayout) findViewById(R.id.price);
        linearLayout4.setVisibility(View.GONE);
        LinearLayout linearLayout5 = (LinearLayout) findViewById(R.id.sortby);
        linearLayout5.setVisibility(View.GONE);
        LinearLayout linearLayout6 = (LinearLayout) findViewById(R.id.bathrooms);
        linearLayout6.setVisibility(View.GONE);
        LinearLayout linearLayout7 = (LinearLayout) findViewById(R.id.locality);
        linearLayout7.setVisibility(View.GONE);
    }

    public void pricefilteroptions(View view) {
        TextView textView = (TextView) findViewById(R.id.sortfilteroptions);
        TextView textView1 = (TextView) findViewById(R.id.pricefilteroptions);
        TextView textView2= (TextView) findViewById(R.id.availablefilteroptions);
        TextView textView3 = (TextView) findViewById(R.id.localityfilteroptions);
        TextView textView4 = (TextView) findViewById(R.id.postedfilteroptions);
        TextView textView5 = (TextView) findViewById(R.id.furnishingfilteroptions);
        TextView textView6 = (TextView) findViewById(R.id.bedroomfilteroptions);
        TextView textView7 = (TextView) findViewById(R.id.bathroomfilteroptions);
        TextView textView8 = (TextView) findViewById(R.id.amenitiesfilteroptions);
        textView.setBackgroundColor(getResources().getColor(R.color.back));
        textView1.setBackgroundColor(getResources().getColor(R.color.primaryDarkColor));
        textView2.setBackgroundColor(getResources().getColor(R.color.back));
        textView3.setBackgroundColor(getResources().getColor(R.color.back));
        textView4.setBackgroundColor(getResources().getColor(R.color.back));
        textView5.setBackgroundColor(getResources().getColor(R.color.back));
        textView6.setBackgroundColor(getResources().getColor(R.color.back));
        textView7.setBackgroundColor(getResources().getColor(R.color.back));
        textView8.setBackgroundColor(getResources().getColor(R.color.back));
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.bedrooms);
        linearLayout.setVisibility(View.GONE);
        LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.furnishing);
        linearLayout1.setVisibility(View.GONE);
        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.postedsince);
        linearLayout2.setVisibility(View.GONE);
        LinearLayout linearLayout3 = (LinearLayout) findViewById(R.id.availablefor);
        linearLayout3.setVisibility(View.GONE);
        LinearLayout linearLayout4 = (LinearLayout) findViewById(R.id.price);
        linearLayout4.setVisibility(View.VISIBLE);
        LinearLayout linearLayout5 = (LinearLayout) findViewById(R.id.sortby);
        linearLayout5.setVisibility(View.GONE);
        LinearLayout linearLayout6 = (LinearLayout) findViewById(R.id.bathrooms);
        linearLayout6.setVisibility(View.GONE);
        LinearLayout linearLayout7 = (LinearLayout) findViewById(R.id.locality);
        linearLayout7.setVisibility(View.GONE);
    }

    public void sortfilteroptions(View view) {
        TextView textView = (TextView) findViewById(R.id.sortfilteroptions);
        TextView textView1 = (TextView) findViewById(R.id.pricefilteroptions);
        TextView textView2= (TextView) findViewById(R.id.availablefilteroptions);
        TextView textView3 = (TextView) findViewById(R.id.localityfilteroptions);
        TextView textView4 = (TextView) findViewById(R.id.postedfilteroptions);
        TextView textView5 = (TextView) findViewById(R.id.furnishingfilteroptions);
        TextView textView6 = (TextView) findViewById(R.id.bedroomfilteroptions);
        TextView textView7 = (TextView) findViewById(R.id.bathroomfilteroptions);
        TextView textView8 = (TextView) findViewById(R.id.amenitiesfilteroptions);
        textView.setBackgroundColor(getResources().getColor(R.color.primaryDarkColor));
        textView1.setBackgroundColor(getResources().getColor(R.color.back));
        textView2.setBackgroundColor(getResources().getColor(R.color.back));
        textView3.setBackgroundColor(getResources().getColor(R.color.back));
        textView4.setBackgroundColor(getResources().getColor(R.color.back));
        textView5.setBackgroundColor(getResources().getColor(R.color.back));
        textView6.setBackgroundColor(getResources().getColor(R.color.back));
        textView7.setBackgroundColor(getResources().getColor(R.color.back));
        textView8.setBackgroundColor(getResources().getColor(R.color.back));
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.bedrooms);
        linearLayout.setVisibility(View.GONE);
        LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.furnishing);
        linearLayout1.setVisibility(View.GONE);
        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.postedsince);
        linearLayout2.setVisibility(View.GONE);
        LinearLayout linearLayout3 = (LinearLayout) findViewById(R.id.availablefor);
        linearLayout3.setVisibility(View.GONE);
        LinearLayout linearLayout4 = (LinearLayout) findViewById(R.id.price);
        linearLayout4.setVisibility(View.GONE);
        LinearLayout linearLayout5 = (LinearLayout) findViewById(R.id.sortby);
        linearLayout5.setVisibility(View.VISIBLE);
        LinearLayout linearLayout6 = (LinearLayout) findViewById(R.id.bathrooms);
        linearLayout6.setVisibility(View.GONE);
        LinearLayout linearLayout7 = (LinearLayout) findViewById(R.id.locality);
        linearLayout7.setVisibility(View.GONE);
    }

    public void applyfilters(View view) {
        RadioGroup sortby= (RadioGroup) findViewById(R.id.radiosortby);
        int selectedID =sortby.getCheckedRadioButtonId();
        RadioButton sRB= (RadioButton) findViewById(selectedID);
        String sortfilter= (String) sRB.getText();
        Intent intent=new Intent(FilterOptions.this,NeedHome.class);
        intent.putExtra("sort",sortfilter);
        startActivity(intent);

    }
}
