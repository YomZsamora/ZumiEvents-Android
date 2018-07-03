package com.adzumi.zumievents.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.adzumi.zumievents.R;
import com.adzumi.zumievents.models.Event;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CurrentLocationAdapter extends RecyclerView.Adapter<CurrentLocationAdapter.CustomViewHolder> {

    private List<Event> mEvents;
    private Context context;

    public CurrentLocationAdapter(Context context,List<Event> mEvents){
        this.context = context;
        this.mEvents = mEvents;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        TextView eventTitle;
        TextView eventDescription;
        TextView eventDate;
        private ImageView coverImage;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            eventTitle = mView.findViewById(R.id.eventTitleTextView);
            eventDescription = mView.findViewById(R.id.eventDescriptionTextView);
            eventDate = mView.findViewById(R.id.eventDateTextView);
            coverImage = mView.findViewById(R.id.eventImageView);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.layout_current_location, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.eventTitle.setText(mEvents.get(position).getName().getText());
        holder.eventDescription.setText(mEvents.get(position).getDescription().getText());
        holder.eventDate.setText(mEvents.get(position).getCreated());

        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(mEvents.get(position).getLogo().getOriginal().getUrl())
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_background)
                .into(holder.coverImage);

    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }
}
