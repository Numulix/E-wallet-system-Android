package rs.raf.projekat1.jovan_babic_rn3018.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rafwalletprojekat.R;

import java.io.File;

public class IncomeExpenseInfoActivity extends AppCompatActivity {

    private TextView infoNameTv;
    private TextView infoAmountTv;
    private TextView infoDescTv;

    private ImageView playButton;
    private ImageView pauseButton;

    // Audio
    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;
    private AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener;
    private AudioFocusRequest audioFocusRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_income_expense_info);
        initViews();
        updateData();
    }

    private void initViews() {
        infoNameTv = findViewById(R.id.info_income_expense_name);
        infoAmountTv = findViewById(R.id.info_income_expense_amount);
        infoDescTv = findViewById(R.id.info_income_expense_description);
        playButton = findViewById(R.id.play_button);
        pauseButton = findViewById(R.id.pause_button);
    }

    private void updateData() {
        infoNameTv.setText(getIntent().getStringExtra("naziv"));
        infoAmountTv.setText(getIntent().getIntExtra("kolicina", 0)+"");
        if (getIntent().getStringExtra("opis") != null)
            infoDescTv.setText(getIntent().getStringExtra("opis"));
        else {
            infoDescTv.setVisibility(View.GONE);
            playButton.setVisibility(View.VISIBLE);
//            getIntent().getStringExtra("audioFile");
            initPlayer();
            initListeners();
        }
    }

    private void initPlayer() {
        mediaPlayer = MediaPlayer.create(this, Uri.fromFile(new File(getIntent().getStringExtra("audioFile"))));
        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
    }

    private void initListeners() {
        playButton.setOnClickListener(v -> play());
        pauseButton.setOnClickListener(v -> pause());

        onAudioFocusChangeListener = focusChange -> {
          switch (focusChange) {
              case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
              case AudioManager.AUDIOFOCUS_LOSS: {
                  pause();
              } break;
              case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK: {
                  playerDuck(true);
              } break;
              case AudioManager.AUDIOFOCUS_GAIN: {
                  playerDuck(false);
                  play();
              } break;
          }
        };

        mediaPlayer.setOnCompletionListener(mp -> {
            pauseButton.setVisibility(View.GONE);
            playButton.setVisibility(View.VISIBLE);
        });

        audioFocusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                .setAudioAttributes(new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .build())
                .setAcceptsDelayedFocusGain(true)
                .setWillPauseWhenDucked(true)
                .setOnAudioFocusChangeListener(onAudioFocusChangeListener)
                .build();
    }

    private void play() {
        int result = audioManager.requestAudioFocus(audioFocusRequest);
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            playButton.setVisibility(View.GONE);
            pauseButton.setVisibility(View.VISIBLE);
            mediaPlayer.start();
        }
    }

    private void pause() {
        if (mediaPlayer != null) {
            pauseButton.setVisibility(View.GONE);
            playButton.setVisibility(View.VISIBLE);
            mediaPlayer.pause();
        }
    }

    public synchronized void playerDuck(boolean duck) {
        if (mediaPlayer != null)
            mediaPlayer.setVolume(duck ? 0.2f : 1.0f, duck ? 0.2f : 1.0f);
    }

    @Override
    protected void onPause() {
        super.onPause();
        pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        if (mediaPlayer != null)
            mediaPlayer.release();
        mediaPlayer = null;
        if (audioManager != null)
            audioManager.abandonAudioFocusRequest(audioFocusRequest);
    }
}