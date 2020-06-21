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

    private val musicBind: IBinder = MusicBinder()

    override fun onCreate() {
        super.onCreate()
        player = MediaPlayer()
        songPosition = 0

        initMusicPlayer()
    }

    /* override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
         var playThisSong = songModel
         var thisPosition = intent!!.getIntExtra(MUSICPOSITION, 0)
         var songPath = playThisSong[thisPosition].songPath

         player!!.stop()
         player!!.setDataSource(songPath)
         player!!.prepare()
         player!!.start()


         return super.onStartCommand(intent, flags, startId)
     }*/

    override fun onBind(intent: Intent?): IBinder? {
        return musicBind
    }

    override fun onUnbind(intent: Intent?): Boolean {
        player!!.stop()
        player!!.release()
        return false
    }

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
        val playSong: SongModel = songs[0]
        var currentSong = playSong.songPath
        try {
            player!!.setDataSource(currentSong)
        } catch (e: Exception) {
            Log.e("VickiKbtService", "Error setting data source", e)
        }
        player!!.prepareAsync()
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

    class MusicBinder : Binder() {
        val musicservice: MusicPlayerService
            get() = MusicPlayerService()
    }

}