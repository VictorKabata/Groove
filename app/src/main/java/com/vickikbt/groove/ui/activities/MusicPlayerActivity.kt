package com.vickikbt.groove.ui.activities

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.vickikbt.groove.R
import com.vickikbt.groove.adapters.SongsAdapter
import com.vickikbt.groove.repository.SongsRepository
import com.vickikbt.groove.services.MusicPlayerService


class MusicPlayerActivity : AppCompatActivity() {

    var musicPlayerService: MusicPlayerService? = null
    var playIntent: Intent? = null
    var isBound: Boolean = false

    var selectedPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_player)

    }

    private val musicConnection = object : ServiceConnection {
        override fun onServiceConnected(
            className: ComponentName,
            service: IBinder
        ) {
            val binder = service as MusicPlayerService.LocalBinder
            musicPlayerService = binder.getService()
            isBound = true
            songPicked()
        }

        override fun onServiceDisconnected(name: ComponentName) {
            isBound = false
        }
    }


    private fun songPicked() {
        musicPlayerService!!.setList(SongsRepository.songModel)
        selectedPosition = intent.getIntExtra(SongsAdapter.MUSICPOSITION, 0)
        musicPlayerService!!.setSong(selectedPosition)
        musicPlayerService!!.playSong()
    }

    override fun onStart() {
        super.onStart()
        playIntent = Intent(this, MusicPlayerService::class.java)
        bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE)
        startService(playIntent)
    }

    /*override fun onDestroy() {
        stopService(playIntent)
        musicPlayerService = null
        super.onDestroy()
    }*/
}