package com.vickikbt.groove.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.bumptech.glide.Glide
import com.vickikbt.groove.R
import com.vickikbt.groove.model.SongModel
import com.vickikbt.groove.services.MediaPlayerService
import com.vickikbt.groove.util.GetSongCoverArt
import java.io.File

class SongsAdapter(private val context: Context, private val songsModel: ArrayList<SongModel>) :
    RecyclerView.Adapter<SongsAdapter.SongsViewHolder>() {

    companion object {
        var MUSICLIST = "musiclist"
        var MUSICPOSITION = "pos"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.song_item, parent, false)

        return SongsViewHolder(view)
    }

    override fun getItemCount(): Int = songsModel.size

    override fun onBindViewHolder(holder: SongsViewHolder, position: Int) {
        var songPath = songsModel[position].songPath
        var songCoverArt = GetSongCoverArt.getSongCoverArt(songPath)

        if (songCoverArt != null) {
            Glide.with(context).load(songCoverArt).into(holder.songCoverArt)
        } else {
            Glide.with(context).load(R.drawable.ic_launcher_background).into(holder.songCoverArt)
        }

        holder.songName.text = songsModel[position].songName
        holder.songArtist.text = songsModel[position].songArtist


        /*holder.itemView.setOnClickListener {
            var intentService = Intent(context, MediaPlayerService::class.java)
            intentService.putExtra(MUSICPOSITION, position)
            context.startService(intentService)
        }*/
    }

    class SongsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var songName: TextView = itemView.findViewById(R.id.textView_songName)
        var songArtist: TextView = itemView.findViewById(R.id.textView_songArtist)
        var songCoverArt: ImageView = itemView.findViewById(R.id.imageView_songCover)
    }
}