package com.vickikbt.groove.ui.fragments

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.vickikbt.groove.R
import com.vickikbt.groove.adapters.SongsAdapter
import com.vickikbt.groove.databinding.FragmentSongsBinding
import com.vickikbt.groove.repository.SongsRepository
import com.vickikbt.groove.repository.SongsRepository.Companion.songModel
import com.vickikbt.groove.services.MediaPlayerService
import com.vickikbt.groove.services.MediaPlayerService.LocalBinder


class SongsFragment : Fragment() {
    lateinit var binding: FragmentSongsBinding

    private var songsRepository: SongsRepository? = null

    private var player: MediaPlayerService? = null
    var serviceBound = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_songs, container, false)
        songsRepository = SongsRepository(requireActivity().applicationContext)

        loadRecyclerView()
        playAudio(songModel[0].songPath)

        return binding.root
    }

    private fun loadRecyclerView() {
        val adapter =
            SongsAdapter(requireActivity().applicationContext, songsRepository!!.loadSongs())

        binding.recyclerviewSongs.adapter = adapter
    }

    private val serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val binder = service as LocalBinder
            player = binder.service
            serviceBound = true
            Toast.makeText(activity, "Service Bound", Toast.LENGTH_SHORT).show()
        }

        override fun onServiceDisconnected(name: ComponentName) {
            serviceBound = false
        }
    }

    private fun playAudio(media: String) {
        if (!serviceBound) {
            val playerIntent = Intent(activity, MediaPlayerService::class.java)
            playerIntent.putExtra("media", media)
            requireActivity().startService(playerIntent)
            requireActivity().bindService(playerIntent, serviceConnection, Context.BIND_AUTO_CREATE)
        } else {
            //Service is active
            //Send media with BroadcastReceiver
        }
    }

}