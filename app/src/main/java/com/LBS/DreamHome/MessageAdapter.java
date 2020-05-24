package com.LBS.DreamHome;

import android.app.Activity;
import android.content.Context;

import androidx.core.app.ActivityCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MessageAdapter extends ArrayAdapter<FriendlyMessage> {
    private Context mContext;

    public MessageAdapter(Context context, int resource, List<FriendlyMessage> objects) {
        super(context, resource, objects);
        this.mContext = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_message, parent, false);
        }

        ImageView photoImageView = (ImageView) convertView.findViewById(R.id.photoImageView);
        TextView messageTextView = (TextView) convertView.findViewById(R.id.messageTextView);
        TextView authorTextView = (TextView) convertView.findViewById(R.id.nameTextView);
        TextView areaTextView = (TextView) convertView.findViewById(R.id.messagearea);
        TextView OwnerTextView = (TextView) convertView.findViewById(R.id.Owner);
        TextView distTextView = (TextView) convertView.findViewById(R.id.dist);
        ImageButton button = (ImageButton) convertView.findViewById(R.id.phonecall);
        ImageButton button1 = (ImageButton) convertView.findViewById(R.id.shortlists);

        final FriendlyMessage message = getItem(position);

        boolean isPhoto = message.geturl() != null;
        if (isPhoto) {
            messageTextView.setVisibility(View.VISIBLE);
            photoImageView.setVisibility(View.VISIBLE);
            Glide.with(photoImageView.getContext())
                    .load(message.geturl())
                    .into(photoImageView);
        } else {

            messageTextView.setVisibility(View.VISIBLE);
            photoImageView.setVisibility(View.VISIBLE);

        }
        double lat = message.getLat();
        double lng = message.getLng();
        double dist;
        if (lat != 0 && lng != 0) {
            if (mContext instanceof NeedHome) {
                dist = ((NeedHome) mContext).distance(lat, lng);
                if (dist > 1000) {
                    dist = dist / 1000;
                    dist = Math.round(dist * 100.0) / 100.0;
                    distTextView.setText(String.valueOf(dist) + "Km");
                } else {
                    dist = Math.round(dist * 100.0) / 100.0;

                    distTextView.setText(String.valueOf(dist) + "M");
                }
            }
        }

        messageTextView.setText("\u20B9" + message.getPrice());
        authorTextView.setText(message.getBedRooms() + " BHK");
        areaTextView.setText(message.getArea());
        OwnerTextView.setText(message.getOwner());
        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Intent callIntent = new Intent(Intent.ACTION_CALL);
                // callIntent.setData(Uri.parse("tel:" + message.getPhoneNo()));

                // this.startActivity(callIntent);
                if (mContext instanceof NeedHome) {
                    ((NeedHome) mContext).phoneCall(message.getPhoneNo());
                }

            }
        });
        button1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Intent callIntent = new Intent(Intent.ACTION_CALL);
                // callIntent.setData(Uri.parse("tel:" + message.getPhoneNo()));

                // this.startActivity(callIntent);
                if (mContext instanceof NeedHome) {
                    ((NeedHome) mContext).setfav(position, view);
                }

            }
        });
        return convertView;
    }


}