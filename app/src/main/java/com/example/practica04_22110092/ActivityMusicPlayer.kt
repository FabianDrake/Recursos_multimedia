package com.example.practica04_22110092

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class ActivityMusicPlayer : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var playButton: ImageButton
    private lateinit var pauseButton: ImageButton
    private lateinit var rewindButton: ImageButton
    private lateinit var forwardButton: ImageButton
    private lateinit var progressBar: SeekBar
    private lateinit var textView: TextView
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_player)

        val toolbar: Toolbar = findViewById(R.id.toolbar2)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "MediaPlayer"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        playButton = findViewById(R.id.btnPlay)
        pauseButton = findViewById(R.id.btnPause)
        rewindButton = findViewById(R.id.btnRewind)
        forwardButton = findViewById(R.id.btnForward)
        progressBar = findViewById(R.id.progressBar)
        textView = findViewById(R.id.textView)

        //la cancion que se va a reproducir
        mediaPlayer = MediaPlayer.create(this, R.raw.audio)
        progressBar.max = mediaPlayer.duration

        playButton.setOnClickListener {
            mediaPlayer.start()
            textView.text = "Mago de Oz - El libro de las sombras"
            updateProgressBar()
        }

        pauseButton.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
            }
        }

        rewindButton.setOnClickListener {
            val newPosition = (mediaPlayer.currentPosition - 5000).coerceAtLeast(0)
            mediaPlayer.seekTo(newPosition)
        }

        forwardButton.setOnClickListener {
            val newPosition = (mediaPlayer.currentPosition + 5000).coerceAtMost(mediaPlayer.duration)
            mediaPlayer.seekTo(newPosition)
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
                mediaPlayer.seekTo(newPosition)
                mediaPlayer.start()
            } else {
                mediaPlayer.seekTo(newPosition)
            }
        }

        progressBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        mediaPlayer.setOnCompletionListener {
            textView.text = "Music Player"
            progressBar.progress = 0
        }
    }

    private fun updateProgressBar() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                progressBar.progress = mediaPlayer.currentPosition
                handler.postDelayed(this, 1000)
            }
        }, 1000)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                Toast.makeText(this, "Regreso", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::mediaPlayer.isInitialized) {
            mediaPlayer.release()
        }
        handler.removeCallbacksAndMessages(null)
    }
}