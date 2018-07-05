package com.adzumi.zumievents.ui;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.adzumi.zumievents.R;
import com.adzumi.zumievents.adapters.EventPagerAdapter;
import com.adzumi.zumievents.models.Event;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventDetailActivity extends AppCompatActivity {

    @BindView(R.id.viewPager) ViewPager mViewPager;
    private EventPagerAdapter adapterViewPager;
    ArrayList<Event> mEvent = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        ButterKnife.bind(this);

        mEvent = Parcels.unwrap(getIntent().getParcelableExtra("event"));
        int startingPosition = getIntent().getIntExtra("position", 0);

        adapterViewPager = new EventPagerAdapter(getSupportFragmentManager(), mEvent);
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(startingPosition);
    }
}
