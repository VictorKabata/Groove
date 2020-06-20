package com.vickikbt.groove.util

import android.media.MediaMetadataRetriever

class GetSongCoverArt {

    companion object {

        fun getSongCoverArt(uri: String): ByteArray? {
            var mediaMetadataRetriever = MediaMetadataRetriever()
            mediaMetadataRetriever.setDataSource(uri)
            var songCoverArt = mediaMetadataRetriever.embeddedPicture
            mediaMetadataRetriever.release()

            return songCoverArt
        }
    }

}