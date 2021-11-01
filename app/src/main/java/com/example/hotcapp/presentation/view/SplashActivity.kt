package com.example.hotcapp.presentation.view

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.hotcapp.R
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        WindowInsetsControllerCompat(window, activitySplash).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            controller.isAppearanceLightStatusBars = true
        }
        val video = Uri.parse("android.resource://" + packageName + "/" + R.raw.hotc)
        splashVid.setVideoPath(video.toString())
        splashVid.setOnCompletionListener(object: MediaPlayer.OnCompletionListener{
            override fun onCompletion(mp: MediaPlayer?) {
                startNextActivity()
            }

        });
        splashVid.start();

    }

    private fun startNextActivity() {
        if (isFinishing())
            return;
        startActivity( Intent(this, HomeActivity::class.java))
        finish();

    }
}