package com.scsa.andr.project;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.scsa.andr.project.databinding.ActivityWakeupBinding;

public class WakeupActivity extends AppCompatActivity {
	MediaPlayer player;

	private ActivityWakeupBinding binding;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityWakeupBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
				| WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
				| WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

		player = MediaPlayer.create(this, R.raw.sound1);
		player.start();

		binding.btnAlarmOff.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				releasePlayer();
				finish();
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		releasePlayer();
	}

	private void releasePlayer(){
		if(player != null && player.isPlaying()){
			player.stop();
			player.release();
			player = null;
		}
	}
}
