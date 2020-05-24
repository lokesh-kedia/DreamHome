package com.LBS.DreamHome;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeInfo extends AppCompatActivity {
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    private String State, City, Area, Plot;
    private ValueEventListener valueEventListener;
    private TextView ET1, ET2, ET3, ET4, ET5, ET6, ET7, ET8, ET9, ET10, ET11, ET12, ET13, ET14, ET15, ET16, ET17, ET18, ET19, ET20;
    private String S1, S2, S3, S4, S5, S6, S7, S8, S9, S10, S11, S12, S13, S14, S15, S16, S17, S18, S19, S20;
    private HomeData homeData;
    private ImageView imageView, IM10, IM11, IM12, IM13, IM14, IM15, IM16;
    private boolean isImageFitToScreen;
    private LinearLayout LL10, LL11, LL12, LL13, LL14, LL15, LL16;
    private double plotlat;
    private GoogleMap mMap;
    private double plotlng;
    private DrawerLayout mDrawerLayout;
private int backButtonCount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_info);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.mipmap.menu1);
        Switch s = (Switch) findViewById(R.id.avail);
        s.setVisibility(View.GONE);
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
                            Intent i = new Intent(HomeInfo.this, MainActivity.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("Have Home")) {
                            Intent i = new Intent(HomeInfo.this, HaveHome.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("Need Home")) {
                            Intent i = new Intent(HomeInfo.this, Filter.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("About us")) {
                            Intent i = new Intent(HomeInfo.this, aboutus.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("Profile")) {
                            Intent i = new Intent(HomeInfo.this, Profile.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("My preferences")) {
                            Intent i = new Intent(HomeInfo.this, preferences.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("My shortlists")) {
                            Intent i = new Intent(HomeInfo.this, Shortlist.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("My Houses")) {
                            Intent i = new Intent(HomeInfo.this, Uploads.class);
                            startActivity(i);
                        }
                        return true;
                    }
                });

        // FirebaseDatabase firebaseDatabase;
        /// DatabaseReference databaseReference;

        Bundle bundle = getIntent().getExtras();
        State = bundle.getString("StateFilter");
        City = bundle.getString("CityFilter");
        Area = bundle.getString("AreaFilter");
        DatabaseReference databaseReference;
        Plot = bundle.getString("PlotFilter");
        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b == true) {
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("States").child(Plot);
                    databaseReference1.child("Avail").setValue("True");
                    Snackbar.make(getWindow().getDecorView(), "Home Is Set To Available", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("States").child(Plot);
                    databaseReference1.child("Avail").setValue("False");
                    Snackbar.make(getWindow().getDecorView(), "Home Is Set To Not Available", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        });
        if (Plot.contains("_") || Plot.contains("/")) {
            Plot = Plot.replaceAll("_", "/");
            databaseReference = FirebaseDatabase.getInstance()
                    .getReference().child("States").child(Plot).getRef();
            if (bundle.getString("type").equals("uploads")) {
                s.setVisibility(View.VISIBLE);

                Button button = (Button) findViewById(R.id.infocall);
                Button button1 = (Button) findViewById(R.id.infochat);
                button.setVisibility(View.GONE);
                button1.setText("Edit");
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(HomeInfo.this, HaveHome.class);
                        i.putExtra("homeData", homeData);
                        i.putExtra("plotData", Plot);

                        startActivity(i);

                    }
                });
            }

        } else {
            State = bundle.getString("StateFilter");
            City = bundle.getString("CityFilter");
            Area = bundle.getString("AreaFilter");

            // Toast.makeText(this,State+City+Area+Plot,Toast.LENGTH_SHORT).show();
            this.setTitle(Plot + "," + Area + "," + City);

            databaseReference = FirebaseDatabase.getInstance()
                    .getReference().child("States").child(State).child(City).child(Area).child(Plot).getRef();
        }

        databaseReference
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        homeData = dataSnapshot.getValue(HomeData.class);
                        // Toast.makeText(HomeInfo.this,String.valueOf(dataSnapshot.child("PhoneNo").getValue()),Toast.LENGTH_LONG).show();
                        S6 = homeData.Agreement;
                        S7 = homeData.BedRooms;
                        S8 = homeData.BathRooms;
                        S9 = homeData.Toilets;
                        S10 = homeData.WardRobe;
                        S11 = homeData.Beds;
                        S12 = homeData.Fans;
                        S13 = homeData.Lights;
                        S14 = homeData.ModularKitchen;
                        S15 = homeData.AC;
                        S16 = homeData.Inverter;
                        S17 = homeData.url;
                        S18 = homeData.Price;
                        S19 = homeData.BedRooms;
                        S20 = homeData.RentTo;
                        S5 = homeData.Owner;
                        S4 = homeData.Date;
                        plotlat = homeData.lat;
                        plotlng = homeData.lng;
                        mapmark(plotlng, plotlat);
                        imageView = (ImageView) findViewById(R.id.imgView);
                        Glide.with(HomeInfo.this).load(S17).into(imageView);
                        ET6 = (TextView) findViewById(R.id.agreement);
                        ET6.setText("\u2022" + S6 + "Years Agreement");
                        ET7 = (TextView) findViewById(R.id.bedrooms);
                        ET7.setText(S7);
                        ET8 = (TextView) findViewById(R.id.bathrooms);
                        ET8.setText(S8);
                        ET9 = (TextView) findViewById(R.id.toilets);
                        ET9.setText(S9);
                        ET10 = (TextView) findViewById(R.id.Owner);
                        ET10.setText(S5 + "(" + S4 + ")");
                        LL10 = (LinearLayout) findViewById(R.id.wardrobeL);
                        //           ET10.append(S10);
                        LL11 = (LinearLayout) findViewById(R.id.BedL);
                        //             ET11.append(S11);
                        LL12 = (LinearLayout) findViewById(R.id.fansL);
                        //               ET12.append(S12);
                        LL13 = (LinearLayout) findViewById(R.id.lightsL);
                        //                 ET13.append(S13);
                        LL14 = (LinearLayout) findViewById(R.id.modularkitchenL);
                        //                   ET14.append(S14);
                        LL15 = (LinearLayout) findViewById(R.id.acL);
                        //                      ET15.append(S15);
                        LL16 = (LinearLayout) findViewById(R.id.inverterL);
