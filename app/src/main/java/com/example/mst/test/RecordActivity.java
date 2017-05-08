package com.example.mst.test;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class RecordActivity extends AppCompatActivity {
    private static final String LOG_TAG = "AudioRecordTest";
    private static String mFileName = null;
//    录音按钮
//    private RecordButton mRecordButton = null;
    private MediaRecorder mRecorder = null;
//    回放按钮
//    private PlayButton   mPlayButton = null;
    private MediaPlayer mPlayer = null;

    boolean mStartRecording = false;
    boolean mStartPlaying = false;


    //   当录音按钮被click时调用此方法，开始或停止录音
    private void onRecord(boolean start) {
         if (mStartRecording) {
            startRecording();
             mStartRecording = true;

        } else {
            stopRecording();
             mStartRecording = false;
        }
    }

//    当播放按钮被click时调用此方法，开始或停止播放
    private void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }

    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
//            设置要播放的文件
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();

            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    //停止播放
    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }

    private void startRecording() {
        mRecorder = new MediaRecorder();
        //设置音源为Micphone
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        //设置封装格式
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        //设置编码格式
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }


    public void AudioRecordTest() {
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/audiorecordtest.3gp";
    }

    public static void start(Context context)  {
        Intent intent = new  Intent(context, RecordActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void onPause() {
        super.onPause();
        //Activity暂停时释放录音和播放对象
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }

        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        AudioRecordTest();

        final Button recordButton = (Button)findViewById(R.id.record);
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStartRecording == false) {
                    startRecording();
                    mStartRecording = true;
                    recordButton.setText("stop record");
                } else {
                    stopRecording();
                    mStartRecording = false;
                    recordButton.setText("start record");
                }
            }
        });

        final Button playButton = (Button)findViewById(R.id.play);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStartPlaying == false) {
                    startPlaying();
                    mStartPlaying = true;
                    playButton.setText("stop playing");
                } else {
                    stopPlaying();
                    mStartPlaying = false;
                    playButton.setText("start playing");
                }
            }
        });

    }




}
