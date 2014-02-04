package com.mikes.Twitter.app;


import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.mikes.Twitter.app.models.Tweet;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class TimelineActivity extends Activity {

	PullToRefreshListView lvTweets;
	TweetsAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_timeline);
		 		    
		lvTweets = (PullToRefreshListView) findViewById(R.id.lvTweets);

		lvTweets.setOnScrollListener(new EndlessScrollListener() {
			
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				
				Tweet lastTweet = (Tweet) lvTweets.getItemAtPosition(totalItemsCount-1);
				
				MyTwitterApp.getRestClient().getMoreHomeTimeline( lastTweet.getId(),
						new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONArray jsonTweets) {
						Log.d("DEBUG", jsonTweets.toString());
						ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets);
						adapter.addAll(tweets);
						adapter.notifyDataSetChanged();

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
		setProgressBarIndeterminateVisibility(true);
		MyTwitterApp.getRestClient().getHomeTimeline(
				new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray jsonTweets) {
				Log.d("DEBUG", jsonTweets.toString());
				setProgressBarIndeterminateVisibility(false);
				ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets);

				 adapter = new TweetsAdapter(getBaseContext(), tweets);
				lvTweets.setAdapter(adapter);				
				lvTweets.onRefreshComplete();
//				super.onSuccess(jsonTweets); 
			}
			@Override
					public void onFailure(Throwable arg0, String arg1) {
						setProgressBarIndeterminateVisibility(false);
						Toast.makeText(TimelineActivity.this, "Whoops, we can't seem to speak to teh netz at the moment. You'll see your tweets again once you've gotten a better connection.", Toast.LENGTH_LONG).show();
						super.onFailure(arg0, arg1);
					}
			
		});		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}

//	@Override
//	protected void onResume() {
//		fetchTimelineAsync(0);
//		super.onResume();
//	};
	
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
