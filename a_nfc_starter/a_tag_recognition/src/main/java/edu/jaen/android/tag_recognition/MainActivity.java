package edu.jaen.android.tag_recognition;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.jaen.android.tag_recognition.databinding.ActivityMainBinding;

/**
 * activity의 default launchMode : standard이므로 onCreate가 계속 호출된다.
 * 호출시마다 Activity를 재생성
 */
public class MainActivity extends AppCompatActivity {
	private static final String TAG = "MainActivity_SCSA";

	private ActivityMainBinding binding;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityMainBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		String action = getIntent().getAction();
		binding.textview.setText(action);

		processIntent(getIntent());

		Toast.makeText(this, "onCreate"+ action, Toast.LENGTH_SHORT).show();
		Log.d(TAG, "onCreate: "+action);
	}

	private void processIntent(Intent intent) {
		String action = intent.getAction();
		// Main, Ndef_discovered
		if (action.equals(NfcAdapter.ACTION_NDEF_DISCOVERED)) {
			Parcelable [] parcelable = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
			for(Parcelable parcelable1 : parcelable) {
				NdefMessage message = (NdefMessage) parcelable1;
				NdefRecord [] records = message.getRecords();
				for (NdefRecord record : records) {
					Log.d(TAG, "processIntent: id: "+new String(record.getId()));
					Log.d(TAG, "processIntent: tnf: "+record.getTnf());
					Log.d(TAG, "processIntent: type: "+new String(record.getType()));
					Log.d(TAG, "processIntent: payload: "+new String(record.getPayload()));
					byte [] b = record.getPayload();
					String s = new String(b, 3, b.length-3);
					Log.d(TAG, "processIntent: payload : "+ s);
				}
			}

		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
	}
}
