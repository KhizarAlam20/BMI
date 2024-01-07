package com.example.bmi

import android.annotation.SuppressLint
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.SeekBar
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {


    lateinit var seekBar: SeekBar
    lateinit var tvStart: TextView
    lateinit var tvRemaining: TextView
    lateinit var play_btn: FloatingActionButton
    lateinit var pause_btn: FloatingActionButton
    lateinit var stop_btn: FloatingActionButton
    private lateinit var handler: Handler

    private lateinit var runnable: Runnable

    var media: MediaPlayer? = null


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //handler
        handler = Handler(Looper.getMainLooper())

        //initializations
        seekBar = findViewById(R.id.seekBar)
        tvStart = findViewById(R.id.tvStart)
        tvRemaining = findViewById(R.id.tvRemaining)
        play_btn = findViewById(R.id.play_btn)
        pause_btn = findViewById(R.id.pause_btn)
        stop_btn = findViewById(R.id.stop_btn)


        //play buttons
        play_btn.setOnClickListener {
            if (media == null) {
                media = MediaPlayer.create(this, R.raw.stranger_things)
                initializedSeekBar()
            }
            media?.start()
        }


        pause_btn.setOnClickListener {
            media?.pause()
        }

        stop_btn.setOnClickListener {
            media?.stop()
            media?.reset()
            media?.release()
            media = null
            handler.removeCallbacks(runnable)
            seekBar.progress = 0
        }

    }

    private fun initializedSeekBar() {
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    media?.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })

        seekBar.max = media!!.duration
        runnable = Runnable {
            val playedTime = media!!.currentPosition/1000
            tvStart.text = "$playedTime sec"

            val duration = media!!.duration/1000
            val dueTime = duration-playedTime

            tvRemaining.text = "- $dueTime sec"


            seekBar.progress = media!!.currentPosition
            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)

    }


}

