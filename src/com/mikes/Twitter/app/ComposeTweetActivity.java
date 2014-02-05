package com.mikes.Twitter.app;

import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

public class ComposeTweetActivity extends Activity {

	public EditText etCompose;
	public ImageButton bTweet;
	public TextView tvCount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose_tweet);

		etCompose = (EditText) findViewById(R.id.etCompose);
		bTweet = (ImageButton) findViewById(R.id.bTweet);
		tvCount = (TextView) findViewById(R.id.tvCount);

		bTweet.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				
				
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
			}
		});

		etCompose.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				

				Integer length = etCompose.getText().length();
				
				Integer stuffstuff = 140 - length;
				String stuff = stuffstuff.toString();
				tvCount.setText(stuff);

				if (length > 115) {
					tvCount.setTextColor(Color.RED);
				} else {
					tvCount.setTextColor(Color.BLACK);
				}

				Log.d("KeyNumber", "chars: " + length);

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

}
