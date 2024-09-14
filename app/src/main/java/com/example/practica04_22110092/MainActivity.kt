package com.example.practica04_22110092

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "MediaPlayer"

        val musicPlayerButton: Button = findViewById(R.id.button)
        val mediaPlayerButton: Button = findViewById(R.id.button3)
        val videoViewButton: Button = findViewById(R.id.button2)
        val closeButton: Button = findViewById(R.id.button4)

        musicPlayerButton.setOnClickListener {
            val intent = Intent(this, ActivityMusicPlayer::class.java)
            startActivity(intent)
        }

        mediaPlayerButton.setOnClickListener {
            val intent = Intent(this, ActivityMediaPlayer::class.java)
            startActivity(intent)
        }

        videoViewButton.setOnClickListener {
            val intent = Intent(this, ActivityVideoView::class.java)
            startActivity(intent)
        }

        closeButton.setOnClickListener {
            finish()
        }
    }
}