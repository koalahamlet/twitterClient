package com.mikes.Twitter.app.fragments;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.mikes.Twitter.app.MyTwitterApp;
import com.mikes.Twitter.app.ProfileActivity;
import com.mikes.Twitter.app.R;
import com.mikes.Twitter.app.models.Tweet;
import com.mikes.Twitter.app.models.User;

public class FollowersListFragment extends UsersListFragment {

	

	public static FollowersListFragment newInstance( String screenName) {
		FollowersListFragment fragmentDemo = new FollowersListFragment();
        Bundle args = new Bundle();
        args.putString("screenName", screenName);
        fragmentDemo.setArguments(args);
        return fragmentDemo;
    }
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		 
		
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
//	    getAdapter().setMikesValue(true);
		
		
		
		
	    

			

	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		getMainData();
	}
	
	@Override
	public void getMainData() {
		String screenName = getArguments().getString("screenName", "");
		Log.d("DEBUG", "Screen name is "+screenName);
		MyTwitterApp.getRestClient().getFollowers(screenName, new JsonHttpResponseHandler() {
			@Override
			public void onStart() {
				super.onStart();
				Log.d("DEBUG", "Something started");
			}
			
			@Override
			public void onSuccess(int arg0, JSONObject arg1) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0, arg1);
				Log.d("DEBUG", arg1.toString());
				Log.d("DEBUG", "Something succeeded1");
			}
			
			@Override
			public void onSuccess(JSONArray arg0) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0);
				Log.d("DEBUG", "Something succeeded2");
			}
			@Override
			public void onSuccess(JSONObject arg0) {
				Log.d("DEBUG", arg0.toString());
				super.onSuccess(arg0);
				Log.d("DEBUG", "Something succeeded3");
			}
			
			@Override
			public void onSuccess(int arg0, JSONArray json) {
				lvUsers.onRefreshComplete();
				Log.d("DEBUG", "JSON is "+json.toString());
				getAdapter().addAll(User.fromJson(json));
				super.onSuccess(arg0, json);
			}
			@Override
			public void onFailure(Throwable arg0, String arg1) {
				Log.d("DEBUG", "Something failed "+arg1+"string");
				arg0.printStackTrace();
				super.onFailure(arg0, arg1);
			}
			@Override
			public void onFailure(Throwable arg0, JSONArray arg1) {
				Log.d("DEBUG", "Something failed "+arg1 + "jsonarray");
				arg0.printStackTrace();
				super.onFailure(arg0, arg1);
			}
			@Override
			public void onFailure(Throwable arg0, JSONObject arg1) {
				 Log.d("DEBUG", "Something failed "+arg1 + "jsonarray");
				arg0.printStackTrace();
				super.onFailure(arg0, arg1);
			}
			@Override
			public void onFinish() {
				super.onFinish();
				 Log.d("DEBUG", "Something finished");
			}
		});
		super.getMainData();
	}

	@Override
	public void queryTehNetz(int page, int totalItemsCount) {

//		if (totalItemsCount == 1) {
//			Log.d("DEBUG", "FFFFFFFFFFFFFFFFFFFFUUUUUUUUUUUUUUUUUUUUU");
//		} else
//		{
//			Tweet lastTweet = (Tweet) lvTweets
//					.getItemAtPosition(totalItemsCount - 1);
//			Integer count = totalItemsCount;
//
//			Log.d("DEBUG", count.toString());
//			Log.d("DEBUG", lastTweet.toString());
//
//			  String screenName = getArguments().getString("screenName", "");
//				
//
//			getActivity().setProgressBarIndeterminate(true);
//			MyTwitterApp.getRestClient().getAdditionalOtherUsersTimeline(
//					screenName, lastTweet.getTweetId() - 1, new JsonHttpResponseHandler() {
//
//						public void onSuccess(JSONArray json) {
//							getActivity().setProgressBarIndeterminate(false);
//							// Log.d("DEBUG", json.toString());
//							tweets = Tweet.fromJson(json);
//							getAdapter().addAll(tweets);
//							// adapter.notifyDataSetChanged();
//
//							// ActiveAndroid.beginTransaction();
//							// try {
//							// for (Tweet tweetInstance : Tweet.fromJson(json))
//							// {
//							// tweetInstance.getUser().save();
//							// tweetInstance.save();
//							// }
//							// ActiveAndroid.setTransactionSuccessful();
//							// } finally {
//							// ActiveAndroid.endTransaction();
//							// }
//						}
//
//						public void onFailure(Throwable e) {
//							getActivity().setProgressBarIndeterminate(false);
//							Log.d("DEBUG",
//									"Fetch timeline error: " + e.toString());
//						}
//					});
//		}
	}
}
