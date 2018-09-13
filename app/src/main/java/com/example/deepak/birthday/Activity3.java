package com.example.deepak.birthday;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class Activity3 extends AppCompatActivity {

    MediaPlayer player;

    private AudioManager audioManager;

    AudioManager.OnAudioFocusChangeListener afChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {

                player.pause();
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {

                player.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {

                releaseMediaPlayer();
            }
        }
    };

    private MediaPlayer.OnCompletionListener mcomplete = new MediaPlayer.OnCompletionListener() {

        @Override
        public void onCompletion(MediaPlayer mediaPlayer1) {

            releaseMediaPlayer();

        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        int result = audioManager.requestAudioFocus(afChangeListener, AudioManager.STREAM_MUSIC, audioManager.AUDIOFOCUS_GAIN);

        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

            // WE HAVE AUDIO FOCUS NOW

            player = MediaPlayer.create(Activity3.this, R.raw.happy_diljit);

            player.start();

            player.setOnCompletionListener(mcomplete);
        }


        ViewFlipper flipParent = (ViewFlipper) findViewById(R.id.flipper2);

        ViewFlipper flipChild = (ViewFlipper) findViewById(R.id.flip3);

        flipParent.startFlipping();

        flipChild.startFlipping();

        ImageView image3 = (ImageView) findViewById(R.id.ActivityFour);

        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent4 = new Intent(Activity3.this, Activity4.class);

                startActivity(intent4);
            }
        });
    }

    public void releaseMediaPlayer() {

        if (player != null) {

            player.release();

            player = null;

            audioManager.abandonAudioFocus(afChangeListener);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        releaseMediaPlayer();
    }
}
