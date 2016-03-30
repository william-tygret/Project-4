package com.example.w;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.graphics.Color;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.*;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.w.Fragments.ContactFragment;
import com.example.w.Fragments.ResumeFragment;
import com.example.w.Fragments.SocialFragment;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getCanonicalName();
    public static final String AUTHORITY = "com.example.w.ContentProvider";

    ViewPager mFragmentSpace;
    android.support.design.widget.FloatingActionButton fabResume;
    FloatingActionButton fabContact;
    FloatingActionButton fabSocial;
    FloatingActionButton fabProjects;
    FloatingActionButton fabGallery;
    FloatingActionButton fabAnimation;
    android.support.v7.widget.Toolbar mToolbar;
    ImageView mAndroidArm;

    private Account mAccount;

    CardView mAboutCardView;
    TextView mAboutDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(mToolbar);

        mAccount = createSyncAccount(this);

        fabResume = (android.support.design.widget.FloatingActionButton)findViewById(R.id.fabResume);
        fabContact = (FloatingActionButton)findViewById(R.id.fabContact);
        fabSocial = (android.support.design.widget.FloatingActionButton)findViewById(R.id.fabSocial);
        fabProjects = (android.support.design.widget.FloatingActionButton)findViewById(R.id.fabProjects);
        fabGallery = (android.support.design.widget.FloatingActionButton)findViewById(R.id.fabGallery);
        fabAnimation = (android.support.design.widget.FloatingActionButton)findViewById(R.id.fabAnimation);
        final Animation zoomAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.button_enlarge);
        fabResume.startAnimation(zoomAnimation);
        mToolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.toolBar);
        mToolbar.setBackgroundColor(Color.rgb(62, 123, 45));
        mToolbar.setTitle("Resume");

        mAndroidArm = (ImageView)findViewById(R.id.androidArm);
        final Animation waveAnimation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.armwave);
        waveAnimation.setRepeatCount(20);
        mAndroidArm.startAnimation(waveAnimation);

        ContentResolver.setIsSyncable(mAccount,ContentProvider.AUTHORITY,1);
        ContentResolver.setSyncAutomatically(mAccount, AUTHORITY, true);
        ContentResolver.addPeriodicSync(
                mAccount,
                AUTHORITY,
                Bundle.EMPTY,
                600);

        ContentResolver.requestSync(mAccount, ContentProvider.AUTHORITY,Bundle.EMPTY);

