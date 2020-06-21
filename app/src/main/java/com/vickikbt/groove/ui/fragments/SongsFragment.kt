package com.vickikbt.groove.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.vickikbt.groove.R
import com.vickikbt.groove.adapters.SongsAdapter
import com.vickikbt.groove.databinding.FragmentSongsBinding
import com.vickikbt.groove.repository.SongsRepository
import com.vickikbt.groove.services.MusicPlayerService


class SongsFragment : Fragment() {
    lateinit var binding: FragmentSongsBinding

    private var songsRepository: SongsRepository? = null

    private var player: MusicPlayerService? = null
    var serviceBound = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_songs, container, false)
        songsRepository = SongsRepository(requireActivity().applicationContext)

        loadRecyclerView()

        return binding.root
    }

    private fun loadRecyclerView() {
        val adapter =
            SongsAdapter(requireActivity().applicationContext, songsRepository!!.loadSongs())

        binding.recyclerviewSongs.adapter = adapter
    }


}