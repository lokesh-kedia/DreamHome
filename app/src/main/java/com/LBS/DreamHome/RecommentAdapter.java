package com.LBS.DreamHome;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class RecommentAdapter extends ArrayAdapter<FriendlyMessage> {
    private Context mContext;

    public RecommentAdapter(@NonNull Context context, int resource, List<FriendlyMessage> objects) {
        super(context, resource, objects);
        this.mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_home, parent, false);
        }
        ImageView imageView = (ImageView) convertView.findViewById(R.id.homeimage);
        TextView textView = (TextView) convertView.findViewById(R.id.date_owner);
        TextView textView1 = (TextView) convertView.findViewById(R.id.price_bhk);
        TextView textView2 = (TextView) convertView.findViewById(R.id.call);
        final FriendlyMessage message = getItem(position);
        boolean isPhoto = message.geturl() != null;
        if (isPhoto) {
            imageView.setVisibility(View.VISIBLE);
            Glide.with(imageView.getContext())
                    .load(message.geturl())
                    .into(imageView);
        } else {
            imageView.setVisibility(View.VISIBLE);
        }
        textView.setText(message.getDate() + "|" + message.getOwner());
        textView1.setText("\u20B9" + message.getPrice() + "|" + message.getBedRooms() + " BHK");
        textView2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Intent callIntent = new Intent(Intent.ACTION_CALL);
                // callIntent.setData(Uri.parse("tel:" + message.getPhoneNo()));

                // this.startActivity(callIntent);
                if (mContext instanceof Recommend) {
                    ((Recommend) mContext).phoneCall(message.getPhoneNo());
                }

            }
        });
        return convertView;
    }
}
