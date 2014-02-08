package com.mikes.Twitter.app.fragments;

import java.util.List;

import org.json.JSONArray;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.mikes.Twitter.app.MyTwitterApp;
import com.mikes.Twitter.app.TweetsAdapter;
import com.mikes.Twitter.app.models.Tweet;

import android.os.Bundle;

public class MentionsFragment extends TweetsListFragment {
	
	TweetsAdapter adapter;
	List<Tweet> tweets;
	TweetsListFragment fragmentTweets;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		MyTwitterApp.getRestClient().getMentions(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, JSONArray jsonTweets) {
				
				tweets = Tweet.fromJson(jsonTweets);
				
				getAdapter().addAll(tweets);
				
				
			}
			
		});
		
		
	}
}
