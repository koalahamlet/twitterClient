package com.mikes.Twitter.app;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikes.Twitter.app.models.Tweet;
import com.mikes.Twitter.app.models.User;
import com.nostra13.universalimageloader.core.ImageLoader;

public class UsersAdapter extends ArrayAdapter<User> {
//////////////////////this class hasn't been customized to do what it is supposed to do yet. 
	String screenName;
	ImageView imageView;
	
	public UsersAdapter(Context context, List<User> users) {
		super(context, 0, users);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null){
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.user_item, null); 
		}
		
		final User user = getItem(position);
		Log.d("DEBUG", user.toString());
		String screenName = user.getScreenName();
		
		imageView = (ImageView) view.findViewById(R.id.ivProfile);
		ImageLoader.getInstance().displayImage(user.getProfileImageUrl(), imageView);
		
		TextView nameView = (TextView) view.findViewById(R.id.tvName);
		TextView screennameView = (TextView) view.findViewById(R.id.tvScreenName );
		String formattedName = "<b>" + user.getName() + "</b>";
		String twitterName = " <small><font color = '#777777'>@" + user.getScreenName() + "</font></small>";
		
		nameView.setText(Html.fromHtml(formattedName));
		nameView.setText(Html.fromHtml(twitterName));
		
//		bodyView.setText(Html.fromHtml(tweet.getBody()));

//		TextView createdView = (TextView) view.findViewById(R.id.tvDate);

		
		
		
		
		imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent i = new Intent(getContext(), ProfileActivity.class);
				i.putExtra("userinfo", user.getScreenName());
				getContext().startActivity(i);
				
			}
		});
		
//		try {
//			date = tweet.getCreatedAt();
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//
//		String str = (String) DateUtils.getRelativeDateTimeString(getContext(),
//				date.getTime(), DateUtils.MINUTE_IN_MILLIS,
//				DateUtils.WEEK_IN_MILLIS, 0);
//		
//		createdView.setText(str);

		return view; 
	}

}
