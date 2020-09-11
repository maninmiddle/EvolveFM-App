package com.example.evolvefm.fragment;

import android.content.Context;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.evolvefm.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

import java.util.Objects;

public class FragmentPlay extends Fragment {

    SimpleExoPlayer player;

    String stream = "http://s1.evolve-rp.ru:8000/evolve";
    boolean isStarted = false;
    boolean isLoading = false;

    SeekBar mainVolume;
    SettingsContentObserver settingsContentObserver;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play, container, false);

        settingsContentObserver = new SettingsContentObserver(new Handler());
        getContext().getContentResolver().registerContentObserver(android.provider.Settings.System.CONTENT_URI, true, settingsContentObserver);

        player = ExoPlayerFactory.newSimpleInstance(getContext(), new DefaultRenderersFactory(getContext()), new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(new DefaultBandwidthMeter())), new DefaultLoadControl());

        ProgressBar playButtonProgressBar = view.findViewById(R.id.playButtonProgressBar);
        TextView playButton = view.findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isLoading) {
                    if (!isStarted) {
                        isLoading = true;
                        playButtonProgressBar.setVisibility(View.VISIBLE);
                        playButton.setVisibility(View.GONE);

                        player.prepare(new ExtractorMediaSource(Uri.parse(stream),
                                new DefaultDataSourceFactory(getContext(), "EvolveFM"),
                                new DefaultExtractorsFactory(),
                                new Handler(),
                                null));

                        player.addListener(new Player.EventListener() {
                            @Override
                            public void onIsPlayingChanged(boolean isPlaying) {
                                if (isPlaying && isLoading) {
                                    playButtonProgressBar.setVisibility(View.GONE);

                                    playButton.setVisibility(View.VISIBLE);
                                    playButton.setText(getString(R.string.stringPause));
                                    isLoading = false;
                                }
                            }
                        });
                    } else {
                        playButton.setText(getString(R.string.stringPlay));

                        player.stop();
                    }

                    isStarted = !isStarted;
                }
            }
        });

        final AudioManager audioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        mainVolume = view.findViewById(R.id.mainVolume);
        mainVolume.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        mainVolume.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
        mainVolume.getProgressDrawable().setTint(getResources().getColor(R.color.colorText));
        mainVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, i, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        getContext().getContentResolver().unregisterContentObserver(settingsContentObserver);

        player.setPlayWhenReady(false);
    }

    @Override
    public void onResume() {
        super.onResume();

        player.setPlayWhenReady(true);
    }

    public class SettingsContentObserver extends ContentObserver {
        public SettingsContentObserver(Handler handler) {
            super(handler);
        }

        @Override
        public boolean deliverSelfNotifications() {
            return super.deliverSelfNotifications();
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);

            mainVolume.setProgress(Objects.requireNonNull((AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE)).getStreamVolume(AudioManager.STREAM_MUSIC));
        }
    }
}