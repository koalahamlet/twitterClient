package com.mikes.Twitter.app.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.mikes.Twitter.app.EndlessScrollListener;
import com.mikes.Twitter.app.MyTwitterApp;
import com.mikes.Twitter.app.R;
import com.mikes.Twitter.app.TweetsAdapter;
import com.mikes.Twitter.app.models.Tweet;

import eu.erikw.PullToRefreshListView;

public class TweetsListFragment extends Fragment {
	
	ArrayList<Tweet> tweets = new ArrayList<Tweet>();
	protected PullToRefreshListView lvTweets;
	TweetsAdapter adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {
		//where you inflate xml
		return inflater.inflate(R.layout.fragment_tweets_list, parent, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		//whenever fragment is displayed AND activity exists
		super.onActivityCreated(savedInstanceState);
		 
//		Log.d("DEBUG", jsonTweets.toString());
		
		 
		adapter = new TweetsAdapter(getActivity(), tweets);
		lvTweets = (PullToRefreshListView) getActivity().findViewById(R.id.lvTweets);
		lvTweets.setAdapter(adapter);
		
		lvTweets.setOnScrollListener(new EndlessScrollListener() {

			
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
//				setProgressBarIndeterminateVisibility(Boolean.TRUE); 
				
				queryTehNetz(page ,totalItemsCount);
				
			}

			
});
	}
	
	public void showFailMessage() {
		// TODO Auto-generated method stub
		Toast.makeText(getActivity(), "Whoops, we can't seem to speak to teh netz at the moment. " +
				"You'll see your tweets again once you've gotten a better connection.",
				Toast.LENGTH_LONG).show();
	}
	
	public TweetsAdapter getAdapter() {
		return adapter;
	}

	public void queryTehNetz(int page, int totalItemsCount) {
		//nothing to see here
		
	}
}
