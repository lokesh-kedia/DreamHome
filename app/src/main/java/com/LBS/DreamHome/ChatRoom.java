package com.LBS.DreamHome;


import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ChatRoom extends AppCompatActivity {
    private int backButtonCount = 0;
    private static final int NOTIFICATION_ID = 456;
    private EditText msg;
    private String title, ruid;
    private String senderUid;
    private String receiverUid;
    private DrawerLayout mDrawerLayout;
    private String room_type_1;
    private String room_type_2;
    private NotificationManager notificationManager;
    private String Owner;
    private List<ChatMessage> chatMessages;
    private RecyclerView mMessageRecycler;
    private ChatAdapter mMessageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        mMessageRecycler = (RecyclerView) findViewById(R.id.msgss);
        chatMessages = new ArrayList<>();
        mMessageAdapter = new ChatAdapter(this, chatMessages);
        mMessageRecycler.setAdapter(mMessageAdapter);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(ChatRoom.this);
        layoutManager.setStackFromEnd(true);
        mMessageRecycler.setLayoutManager(layoutManager);
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
                        menuItem.setChecked(true);

                        mDrawerLayout.closeDrawers();

                        if (menuItem.getTitle().equals("Dashboard")) {
                            Intent i = new Intent(ChatRoom.this, MainActivity.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("Have Home")) {
                            Intent i = new Intent(ChatRoom.this, HaveHome.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("Need Home")) {
                            Intent i = new Intent(ChatRoom.this, Filter.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("About us")) {
                            Intent i = new Intent(ChatRoom.this, aboutus.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("Profile")) {
                            Intent i = new Intent(ChatRoom.this, Profile.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("My preferences")) {
                            Intent i = new Intent(ChatRoom.this, preferences.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("My shortlists")) {
                            Intent i = new Intent(ChatRoom.this, Shortlist.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("My Houses")) {
                            Intent i = new Intent(ChatRoom.this, Uploads.class);
                            startActivity(i);
                        }
                        return true;
                    }
                });
        Bundle b = getIntent().getExtras();
        msg = (EditText) findViewById(R.id.msg);
        ruid = b.getString("rphone");
        senderUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Owner = FirebaseAuth.getInstance().getCurrentUser().getUid();
        receiverUid = ruid;
        receiveMessageFromUser();
        //  receiveMessageFromUser();
    }


    public void send(View view) {
        String message = String.valueOf(msg.getText());
        msg.setText("");
        if (!message.isEmpty()) {
            String senderUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            String name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("hh:mm a");
            String formattedDate = df.format(c);
            sendMessageToFirebaseUser(this, message, senderUid, formattedDate, name);
            // receiveMessageFromUser();
        }
    }

    public void receiveMessageFromUser() {
        //mMessageAdapter = new ChatAdapter(this, R.layout.item_message1, chatMessages);
        //mMessageRecycler.setAdapter(mMessageAdapter);
        if (!ruid.contains("_")) {
            room_type_1 = senderUid + "_" + receiverUid;
            room_type_2 = receiverUid + "_" + senderUid;
        } else {
            room_type_1 = receiverUid;
            room_type_2 = receiverUid + "_" + senderUid;
        }
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference();
        databaseReference.child(Constants.ARG_CHAT_ROOMS)
                .getRef()
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(room_type_1)) {
                            final DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                                    .getReference()
                                    .child(Constants.ARG_CHAT_ROOMS)
                                    .child(room_type_1);

                            databaseReference.addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, @Nullable String s) {
                                    ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                                    chatMessages.add(chatMessage);

                                    mMessageAdapter = new ChatAdapter(ChatRoom.this, chatMessages);
                                    mMessageRecycler.setAdapter(mMessageAdapter);

                                }

                                @Override
                                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                                }

                                @Override
                                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                                }

                                @Override
                                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        } else if (dataSnapshot.hasChild(room_type_2)) {
                            final DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                                    .getReference()
                                    .child(Constants.ARG_CHAT_ROOMS)
                                    .child(room_type_2);

                            databaseReference.addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                    ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                                    chatMessages.add(chatMessage);
                                    //Toast.makeText(ChatRoom.this, chatMessage.getMessage(), Toast.LENGTH_SHORT).show();

                                    mMessageAdapter = new ChatAdapter(ChatRoom.this, chatMessages);
                                    mMessageRecycler.setAdapter(mMessageAdapter);

                                }

                                @Override
                                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                                }

                                @Override
                                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                                }

                                @Override
                                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        } else {

                        }

                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Unable to get message
                    }
                });


    }


    public void sendMessageToFirebaseUser(final Context context,
                                          final String chat,
                                          final String sender,
                                          final String time,
                                          final String name) {
        if (!ruid.contains("_")) {
            room_type_1 = sender + "_" + receiverUid;
            room_type_2 = receiverUid + "_" + sender;
        } else {
            room_type_1 = receiverUid;
            room_type_2 = receiverUid;
        }
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference();
        final DatabaseReference databaseReference1 = FirebaseDatabase.getInstance()
                .getReference().child(Constants.ARG_CHAT_ROOMS)
                .child(room_type_1);
        final DatabaseReference databaseReference2 = FirebaseDatabase.getInstance()
                .getReference().child(Constants.ARG_CHAT_ROOMS)
                .child(room_type_2);


        databaseReference.child(Constants.ARG_CHAT_ROOMS)
                .getRef()
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(room_type_1)) {
                            String key = databaseReference1.push().getKey();
                            ChatMessage chatMessage = new ChatMessage(chat, sender, time, name);
                            databaseReference1.child(key).setValue(chatMessage);

                        } else if (dataSnapshot.hasChild(room_type_2)) {
                            String key = databaseReference2.push().getKey();
                            ChatMessage chatMessage = new ChatMessage(chat, sender, time, name);
                            databaseReference2.child(key).setValue(chatMessage);

                        } else {
                            String key = databaseReference.child(Constants.ARG_CHAT_ROOMS)
                                    .child(room_type_1).push().getKey();
                            ChatMessage chatMessage = new ChatMessage(chat, sender, time, name);
                            databaseReference.child(Constants.ARG_CHAT_ROOMS)
                                    .child(room_type_1).child(key).setValue(chatMessage);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {


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
                Intent i = new Intent(ChatRoom.this, ChatList.class);
                startActivity(i);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onBackPressed() {
        if (backButtonCount >= 1) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            super.onBackPressed();
            Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }

    @Override
    protected void onStart() {
        backButtonCount = 0;
        super.onStart();
    }
}






