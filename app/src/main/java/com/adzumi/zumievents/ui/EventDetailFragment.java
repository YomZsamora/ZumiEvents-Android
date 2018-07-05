package com.adzumi.zumievents.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.adzumi.zumievents.Constants;
import com.adzumi.zumievents.R;
import com.adzumi.zumievents.models.Event;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.text.NumberFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


public class EventDetailFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.bookImageDetail)
    ImageView mBookImageDetail;
    @BindView(R.id.bookTitleDetail)
    TextView mBookTitleDetail;
    @BindView(R.id.bookAuthorName) TextView mBookAuthorName;
    @BindView(R.id.ratingBar2)
    RatingBar mBookRatingBar;
    @BindView(R.id.rateTextView) TextView mRateTextView;
    @BindView(R.id.ratingsTextView) TextView mRatingsTextView;
    @BindView(R.id.saveBookButton)
    Button mSaveBookButton;

    private Event mEvent;

    public static EventDetailFragment newInstance(Event event) {
        EventDetailFragment eventDetailFragment = new EventDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("event", Parcels.wrap(event));
        eventDetailFragment.setArguments(args);
        return eventDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEvent = Parcels.unwrap(getArguments().getParcelable("event"));
    }

    @Override
    public void onClick(View view){
        if(view == mSaveBookButton){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            DatabaseReference mSavedBookReference = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.FIREBASE_CHILD_FAVORITES)
                    .child(uid);
            DatabaseReference pushRef = mSavedBookReference.push();
            String pushId = pushRef.getKey();
            mEvent.setPushId(pushId);
            pushRef.setValue(mEvent);

            Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_detail, container, false);
        ButterKnife.bind(this, view);

        mSaveBookButton.setOnClickListener(this);


        Picasso.with(view.getContext()).load(mEvent.getLogo().getUrl()).fit().into(mBookImageDetail);
        mBookTitleDetail.setText(mEvent.getName().getText());
        mBookAuthorName.setText(mEvent.getDescription().getText());
//        mBookRatingBar.setRating(Float.valueOf(mWork.getAverageRating()));
//        mRateTextView.setText(mWork.getAverageRating());
//        mRatingsTextView.setText("(" + formatter.format(bookRatings) + " Ratings)");

        return view;
    }
}
