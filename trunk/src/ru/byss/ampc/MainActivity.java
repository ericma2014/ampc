package ru.byss.ampc;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spanned;
import android.text.method.KeyListener;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

// TODO: create seperate class?
class NumericRangeKeyListener extends DigitsKeyListener {
	private static final String TAG = "NumericRangeKeyListener";

	public NumericRangeKeyListener () {
		this (Integer.MIN_VALUE, Integer.MAX_VALUE);
	}

	public NumericRangeKeyListener (int max) {
		this (0, max);
	}

	public NumericRangeKeyListener (int min, int max) {
		super ((min < 0), false);
		this.min = min;
		this.max = max;
	}
	
	@Override
	public CharSequence filter (CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
		boolean ok = true;
		String new_string = (dest.subSequence (0, dstart).toString () +
		                    source.subSequence (start, end).toString () +
		                    dest.subSequence (dend, dest.length ()).toString ()
		                   );
		int new_value;
		if (new_string.length () != 0) {
			try {
				new_value = Integer.parseInt (new_string);
				if ((new_value < min) || (new_value > max)) {
					ok = false;
				}
			}
			catch (NumberFormatException e) {
				ok = false;
			}
			if (ok) {
				return null;
			} else {
				return dest.subSequence (dstart, dend).toString ();
			}
		} else {
			return null;
		}
	}
	
	private int min;
	private int max;
}

public class MainActivity extends Activity {
	private static final String TAG = "AMPCMainActivity";
	
	private OnClickListener onConnectClickListener = new OnClickListener () {
		public void onClick (View v) {
			String messageText = "OK";
			
			EditText hostEditText = (EditText) findViewById (R.id.host);
			String host = hostEditText.getText ().toString ();
			
			EditText portEditText = (EditText) findViewById (R.id.port);
			String port_str = portEditText.getText ().toString ();
			int port = 0;
			if (port_str.length () != 0) {
				port = Integer.parseInt (port_str);
			}
			
			if (!control.connect (host, port)) {
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
	
	private OnClickListener onStatusClickListener = new OnClickListener () {
		public void onClick (View v) {
			String messageText = "OK";
			MPDStatus status = control.getStatus ();
			if (status == null) {
				messageText = "Failed"; // TBD
			} else {
				String toastText = "";
				toastText += "Volume:        " + Integer.toString (status.volume ())       + "\n";
				toastText += "Repeat:        " + Boolean.toString (status.repeat ())       + "\n";
				toastText += "Random:        " + Boolean.toString (status.random ())       + "\n";
				toastText += "Single:        " + Boolean.toString (status.single ())       + "\n";
				toastText += "Consume:       " + Boolean.toString (status.consume ())      + "\n";
				toastText += "Queue length:  " + Integer.toString (status.queueLength ())  + "\n";
				toastText += "Queue version: " + Integer.toString (status.queueVersion ()) + "\n";
				String stateString = "(null)";
				switch (status.state ()) {
					case MPDStatus.MPD_STATE_UNKNOWN:
						stateString = "Unknown";
						break;

					case MPDStatus.MPD_STATE_PLAY:
						stateString = "Play";
						break;

					case MPDStatus.MPD_STATE_PAUSE:
						stateString = "Pause";
						break;

					case MPDStatus.MPD_STATE_STOP:
						stateString = "Stop";
						break;
				}
				toastText += "MPD state:     " + stateString                               + "\n";
				toastText += "Crossfade:     " + Integer.toString (status.crossfade ())    + "\n";
				toastText += "Song position: " + Integer.toString (status.songPos ())      + "\n";
				toastText += "Song ID:       " + Integer.toString (status.songId ())       + "\n";
				toastText += "Elapsed time:  " + Integer.toString (status.elapsedTime ())  + "\n";
				toastText += "Elapsed ms:    " + Integer.toString (status.elapsedMs ())    + "\n";
				toastText += "Total time:    " + Integer.toString (status.totalTime ())    + "\n";
				
				Log.d (TAG, "\n\n\n" + toastText + "\n\n\n");
			}
		}
	};
	
	private KeyListener onPortChangeListener = new NumericRangeKeyListener (65535);
	
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

		EditText portEditText = (EditText) findViewById (R.id.port);
		portEditText.setKeyListener (onPortChangeListener);
		
		Button statusButton = (Button) findViewById (R.id.status);
		statusButton.setOnClickListener (onStatusClickListener);
		
		loadSettings ();
	}
	
	@Override
	public void onPause () {
		saveSettings ();
		super.onPause ();
	}
	
	private void loadSettings () {
		SharedPreferences pref = getPreferences (MODE_PRIVATE);
		
		String host = pref.getString ("host", "");
		EditText hostEditText = (EditText) findViewById (R.id.host);
		hostEditText.setText (host);
		
		int port = pref.getInt ("port", 6600);
		EditText portEditText = (EditText) findViewById (R.id.port);
		if (port != 0) {
			portEditText.setText (Integer.toString (port));
		} else {
			portEditText.setText ("");
		}
	}
	
	private void saveSettings () {
		Editor pref = getPreferences (MODE_PRIVATE).edit ();
		
		EditText hostEditText = (EditText) findViewById (R.id.host);
		String host = hostEditText.getText ().toString ();
		pref.putString ("host", host);
		
		EditText portEditText = (EditText) findViewById (R.id.port);
		String port_string = portEditText.getText ().toString ();
		int port = 0;
		if (port_string.length () != 0) {
			port = Integer.parseInt (port_string);
		}
		pref.putInt ("port", port);
		
		pref.commit ();
	}
	
	private MPDControl control;
}
