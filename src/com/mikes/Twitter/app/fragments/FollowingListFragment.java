package com.mikes.Twitter.app.fragments;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.mikes.Twitter.app.MyTwitterApp;
import com.mikes.Twitter.app.ProfileActivity;
import com.mikes.Twitter.app.R;
import com.mikes.Twitter.app.models.Tweet;
import com.mikes.Twitter.app.models.User;

public class FollowingListFragment extends UsersListFragment {

	

	public static FollowingListFragment newInstance( String screenName) {
		FollowingListFragment fragmentDemo = new FollowingListFragment();
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
		
		getMainData();

	}
	
	@Override
	public void getMainData() {
		String screenName = getArguments().getString("screenName", "");
		MyTwitterApp.getRestClient().getFollowing(screenName, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, JSONArray json) {
				lvUsers.onRefreshComplete();
				getAdapter().addAll(User.fromJson(json));
				super.onSuccess(arg0, json);
			}
			@Override
			public void onFailure(Throwable arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onFailure(arg0, arg1);
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
