package io.github.untoastedtoast.foldio.persistence.tag

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import io.github.untoastedtoast.foldio.model.Tag

@Dao
interface TagDao {
    @Transaction
    @Query("SELECT * FROM tag")
    fun all(): Flow<List<Tag>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(tag: Tag)

    @Delete
    suspend fun remove(tag: Tag)
}
