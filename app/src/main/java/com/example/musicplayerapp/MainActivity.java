package com.example.musicplayerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button playbutton;
    private SeekBar positionbar,volumebar;
    private TextView elapsedtimeLable,remainingtimelabel ;
    private MediaPlayer mediaPlayer;
    private int totaltime;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playbutton=(Button)findViewById(R.id.playbutton);
        elapsedtimeLable=(TextView) findViewById(R.id.elapsedtimelabel);
        remainingtimelabel=(TextView)findViewById(R.id.Remainingtimelabel);

        mediaPlayer=MediaPlayer.create(this,R.raw.files);
        mediaPlayer.setLooping(true);
        mediaPlayer.seekTo(0);
        mediaPlayer.setVolume(0.5f,0.5f);
        totaltime=mediaPlayer.getDuration();
        positionbar=(SeekBar)findViewById(R.id.positionbar);
        positionbar.setMax(totaltime);
        positionbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b)
                {
                    mediaPlayer.seekTo(i);
                    positionbar.setProgress(i);

                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        volumebar=(SeekBar)findViewById(R.id.volumebar);
        volumebar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                float volumeNumber=i/100f;
                mediaPlayer.setVolume(volumeNumber,volumeNumber);


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mediaPlayer!=null)
                {
                    try {
                        Message message = new Message();
                        Message.what=mediaPlayer.getCurrentPosition();
                        handler.sendMessage(message);
                        Thread.sleep(1000);
                        }
                    catch (InterruptedException e)
                    {

                    }
                }
            }
        });
    }

    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(@NonNull Message msg)
        {
            int currentPosition = obtainMessage().what;
            positionbar.setProgress(currentPosition);
            super.handleMessage(msg);

            String elapsedTime=createTimeLabel(currentPosition);
            elapsedtimeLable.setText(elapsedTime);
            String remainingTime = createTimeLabel(totaltime-currentPosition);
            remainingtimelabel.setText("-"+remainingTime);

0        }
    };

    public String createTimeLabel(int time)
    {
        String timeLabel="";
        int min=time/1000/60;
        int sec = time/1000%60;
        timeLabel=min + ":";
        if(sec<10)
        timeLabel+="0";
        timeLabel+=sec;
        return timeLabel;

    }

    public void playbtnclick(View view)
    {
        if(!mediaPlayer.isPlaying())
        {
            mediaPlayer.start();
            playbutton.setBackgroundResource(R.drawable.sound);

        }
        else
        {
            mediaPlayer.pause();
            playbutton.setBackgroundResource(R.drawable.play);

        }
    }

}
