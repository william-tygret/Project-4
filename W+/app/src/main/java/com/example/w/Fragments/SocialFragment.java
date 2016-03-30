package com.example.w.Fragments;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.w.ContentProvider;
import com.example.w.DatabaseHelper;
import com.example.w.InstagramAPIstuff.Datum;
import com.example.w.InstagramAPIstuff.InstagramAPI;
import com.example.w.InstagramAPIstuff.InstagramAPIResults;
import com.example.w.InstagramData;
import com.example.w.InstagramRecyclerAdapter;
import com.example.w.R;
import com.example.w.SyncAdapter;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by williamtygret on 3/21/16.
 */
public class SocialFragment extends Fragment {


    private ArrayList<InstagramData> mInstagramPosts;
    RecyclerView mInstagramRecyclerView;
    InstagramRecyclerAdapter mAdapter;
    Cursor mCursor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_social, container, false);


        mCursor = DatabaseHelper.getInstance(getContext()).getInstagram(10);
        InstagramData instagramData = new InstagramData(mCursor);


        getContext().getContentResolver().registerContentObserver(Uri.parse(ContentProvider.BASE_URI_STRING),true,new SocialFragment.InstagramContentObserver(new Handler()));

        mInstagramRecyclerView = (RecyclerView)view.findViewById(R.id.instagramRecyclerView);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        mAdapter = new InstagramRecyclerAdapter(instagramData.getInstagramItems());
        mInstagramRecyclerView.setLayoutManager(manager);
        mInstagramRecyclerView.setAdapter(mAdapter);


      return view;
    }




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }




    public class InstagramContentObserver extends ContentObserver {

        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public InstagramContentObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            //do stuff on UI thread
            getContext().getContentResolver().registerContentObserver(ContentProvider.INSTAGRAM_URI, true, new InstagramContentObserver(new Handler()));

            ContentProvider:
            {
                Log.d("ONCHANGE", "onChange: news");
                new PullFromDbAsyncTask().execute();
            }
        }

    }

        private class PullFromDbAsyncTask extends AsyncTask<Void,Void,InstagramData> {

            @Override
            protected InstagramData doInBackground(Void... params) {

                Cursor cursor;
                cursor = getContext().getContentResolver().query(ContentProvider.INSTAGRAM_URI, null, null, null, null);

                InstagramData igData = new InstagramData(cursor);

                return igData;
            }

            @Override
            protected void onPostExecute(InstagramData igData) {
                super.onPostExecute(igData);
                mAdapter = new InstagramRecyclerAdapter(igData.getInstagramItems());
                mInstagramRecyclerView.setAdapter(mAdapter);
            }
        }




}
