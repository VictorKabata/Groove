package com.vickikbt.groove.ui.activities

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.vickikbt.groove.R
import com.vickikbt.groove.adapters.SongsAdapter.Companion.MUSICPOSITION
import com.vickikbt.groove.repository.SongsRepository
import com.vickikbt.groove.services.MusicPlayerService


class MusicPlayerActivity : AppCompatActivity() {

    private var musicPlayerService = MusicPlayerService()
    private var playIntent: Intent? = null
    private var serviceBound = false

    var songsRepository: SongsRepository? = null
    var selectedPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_player)
        songsRepository = SongsRepository(this)

        songPicked()
    }

    private val serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val binder: MusicPlayerService.MusicBinder = service as MusicPlayerService.MusicBinder
            musicPlayerService = binder.musicservice
            serviceBound = true
            Toast.makeText(applicationContext, "Service connected", Toast.LENGTH_SHORT).show()
        }

        override fun onServiceDisconnected(name: ComponentName) {
            Toast.makeText(applicationContext, "Service disconnected", Toast.LENGTH_SHORT).show()
            serviceBound = false
        }
    }

    override fun onStart() {
        super.onStart()
        if (playIntent == null) {
            playIntent = Intent(this, MusicPlayerService::class.java)
            bindService(playIntent, serviceConnection, Context.BIND_AUTO_CREATE)
            startService(playIntent)
            Log.e("VickiKbt", "OnStart method called")
        }
    }

    private fun songPicked() {
        //Check if service is active
        if (!serviceBound) {
            selectedPosition = intent.getIntExtra(MUSICPOSITION, 0)
            Log.e("VickiKbt", selectedPosition.toString())
            //musicPlayerService!!.setSong(selectedPosition)
            musicPlayerService.playSong()
        } else {

        }
    }

    /*override fun onDestroy() {
        stopService(playIntent)
        musicPlayerService = null
        Log.e("VickiKbt", "OnDestroy method called")
        super.onDestroy()
    }*/
}