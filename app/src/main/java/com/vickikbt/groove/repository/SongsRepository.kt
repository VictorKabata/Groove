package com.vickikbt.groove.repository

import android.content.Context
import android.provider.MediaStore
import com.vickikbt.groove.model.SongModel

class SongsRepository(context: Context) {
    companion object {
        var songModel = arrayListOf<SongModel>()
    }

    private val contentResolver = context.contentResolver
    private val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
    private val selection = MediaStore.Audio.Media.IS_MUSIC + "!=0"
    private val sortOrder = MediaStore.Audio.Media.TITLE
    private val cursor = contentResolver.query(uri, null, selection, null, sortOrder)

    fun loadSongs(): ArrayList<SongModel> {
        while (cursor!!.moveToNext()) {
            var songName =
                cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
            var songArtist =
                cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
            var songPath =
                cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
            var songAlbum =
                cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
            var songDuration =
                cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))

            if (songArtist == null) {
                songArtist = "Unknown"
            }

            songModel.add(SongModel(songPath, songName, songArtist, songAlbum, songDuration))
        }

        cursor.close()

        return songModel
    }
}