package com.mikes.Twitter.app.fragments;

import javax.crypto.spec.IvParameterSpec;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.mikes.Twitter.app.MyTwitterApp;
import com.mikes.Twitter.app.models.Tweet;

public class UserTimelineFragment extends TweetsListFragment {

	String screenName;
	
//	public UserTimelineFragment(String screenName){
//		this.screenName = screenName;
//	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		
		
		MyTwitterApp.getRestClient().getUserTimeline(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray json) {

				getAdapter().addAll(Tweet.fromJson(json));
			}
		});
	}
	
	@Override
	public void queryTehNetz(int page, int totalItemsCount) {
		

		if(totalItemsCount == 1){
			Log.d("DEBUG", "FFFFFFFFFFFFFFFFFFFFUUUUUUUUUUUUUUUUUUUUU");
		}else{
		Tweet lastTweet = (Tweet) lvTweets.getItemAtPosition(totalItemsCount - 1);
			Integer count = totalItemsCount;
			
			Log.d("DEBUG", count.toString());
			Log.d("DEBUG", lastTweet.toString());
			
			MyTwitterApp.getRestClient().getAdditionalUserTimeline(lastTweet.getTweetId() - 1, new JsonHttpResponseHandler() {

					public void onSuccess(JSONArray json) {
						//Log.d("DEBUG", json.toString());
						tweets = Tweet.fromJson(json);
						getAdapter().addAll(tweets);
						//adapter.notifyDataSetChanged();

//						ActiveAndroid.beginTransaction();
//						try {
//							for (Tweet tweetInstance : Tweet.fromJson(json)) {
//								tweetInstance.getUser().save();
//								tweetInstance.save();
//							}
//							ActiveAndroid.setTransactionSuccessful();
//						} finally {
//							ActiveAndroid.endTransaction();
//						}
					}

					public void onFailure(Throwable e) {
						Log.d("DEBUG", "Fetch timeline error: " + e.toString());
					}
				});
		}
	}
}
