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
				
				if(totalItemsCount == 1){
					Log.d("DEBUG", "FFFFFFFFFFFFFFFFFFFFUUUUUUUUUUUUUUUUUUUUU");
				}else{
				Tweet lastTweet = (Tweet) lvTweets.getItemAtPosition(totalItemsCount - 1);
				Integer count = totalItemsCount;
				//								Integer count = getAdapter().getCount();
				Log.d("DEBUG", count.toString());
				Log.d("DEBUG", lastTweet.toString());
				// minus one in the next call to get rid of duplicate tweet
				MyTwitterApp.getRestClient().getMoreHomeTimeline(lastTweet.getTweetId() - 1,
						new JsonHttpResponseHandler() {
							@Override
							public void onSuccess(JSONArray jsonTweets) {
//								setProgressBarIndeterminateVisibility(Boolean.FALSE); 
								Log.d("DEBUG", jsonTweets.toString());
								tweets = Tweet.fromJson(jsonTweets);
								adapter.addAll(tweets);
								adapter.notifyDataSetChanged();
//								ActiveAndroid.beginTransaction();
//								try {
//									for (Tweet tweetInstance : tweets) {
//										tweetInstance.getUser().save();
//										tweetInstance.save();
//									}
//									ActiveAndroid.setTransactionSuccessful();
//								} finally {
//									ActiveAndroid.endTransaction();
//								}

							}

							@Override
							public void onFailure(Throwable arg0, String arg1) {
								showFailMessage();
//								setProgressBarIndeterminateVisibility(Boolean.FALSE); 
								super.onFailure(arg0, arg1);
							}

							
						});
	
			}
			}
});
	}
	
	public void showFailMessage() {
		// TODO Auto-generated method stub
		Toast.makeText(getActivity(), "Whoops, we can't seem to speak to teh netz at the moment. You'll see your tweets again once you've gotten a better connection.", Toast.LENGTH_LONG).show();
	}
	
	public TweetsAdapter getAdapter() {
		return adapter;
	}
}
