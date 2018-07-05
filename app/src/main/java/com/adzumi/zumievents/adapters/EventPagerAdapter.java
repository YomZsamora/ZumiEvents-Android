package com.adzumi.zumievents.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.adzumi.zumievents.models.Event;
import com.adzumi.zumievents.ui.EventDetailFragment;

import java.util.ArrayList;

public class EventPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Event> mEvent;

    public EventPagerAdapter(FragmentManager fm, ArrayList<Event> event) {
        super(fm);
        mEvent = event;
    }

    @Override
    public Fragment getItem(int position) {
        return EventDetailFragment.newInstance(mEvent.get(position));
    }

    @Override
    public int getCount() {
        return mEvent.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mEvent.get(position).getName().getText();
    }
}
