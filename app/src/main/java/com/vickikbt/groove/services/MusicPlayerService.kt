package com.vickikbt.groove.services

import android.app.Service
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.os.PowerManager
import android.util.Log
import com.vickikbt.groove.model.SongModel


class MusicPlayerService : Service(), MediaPlayer.OnCompletionListener,
    MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {

    private var player: MediaPlayer? = null
    private var songPosition: Int? = null
    var songs = arrayListOf<SongModel>()

    private val musicBinder: IBinder = LocalBinder()

    override fun onCreate() {
        super.onCreate()
        player = MediaPlayer()
        songPosition = 0

        initMusicPlayer()
    }


    override fun onBind(intent: Intent?): IBinder? {
        return musicBinder
    }

    /*override fun onUnbind(intent: Intent?): Boolean {
        player!!.stop()
        player!!.release()
        return false
    }*/

    private fun initMusicPlayer() {
        player!!.setWakeMode(applicationContext, PowerManager.PARTIAL_WAKE_LOCK)
        player!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
        player!!.setOnCompletionListener(this)
        player!!.setOnPreparedListener(this)
        player!!.setOnErrorListener(this)
        player!!.reset()
    }

    fun setList(theSongs: ArrayList<SongModel>) {
        songs = theSongs
    }

    fun playSong() {
        player = MediaPlayer()
        player!!.reset()
        player!!.setDataSource(songs[songPosition!!].songPath)
        player!!.prepare()
        player!!.start()

        //player!!.prepareAsync()
    }

    fun setSong(songIndex: Int) {
        songPosition = songIndex
        Log.e("VickiKbtService", songPosition.toString())
    }

    override fun onCompletion(mp: MediaPlayer?) {

    }

    override fun onPrepared(mp: MediaPlayer?) {
        mp!!.start()
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        return false
    }

    inner class LocalBinder : Binder() {
        fun getService(): MusicPlayerService {
            return this@MusicPlayerService
        }
    }

}