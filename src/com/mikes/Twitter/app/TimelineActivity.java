package com.mikes.Twitter.app;


import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.content.ClipData.Item;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.activeandroid.ActiveAndroid;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.mikes.Twitter.app.models.Tweet;
import com.mikes.Twitter.app.models.User;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class TimelineActivity extends Activity {

	PullToRefreshListView lvTweets;
	TweetsAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		
		lvTweets = (PullToRefreshListView) findViewById(R.id.lvTweets);

		lvTweets.setOnScrollListener(new EndlessScrollListener() {
			
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				
				Tweet lastTweet = (Tweet) lvTweets.getItemAtPosition(totalItemsCount-1);
				// minus one in the next call to get rid of duplicate tweet
				MyTwitterApp.getRestClient().getMoreHomeTimeline( lastTweet.getTweetId()-1,
						new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONArray jsonTweets) {
						Log.d("DEBUG", jsonTweets.toString());
						ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets);
						adapter.addAll(tweets);
						adapter.notifyDataSetChanged();
						for(Tweet tweet : tweets){
							 
					    	 
						}
					}
					
				});
				
			}
		});
		
		lvTweets.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				fetchTimelineAsync(0);				
			}
		});
				
		fetchTimelineAsync(0);

	}

	protected void fetchTimelineAsync(int i) {
		MyTwitterApp.getRestClient().getHomeTimeline(
				new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray jsonTweets) {
				Log.d("DEBUG", jsonTweets.toString());
				ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets);
//				for (Tweet tweet : tweets) {
//					Log.d("halper", tweet.getCreatedAt());
					
//				}
				 adapter = new TweetsAdapter(getBaseContext(), tweets);
				lvTweets.setAdapter(adapter);				
				lvTweets.onRefreshComplete();
//				super.onSuccess(jsonTweets); 
			}
			
		});		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		fetchTimelineAsync(0);
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.action_compose:
			Intent i = new Intent(this, ComposeTweetActivity.class);
			startActivityForResult(i, 7);
			break;
		default:
			break;
		}
		return true;

	}
	
}
