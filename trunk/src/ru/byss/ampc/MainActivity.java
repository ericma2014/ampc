package ru.byss.ampc;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	private static final String TAG = "AMPCMainActivity";
	
	private OnClickListener onConnectClickListener = new OnClickListener () {
		public void onClick (View v) {
			String host, messageText = "OK";
			EditText hostEditText = (EditText) findViewById (R.id.host);
			host = hostEditText.getText ().toString ();
			if (!control.connect (host)) {
				messageText = "Failed: " + control.getErrorMessage ();
			}
			TextView messageTextView = (TextView) findViewById (R.id.message);
			messageTextView.setText (messageText);
		}
	};

	private OnClickListener onPlayClickListener = new OnClickListener () {
		public void onClick (View v) {
			String messageText = "OK";
			if (!control.sendPlay ()) {
				messageText = "Failed: " + control.getErrorMessage ();
			}
			TextView messageTextView = (TextView) findViewById (R.id.message);
			messageTextView.setText (messageText);
		}
	};

	private OnClickListener onPauseClickListener = new OnClickListener () {
		public void onClick (View v) {
			String messageText = "OK";
			if (!control.sendPause ()) {
				messageText = "Failed: " + control.getErrorMessage ();
			}
			TextView messageTextView = (TextView) findViewById (R.id.message);
			messageTextView.setText (messageText);
		}
	};

	private OnClickListener onStopClickListener = new OnClickListener () {
		public void onClick (View v) {
			String messageText = "OK";
			if (!control.sendStop ()) {
				messageText = "Failed: " + control.getErrorMessage ();
			}
			TextView messageTextView = (TextView) findViewById (R.id.message);
			messageTextView.setText (messageText);
		}
	};
	
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
		setContentView (R.layout.main);
		
		control = new MPDControl ();
		if (!control.initOK ()) {
			Log.e (TAG, "Cannot initialize MPDControl!");
		}
		
		Button connectButton = (Button) findViewById (R.id.connect);
		connectButton.setOnClickListener (onConnectClickListener);
		Button playButton = (Button) findViewById (R.id.play);
		playButton.setOnClickListener (onPlayClickListener);
		Button pauseButton = (Button) findViewById (R.id.pause);
		pauseButton.setOnClickListener (onPauseClickListener);
		Button stopButton = (Button) findViewById (R.id.stop);
		stopButton.setOnClickListener (onStopClickListener);
	}
	
	private MPDControl control;
}
