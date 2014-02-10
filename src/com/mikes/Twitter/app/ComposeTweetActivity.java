package com.mikes.Twitter.app;

import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ComposeTweetActivity extends Activity {

	public EditText etCompose;
	public ImageView ivProfileImage;
	public TextView tvName;
	public TextView tvScreenName;
	public Integer characterCount= 140;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose_tweet);

		etCompose = (EditText) findViewById(R.id.etCompose);
		ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
		tvName = (TextView) findViewById(R.id.tvName);
		tvScreenName = (TextView) findViewById(R.id.tvScreenName);
		Bundle b = getIntent().getExtras();
		String name = b.getString("name");
		String screenName = b.getString("screen_name");
		String profileUrl = b.getString("profile_image");
		
		ImageLoader.getInstance().displayImage( profileUrl, ivProfileImage);
		tvName.setText(name);
		tvScreenName.setText("@"+screenName);

		etCompose.requestFocus();
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		etCompose.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				//dismiss error 
				etCompose.setError(null);

				Integer length = etCompose.getText().length();
				
				characterCount = 140 - length;
				//used to update 
				invalidateOptionsMenu();


			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
			

			}
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compose_tweet, menu);
//		getSupportMenuInflater().inflate(R.menu.compose_tweet, menu);
//		MenuItem menuItem=menu.findItem(R.id.menuSearch);
//		MenuItem menuItem = menu.findItem(R.id.text_counter);
		
		
		return true;
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
	    MenuItem score = menu.findItem(R.id.text_counter);
	    score.setTitle(characterCount.toString());
	    return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.action_settings:
			
			String tweet = etCompose.getText().toString();
			
			if(tweet.isEmpty()){
				etCompose.setError("You have to give the Twitter Bird something to tweet!"); // fancy error message for edit texts
				
			}else{

				MyTwitterApp.getRestClient().postTweet(tweet,
					new JsonHttpResponseHandler() {
						@Override
						public void onSuccess(JSONObject json) {
							Log.d("DEBUG", "Success");
							Toast.makeText(ComposeTweetActivity.this,
									"Twitter Bird is all atwitter.",
									Toast.LENGTH_LONG).show();
							ComposeTweetActivity.this.finish();
							super.onSuccess(json);
						}

						@Override
						public void onFailure(Throwable arg0,
								JSONObject arg1) {
							Toast.makeText(ComposeTweetActivity.this,
									"We're Sorry. The Twitter Bird has flown the coop. Maybe it'll come back once you've regained network connection",
									Toast.LENGTH_LONG).show();
							Log.d("DEBUG", "Fail sauce");
							super.onFailure(arg0, arg1);
						}
					});
			}
			break;
		default:
			break;
		}
		return true;

	}
}