//        getContentResolver().registerContentObserver(ContentProvider.BASE_URI_STRING,true,new SocialFragment.InstagramContentObserver());


        mFragmentSpace = (ViewPager)findViewById(R.id.fragmentSpace);
        final com.example.w.PagerAdapter pagerAdapter = new com.example.w.PagerAdapter(getSupportFragmentManager());
        mFragmentSpace.setAdapter(pagerAdapter);

        fabResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragmentSpace.setCurrentItem(0);
            }
        });
        fabContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragmentSpace.setCurrentItem(1);
            }
        });
        fabSocial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragmentSpace.setCurrentItem(2);
            }
        });
        fabProjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragmentSpace.setCurrentItem(3);
            }
        });
        fabGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragmentSpace.setCurrentItem(4);
            }
        });
        fabAnimation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragmentSpace.setCurrentItem(5);
            }
        });

        mFragmentSpace.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                fabResume = (android.support.design.widget.FloatingActionButton)findViewById(R.id.fabResume);
                fabContact = (FloatingActionButton)findViewById(R.id.fabContact);
                fabSocial = (android.support.design.widget.FloatingActionButton)findViewById(R.id.fabSocial);
                fabProjects = (android.support.design.widget.FloatingActionButton)findViewById(R.id.fabProjects);
                fabGallery = (android.support.design.widget.FloatingActionButton)findViewById(R.id.fabGallery);
                fabAnimation = (android.support.design.widget.FloatingActionButton)findViewById(R.id.fabAnimation);
                mToolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.toolBar);

                mAndroidArm = (ImageView)findViewById(R.id.androidArm);
                final Animation waveAnimation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.armwave);

                setSupportActionBar(mToolbar);
                if(position==0){
                    Log.d("PageSelect","this is page: "+position);
                    //fabResume = (android.support.design.widget.FloatingActionButton)findViewById(R.id.fabResume);
                    Animation zoomAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.button_enlarge);
                    fabResume.startAnimation(zoomAnimation);
                    mToolbar.setBackgroundColor(Color.rgb(62, 123, 45));
                    mToolbar.setTitle("Resume");
                    mAndroidArm.startAnimation(waveAnimation);
                    fabContact.clearAnimation();
                    fabSocial.clearAnimation();
                    fabProjects.clearAnimation();
                    fabGallery.clearAnimation();
                    fabAnimation.clearAnimation();
                }else if(position==1){
                    Log.d("PageSelect", "this is page: " + position);
                    Animation zoomAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.button_enlarge);
                    fabContact.startAnimation(zoomAnimation);
                    mToolbar.setBackgroundColor(Color.rgb(174, 36, 18));
                    mToolbar.setTitle("Contact");
                    mAndroidArm.startAnimation(waveAnimation);
                    fabResume.clearAnimation();
                    fabSocial.clearAnimation();
                    fabProjects.clearAnimation();
                    fabGallery.clearAnimation();
                    fabAnimation.clearAnimation();
                }else if(position==2){
                    Animation zoomAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.button_enlarge);
                    fabSocial.startAnimation(zoomAnimation);
                    mToolbar.setBackgroundColor(Color.rgb(31, 127, 199));
                    mToolbar.setTitle("Social Media");
                    mAndroidArm.startAnimation(waveAnimation);
                    fabResume.clearAnimation();
                    fabContact.clearAnimation();
                    fabProjects.clearAnimation();
                    fabGallery.clearAnimation();
                    fabAnimation.clearAnimation();
                }else if(position==3){
                    Animation zoomAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.button_enlarge);
                    fabProjects.startAnimation(zoomAnimation);
                    mToolbar.setBackgroundColor(Color.rgb(188, 121, 28));
                    mToolbar.setTitle("Professional Projects");
                    mAndroidArm.startAnimation(waveAnimation);
                    fabResume.clearAnimation();
                    fabContact.clearAnimation();
                    fabSocial.clearAnimation();
                    fabGallery.clearAnimation();
                    fabAnimation.clearAnimation();
                }else if(position ==4){
                    Animation zoomAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.button_enlarge);
                    fabGallery.startAnimation(zoomAnimation);
                    mToolbar.setBackgroundColor(Color.rgb(12, 69, 78));
                    mToolbar.setTitle("Art Gallery");
                    mAndroidArm.startAnimation(waveAnimation);
                    fabResume.clearAnimation();
                    fabContact.clearAnimation();
                    fabSocial.clearAnimation();
                    fabProjects.clearAnimation();
                    fabAnimation.clearAnimation();
                }else if(position==5){
                    Animation zoomAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.button_enlarge);
                    fabAnimation.startAnimation(zoomAnimation);
                    mToolbar.setBackgroundColor(Color.rgb(92, 19, 1));
                    mToolbar.setTitle("Raise Hell");

                    fabResume.clearAnimation();
                    fabContact.clearAnimation();
                    fabSocial.clearAnimation();
                    fabProjects.clearAnimation();
                    fabGallery.clearAnimation();
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                zoomAnimation.cancel();
            }

        });

    }



    private Account createSyncAccount(Context context) {
        Account newAccount = new Account(
                context.getString(R.string.account),
                context.getString(R.string.account_type)
        );

        AccountManager accountManager =
                (AccountManager) context.getSystemService(ACCOUNT_SERVICE);

        if (accountManager.addAccountExplicitly(newAccount, null, null)) {
            Log.d(TAG, "createSyncAccount: successful");
        } else {
            Log.d(TAG, "createSyncAccount: failed");
        }
        return newAccount;
    }


}
