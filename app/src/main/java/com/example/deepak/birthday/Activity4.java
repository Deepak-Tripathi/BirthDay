package com.example.deepak.birthday;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewFlipper;

import org.w3c.dom.Text;

public class Activity4 extends AppCompatActivity {

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
        setContentView(R.layout.activity_4);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        int result = audioManager.requestAudioFocus(afChangeListener, AudioManager.STREAM_MUSIC, audioManager.AUDIOFOCUS_GAIN);

        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

            // WE HAVE AUDIO FOCUS NOW

            player = MediaPlayer.create(Activity4.this, R.raw.tera_happy_b);

            player.start();

            player.setOnCompletionListener(mcomplete);
        }

          ViewFlipper flip1 = (ViewFlipper) findViewById(R.id.last_flip1);

          flip1.startFlipping();

        ViewFlipper flip2 = (ViewFlipper) findViewById(R.id.last_flip2);

        flip2.startFlipping();

        TextView txt = (TextView) findViewById(R.id.ActivityFive);

        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent4 = new Intent(Activity4.this ,Activity5.class);

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
