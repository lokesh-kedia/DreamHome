package com.LBS.DreamHome;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ChatListAdapter extends ArrayAdapter<User> {
    public ChatListAdapter(Context context, int resource, List<User> objects) {
        super(context, resource, objects);

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_chatlist, parent, false);
        }

        ImageView photoImageView = (ImageView) convertView.findViewById(R.id.photoImageView);
        TextView messageTextView = (TextView) convertView.findViewById(R.id.messageTextView);

        final User message = getItem(position);

        boolean isPhoto = message.getPhoto() != null;
        if (isPhoto) {
            messageTextView.setVisibility(View.VISIBLE);
            photoImageView.setVisibility(View.VISIBLE);
            Glide.with(photoImageView.getContext())
                    .load(message.getPhoto())
                    .into(photoImageView);
        } else {

            messageTextView.setVisibility(View.VISIBLE);
            photoImageView.setVisibility(View.VISIBLE);

        }
        messageTextView.setText(message.getName());


        return convertView;
    }
}
