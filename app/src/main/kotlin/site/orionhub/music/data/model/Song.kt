package site.orionhub.music.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "songs")
data class Song(
    @PrimaryKey val id: String,
    val title: String,
    val artist: String,
    val albumName: String? = null,
    val duration: Long = 0L,
    val thumbnailUrl: String? = null,
    val streamUrl: String? = null,
    val isLiked: Boolean = false,
    val addedAt: Long = System.currentTimeMillis(),
)

data class Playlist(
    val id: String,
    val title: String,
    val description: String? = null,
    val thumbnailUrl: String? = null,
    val songs: List<Song> = emptyList(),
)

data class Album(
    val id: String,
    val title: String,
    val artist: String,
    val thumbnailUrl: String? = null,
    val year: Int? = null,
    val songs: List<Song> = emptyList(),
)

data class Artist(
    val id: String,
    val name: String,
    val thumbnailUrl: String? = null,
    val subscriberCount: String? = null,
)

data class SearchResult(
    val songs: List<Song> = emptyList(),
    val albums: List<Album> = emptyList(),
    val artists: List<Artist> = emptyList(),
    val playlists: List<Playlist> = emptyList(),
)