//                        ET16.append(S16);
                        ET17 = (TextView) findViewById(R.id.area);
                        ET17.setText(Area);
                        ET18 = (TextView) findViewById(R.id.price);
                        ET18.setText("\u20B9" + S18);
                        ET19 = (TextView) findViewById(R.id.bhk);
                        ET19.setText(S19 + " BHK");
                        ET20 = (TextView) findViewById(R.id.For);
                        ET20.setText(S20);
                        IM10 = (ImageView) findViewById(R.id.wardrobe);
                        IM11 = (ImageView) findViewById(R.id.beds);
                        IM12 = (ImageView) findViewById(R.id.fans);
                        IM13 = (ImageView) findViewById(R.id.lights);
                        IM14 = (ImageView) findViewById(R.id.modularkitchen);
                        IM15 = (ImageView) findViewById(R.id.ac);
                        IM16 = (ImageView) findViewById(R.id.inverter);

                        if (S10.equals("No")) {
                            LL10.setVisibility(View.GONE);
                        }
                        if (S11.equals("No")) {
                            LL11.setVisibility(View.GONE);
                        }
                        if (S12.equals("No")) {
                            LL12.setVisibility(View.GONE);
                        }
                        if (S13.equals("No")) {
                            LL13.setVisibility(View.GONE);
                        }
                        if (S14.equals("No")) {
                            LL14.setVisibility(View.GONE);
                        }
                        if (S15.equals("No")) {
                            LL15.setVisibility(View.GONE);
                        }
                        if (S16.equals("No")) {
                            LL16.setVisibility(View.GONE);
                        }
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
//                                if(isImageFitToScreen) {
//                                    isImageFitToScreen=false;
//                                    imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//                                    imageView.setAdjustViewBounds(true);
//                                }else{
//                                    isImageFitToScreen=true;
//                                    imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
//                                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//                                }


                            }
                        });

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }


    @Override
    protected void onResume() {
        super.onResume();

    }


    public void call(View view) {
        if (Build.VERSION.SDK_INT < 23) {
            phoneCall();
        } else {

            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

                phoneCall();
            } else {
                final String[] PERMISSIONS_STORAGE = {android.Manifest.permission.CALL_PHONE};
                //Asking request Permissions
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 9);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        boolean permissionGranted = false;
        switch (requestCode) {
            case 9:
                permissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (permissionGranted) {
            phoneCall();
        } else {
            Toast.makeText(this, "You don't assign permission.", Toast.LENGTH_SHORT).show();
        }
    }

    public void phoneCall() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + homeData.PhoneNo));

            this.startActivity(callIntent);
        } else {
            Toast.makeText(this, "You don't assign permission.", Toast.LENGTH_SHORT).show();
        }
    }


    public void chat(View view) {
        Intent i = new Intent(HomeInfo.this, ChatRoom.class);

        String rphone = homeData.UID;

        i.putExtra("rphone", rphone);
        startActivity(i);

    }

    public void mapmark(final double plotlng, final double plotlat) {
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                LatLng loc = new LatLng(plotlat, plotlng);
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(loc)
                        .zoom(18)
                        .build();
                int height = 50;
                int width = 60;
                BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.mipmap.plot);
                Bitmap b = bitmapdraw.getBitmap();
                Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
                mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
                        .position(loc).draggable(false));
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),
                        2000, null);
            }
        });
        final ScrollView scrollView = (ScrollView) findViewById(R.id.scrview);
        ((mapfrag) getSupportFragmentManager().findFragmentById(R.id.map)).setListener(new mapfrag.OnTouchListener() {
            @Override
            public void onTouch() {
                scrollView.requestDisallowInterceptTouchEvent(true);
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
                Intent i = new Intent(HomeInfo.this, ChatList.class);
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
