package com.br.activity;


import com.br.activity.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class ApresentacaoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_apresentacao);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.apresentacao, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_settings:
			Intent it = new Intent(this, MainActivity.class);
			startActivity(it);
			
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
