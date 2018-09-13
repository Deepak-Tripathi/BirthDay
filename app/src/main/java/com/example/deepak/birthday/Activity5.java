package com.example.deepak.birthday;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Activity5 extends AppCompatActivity {

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
        setContentView(R.layout.activity_5);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        int result = audioManager.requestAudioFocus(afChangeListener, AudioManager.STREAM_MUSIC, audioManager.AUDIOFOCUS_GAIN);

        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

            // WE HAVE AUDIO FOCUS NOW

            player = MediaPlayer.create(Activity5.this, R.raw.teresangyaara);

            player.start();

            player.setOnCompletionListener(mcomplete);
        }
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
