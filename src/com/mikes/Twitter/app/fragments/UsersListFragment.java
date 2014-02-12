package com.mikes.Twitter.app.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mikes.Twitter.app.EndlessScrollListener;
import com.mikes.Twitter.app.R;
import com.mikes.Twitter.app.TweetsAdapter;
import com.mikes.Twitter.app.UsersAdapter;
import com.mikes.Twitter.app.models.User;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class UsersListFragment extends Fragment {
	
	
	
	ArrayList<User> users = new ArrayList<User>();
	protected PullToRefreshListView lvUsers;
	UsersAdapter adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {
		//where you inflate xml and this is ok since this xml is only a list.
		
		return inflater.inflate(R.layout.fragment_tweets_list, parent, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		//whenever fragment is displayed AND activity exists
		super.onActivityCreated(savedInstanceState);
		 
		adapter = new UsersAdapter(getActivity(), users);
		//
		lvUsers = (PullToRefreshListView) getActivity().findViewById(R.id.lvTweets);
		//
		lvUsers.setAdapter(adapter);
		
		lvUsers.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				
				getMainData();
				
			}
		});
		
		lvUsers.setOnScrollListener(new EndlessScrollListener() {

			
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
//				setProgressBarIndeterminateVisibility(Boolean.TRUE); 
				
				queryTehNetz(page ,totalItemsCount);
				
			}

			
			
});
	}
	
	public void showFailMessage() {

		Toast.makeText(getActivity(), "Whoops, we can't seem to speak to teh netz at the moment. " +
				"You'll see your friends again once you've gotten a better connection.",
				Toast.LENGTH_LONG).show();
	}
	
	public void getMainData() {
		// nothing to see here
	}
	
	public UsersAdapter getAdapter() {
		return adapter;
	}

	public void queryTehNetz(int page, int totalItemsCount) {
		//nothing to see here
	}
}
