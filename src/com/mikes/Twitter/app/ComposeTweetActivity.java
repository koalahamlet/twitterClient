package com.mikes.Twitter.app;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

public class ComposeTweetActivity extends Activity {

	public EditText etCompose;
	public Button bTweet;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose_tweet);
		
		etCompose = (EditText) findViewById(R.id.etCompose);
		bTweet = (Button) findViewById(R.id.bTweet);
		
		bTweet.setOnClickListener(new OnClickListener() {
		
			@Override
			public void onClick(View v) {
		
				String tweet = etCompose.getText().toString();
				
				MyTwitterApp.getRestClient().postTweet(tweet, new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject json) {
						Log.d("DEBUG", "Success");
						Toast.makeText(ComposeTweetActivity.this, "Hold on to your butts",Toast.LENGTH_LONG).show();
						ComposeTweetActivity.this.finish();
						super.onSuccess(json);
					}
					@Override
					public void onFailure(Throwable arg0, JSONObject arg1) {
						Log.d("DEBUG", "Fail sauce");
						super.onFailure(arg0, arg1);
					}
				});
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compose_tweet, menu);
		return true;
	}

}
