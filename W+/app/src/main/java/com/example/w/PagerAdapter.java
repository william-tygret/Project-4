package com.example.w;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.w.Fragments.AnimationFragment;
import com.example.w.Fragments.ContactFragment;
import com.example.w.Fragments.GalleryFragment;
import com.example.w.Fragments.ProjectsFragment;
import com.example.w.Fragments.ResumeFragment;
import com.example.w.Fragments.SocialFragment;

/**
 * Created by williamtygret on 3/22/16.
 */
public class PagerAdapter extends FragmentPagerAdapter {

    public PagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ResumeFragment();
            case 1:
                return new ContactFragment();
            case 2:
                return new SocialFragment();
            case 3:
                return new ProjectsFragment();
            case 4:
                return new GalleryFragment();
            case 5:
                return new AnimationFragment();
            default:
                break;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 6;
    }


}
