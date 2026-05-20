package site.orionhub.music.data.repository

import kotlinx.coroutines.flow.Flow
import site.orionhub.music.data.db.SongDao
import site.orionhub.music.data.model.Song
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MusicRepository @Inject constructor(
    private val songDao: SongDao,
) {
    fun getAllSongs(): Flow<List<Song>> = songDao.getAllSongs()

    fun getLikedSongs(): Flow<List<Song>> = songDao.getLikedSongs()

    fun searchLocalSongs(query: String): Flow<List<Song>> = songDao.searchSongs(query)

    suspend fun getSongById(id: String): Song? = songDao.getSongById(id)

    suspend fun saveSong(song: Song) = songDao.insertSong(song)

    suspend fun updateSong(song: Song) = songDao.updateSong(song)

    suspend fun deleteSong(song: Song) = songDao.deleteSong(song)

    suspend fun toggleLike(song: Song) {
        songDao.updateSong(song.copy(isLiked = !song.isLiked))
    }
}
