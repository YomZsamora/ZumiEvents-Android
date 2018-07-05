package com.adzumi.zumievents.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.adzumi.zumievents.R;
import com.adzumi.zumievents.models.Event;
import com.adzumi.zumievents.ui.EventDetailActivity;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CurrentLocationAdapter extends RecyclerView.Adapter<CurrentLocationAdapter.CustomViewHolder> {

    private List<Event> mEvents;
    private Context context;

    public CurrentLocationAdapter(Context context,List<Event> mEvents){
        this.context = context;
        this.mEvents = mEvents;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final View mView;

        @BindView(R.id.eventTitleTextView) TextView eventTitle;
        @BindView(R.id.eventDescriptionTextView) TextView eventDescription;
        @BindView(R.id.eventDateTextView) TextView eventDate;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            ButterKnife.bind(this, mView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int itemPosition = getAdapterPosition();
            Intent intent = new Intent(context, EventDetailActivity.class);
            intent.putExtra("position", itemPosition);
            intent.putExtra("event", Parcels.wrap(mEvents));
            context.startActivity(intent);
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
        holder.eventDescription.setMovementMethod(new ScrollingMovementMethod());
        holder.eventTitle.setText(mEvents.get(position).getName().getText());
        holder.eventDescription.setText(mEvents.get(position).getDescription().getText());
        holder.eventDate.setText(mEvents.get(position).getCreated());

//        Picasso.Builder builder = new Picasso.Builder(context);
//        builder.downloader(new OkHttp3Downloader(context));
//        builder.build().load(mEvents.get(position).getLogo().getUrl())
//                .placeholder((R.drawable.ic_launcher_background))
//                .error(R.drawable.ic_launcher_background)
//                .into(holder.coverImage);

    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }
}
